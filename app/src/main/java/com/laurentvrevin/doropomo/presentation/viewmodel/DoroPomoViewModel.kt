package com.laurentvrevin.doropomo.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.usecase.PauseTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.ResetTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.SetTimerPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.StartTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.StopAlarmUseCase
import com.laurentvrevin.doropomo.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    val isDarkTheme = mutableStateOf(false)
    val timerState = mutableStateOf(
        TimerState(
            startTime = 0L,
            remainingTime = preferencesManager.getSavedPomodoroMode().workDuration,
            workDuration = preferencesManager.getSavedPomodoroMode().workDuration,
            breakDuration = preferencesManager.getSavedPomodoroMode().breakDuration,
            isRunning = false,
            isBreakTime = false
        )
    )

    private var timerJob: Job? = null

    // Méthode pour charger les préférences sauvegardées
    fun applySavedPreferences() {
        val savedMode = preferencesManager.getSavedPomodoroMode()
        timerState.value = timerState.value.copy(
            workDuration = savedMode.workDuration,
            breakDuration = savedMode.breakDuration,
            remainingTime = savedMode.workDuration  // Reset remaining time for new mode
        )
    }

    // Mettre à jour les préférences de timer
    fun updateTimerPreferences(workDuration: Long, breakDuration: Long) {
        timerState.value = setTimerPreferencesUseCase.execute(workDuration, breakDuration)
        savePomodoroPreferences(PomodoroMode(workDuration, breakDuration, "${workDuration / 1000 / 60}/${breakDuration / 1000 / 60}"))
    }

    private fun savePomodoroPreferences(mode: PomodoroMode) {
        preferencesManager.savePomodoroMode(mode)
    }

    // Fonction pour démarrer le timer de travail
    fun startTimer() {
        if (timerState.value.isRunning) return
        timerState.value = startTimerUseCase.execute(timerState.value.remainingTime)
        timerJob?.cancel()
        startCountdown()
    }

    // Fonction pour démarrer une session de pause
    fun startBreak() {
        timerState.value = timerState.value.copy(
            remainingTime = timerState.value.breakDuration,
            isRunning = true,
            isBreakTime = true
        )
        startCountdown()
    }

    // Démarre le décompte du timer (réutilisé pour le travail et la pause)
    private fun startCountdown() {
        timerJob = viewModelScope.launch {
            val initialStartTime = System.currentTimeMillis()
            var lastUpdateTime = initialStartTime

            while (timerState.value.isRunning && timerState.value.remainingTime > 0) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastUpdateTime
                if (elapsedTime >= oneSecond) {
                    val newRemainingTime = timerState.value.remainingTime - oneSecond
                    timerState.value = timerState.value.copy(remainingTime = newRemainingTime)
                    lastUpdateTime += oneSecond
                }

                if (timerState.value.remainingTime <= 0L) {
                    timerState.value = timerState.value.copy(remainingTime = 0L, isRunning = false)
                    timerJob?.cancel()
                    onTimerComplete()
                }
                delay(updateInterval)
            }
        }
    }

    // Fonction appelée lorsque le temps de travail ou de pause est écoulé
    private fun onTimerComplete() {
        playAlarmUseCase.execute()
        if (timerState.value.isBreakTime) {
            // Le break est terminé
            stopAlarmUseCase.execute()
        } else {
            // Le travail est terminé, afficher le popup pour la pause
            showBreakPopup()
        }
    }

    // Fonction pour montrer le popup pour la pause
    private fun showBreakPopup() {
        // Logique pour déclencher un événement de popup, par ex. via un état observable
    }

    fun stopAlarm() {
        stopAlarmUseCase.execute()
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerState.value = pauseTimerUseCase.execute()
    }

    fun resetTimer() {
        timerJob?.cancel()
        timerState.value = resetTimerUseCase.execute()
    }

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}
