package com.laurentvrevin.doropomo.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.usecase.*
import com.laurentvrevin.doropomo.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoroPomoViewModel @Inject constructor(
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase,
    private val setTimerPreferencesUseCase: SetTimerPreferencesUseCase,
    private val preferencesManager: PreferencesManager,
    private val playAlarmUseCase: PlayAlarmUseCase,
    private val stopAlarmUseCase: StopAlarmUseCase
) : ViewModel() {

    private val updateInterval: Long = 50L
    private val oneSecond: Long = 1000L

    val isDarkTheme = mutableStateOf(false) // Gestion du thème

    private var timerJob: Job? = null

    private val _timerState = MutableStateFlow(
        TimerState(
            startTime = 0L,
            remainingTime = preferencesManager.getSavedPomodoroMode().workDuration,
            workDuration = preferencesManager.getSavedPomodoroMode().workDuration,
            breakDuration = preferencesManager.getSavedPomodoroMode().breakDuration,
            isRunning = false,
            isBreakTime = false
        )
    )
    val timerState: StateFlow<TimerState> = _timerState

    private val _cyclesBeforeLongBreak = MutableStateFlow(preferencesManager.getSavedCycles())
    val cyclesBeforeLongBreak: StateFlow<Int> = _cyclesBeforeLongBreak

    val showPopup = mutableStateOf(false)
    val popupMessage = mutableStateOf("")

    private var currentCycle = 0 // Compteur de cycles de travail effectués

    init {
        // Écouter les changements de préférences sauvegardées
        viewModelScope.launch {
            preferencesManager.getPomodoroModeFlow().collect { mode ->
                updateTimerPreferences(mode.workDuration, mode.breakDuration, cyclesBeforeLongBreak.value)
            }
        }

        // Log pour vérifier l'initialisation
        println("verifycycles - ViewModel: cyclesBeforeLongBreak initialized as ${_cyclesBeforeLongBreak.value}")
    }

    //--- GESTION DES PRÉFÉRENCES ---//

    fun updateCycleCount(newCycleCount: Int) {
        // Mettre à jour la valeur dans le Flow
        _cyclesBeforeLongBreak.value = newCycleCount

        // Sauvegarder dans les préférences
        preferencesManager.savePomodoroMode(
            mode = preferencesManager.getSavedPomodoroMode(),
            cycles = newCycleCount
        )
        // Log pour vérifier la mise à jour dans ViewModel
        println("verifycycles - ViewModel: cyclesBeforeLongBreak updated to $newCycleCount")
    }


    fun savePomodoroPreferences(mode: PomodoroMode, cycles: Int) {
        preferencesManager.savePomodoroMode(mode, cycles)
        _cyclesBeforeLongBreak.value = cycles
    }


    fun updateTimerPreferences(workDuration: Long, breakDuration: Long, cycles: Int) {
        val newTimerState = setTimerPreferencesUseCase.execute(workDuration, breakDuration)
        _timerState.value = newTimerState.copy(remainingTime = newTimerState.workDuration)
        cycles
        savePomodoroPreferences(PomodoroMode(workDuration, breakDuration, "${workDuration / 1000 / 60}/${breakDuration / 1000 / 60}"), cycles)
    }

    //--- GESTION DU TIMER ---//

    fun startTimer(context: Context) {
        if (timerState.value.isRunning) return
        _timerState.value = startTimerUseCase.execute(timerState.value.remainingTime)
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            val initialStartTime = System.currentTimeMillis()
            var lastUpdateTime = initialStartTime

            while (timerState.value.isRunning && timerState.value.remainingTime > 0) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastUpdateTime
                if (elapsedTime >= oneSecond) {
                    val newRemainingTime = timerState.value.remainingTime - oneSecond
                    _timerState.value = timerState.value.copy(remainingTime = newRemainingTime)
                    lastUpdateTime += oneSecond
                }

                if (timerState.value.remainingTime <= 0L) {
                    timerJob?.cancel()
                    onTimerFinish(context)
                }

                delay(updateInterval)
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _timerState.value = pauseTimerUseCase.execute()
    }

    fun resetTimer() {
        timerJob?.cancel()
        _timerState.value = resetTimerUseCase.execute()
        currentCycle = 0
    }

    //--- GESTION DU THÈME ---//

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }

    //--- GESTION DU CYCLE ET DES PAUSES ---//

    private fun onTimerFinish(context: Context) {
        playAlarmUseCase.execute()
        stopAlarmUseCase.execute()

        if (!timerState.value.isBreakTime) {
            // Fin de la période de travail
            currentCycle++
            if (currentCycle < cyclesBeforeLongBreak.value) {
                showPopup.value = true
                popupMessage.value = "Cycle de travail terminé ! Prêt pour une pause ?"
            } else {
                showPopup.value = true
                popupMessage.value = "Cycles de travail complets ! Prêt pour une pause longue ?"
                currentCycle = 0
            }
            _timerState.value = timerState.value.copy(isRunning = false)
        } else {
            // Fin de la pause
            showPopup.value = true
            popupMessage.value = "Pause terminée ! Prêt à reprendre le travail ?"
            _timerState.value = timerState.value.copy(isRunning = false)
        }
    }

    fun startWork(context: Context) {
        showPopup.value = false
        _timerState.value = timerState.value.copy(
            remainingTime = timerState.value.workDuration,
            isRunning = true,
            isBreakTime = false
        )
        startTimer(context)
    }

    fun startBreak(context: Context) {
        showPopup.value = false
        val breakDuration = if (currentCycle == 0) timerState.value.breakDuration * 3 else timerState.value.breakDuration
        _timerState.value = timerState.value.copy(
            remainingTime = breakDuration,
            isRunning = true,
            isBreakTime = true
        )
        startTimer(context)
    }

    fun stopAlarm() {
        stopAlarmUseCase.execute()
    }
}
