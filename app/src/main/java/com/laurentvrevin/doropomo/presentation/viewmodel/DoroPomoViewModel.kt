package com.laurentvrevin.doropomo.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.usecase.*
import com.laurentvrevin.doropomo.data.layer.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
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
    val id = UUID.randomUUID().toString()

    val isDarkTheme = mutableStateOf(false) // Gestion du thème

    private var timerJob: Job? = null

    //chargé an 1er
    private val _timerState = MutableStateFlow(preferencesManager.getSavedTimerState())
    val timerState: StateFlow<TimerState> = _timerState

    //chargé en 2ème
    private val _cyclesBeforeLongBreak = MutableStateFlow(preferencesManager.getSavedCycles())
    val cyclesBeforeLongBreak: StateFlow<Int> = _cyclesBeforeLongBreak

    init {
        viewModelScope.launch {
            //chargé en 3ème
            println("verifycycles - ViewModel : Welcome in ViewModel init block")
            println("ViewModel created with id: $id")

            //chargé en 4ème
            preferencesManager.getPomodoroModeFlow().collect { mode ->
                updateTimerPreferences(mode.workDuration, mode.breakDuration, _timerState.value.cyclesBeforeLongBreak)
            }
        }
        println("verifycycles - ViewModel: TimerState initialized with ${_timerState.value.cyclesBeforeLongBreak} cycles")
    }

    //--- GESTION DU TIMER ---//

    fun startTimer(context: Context) {
        if (_timerState.value.isRunning) return
        val updatedState = startTimerUseCase.execute(_timerState.value.remainingTime)
        _timerState.value = updatedState
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            val initialTime = System.currentTimeMillis()
            var lastUpdate = initialTime

            while (_timerState.value.isRunning && _timerState.value.remainingTime > 0) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastUpdate
                if (elapsedTime >= 1000) {
                    val newRemainingTime = _timerState.value.remainingTime - 1000
                    _timerState.value = _timerState.value.copy(remainingTime = newRemainingTime)
                    lastUpdate += 1000
                }
                if (_timerState.value.remainingTime <= 0) {
                    timerJob?.cancel()
                    onTimerFinish(context)
                }
                delay(50L)
                println("verifycycles - ViewModel : fun startTimer with ${_timerState.value}")
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _timerState.value = pauseTimerUseCase.execute()
        println("verifycycles - ViewModel : fun pauseTimer with ${_timerState.value}")
    }

    fun resetTimer() {
        timerJob?.cancel()
        val savedState = preferencesManager.getSavedTimerState()
        _timerState.value = savedState.copy(
            remainingTime = savedState.workDuration,
            isRunning = false,
            isBreakTime = false
        )
        println("verifycycles - ViewModel: Timer reset to ${_timerState.value}")
    }

    //--- GESTION DES PRÉFÉRENCES ---//

    fun updateCycleCount(newCycleCount: Int) {
        val updatedState = _timerState.value.copy(cyclesBeforeLongBreak = newCycleCount)
        _timerState.value = updatedState
        _cyclesBeforeLongBreak.value = newCycleCount // Synchronisation explicite

        preferencesManager.savePomodoroMode(
            PomodoroMode(
                updatedState.workDuration,
                updatedState.breakDuration,
                "${updatedState.workDuration / 1000 / 60}/${updatedState.breakDuration / 1000 / 60}"
            ),
            newCycleCount
        )
        println("verifycycles - ViewModel: fun updateCycleCount to $newCycleCount")
    }

    fun savePomodoroPreferences(mode: PomodoroMode, cycles: Int) {
        preferencesManager.savePomodoroMode(mode, cycles)
        _cyclesBeforeLongBreak.value = cycles
        println("verifycycles - ViewModel: fun savePomodoroPreferences ${_cyclesBeforeLongBreak.value}")
    }

    fun updateTimerPreferences(workDuration: Long, breakDuration: Long, cycles: Int) {
        val newTimerState = setTimerPreferencesUseCase.execute(workDuration, breakDuration)
        _timerState.value = newTimerState.copy(remainingTime = newTimerState.workDuration)
        cycles
        savePomodoroPreferences(PomodoroMode(workDuration, breakDuration, "${workDuration / 1000 / 60}/${breakDuration / 1000 / 60}"), cycles)
        println("verifycycles - ViewModel: fun updateTimerPreferences ${_cyclesBeforeLongBreak.value} & ${_timerState.value}")
    }

    //--- GESTION DU CYCLE ET DES PAUSES ---//

    private fun onTimerFinish(context: Context) {
        playAlarmUseCase.execute()
        stopAlarmUseCase.execute()
        val isBreak = !_timerState.value.isBreakTime
        _timerState.value = _timerState.value.copy(
            isRunning = false,
            isBreakTime = isBreak,
            remainingTime = if (isBreak) _timerState.value.breakDuration else _timerState.value.workDuration
        )
    }

    fun stopAlarm() {
        stopAlarmUseCase.execute()
    }

    //--- GESTION DU THÈME ---//

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}
