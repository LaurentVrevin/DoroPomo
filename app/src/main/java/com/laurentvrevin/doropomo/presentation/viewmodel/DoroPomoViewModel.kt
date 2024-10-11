package com.laurentvrevin.doropomo.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.usecase.PauseTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.ResetTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.SetTimerPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.StartTimerUseCase
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
    private val setTimerPreferencesUseCase: SetTimerPreferencesUseCase
) : ViewModel() {

    private val updateInterval: Long = 50L
    private val oneSecond: Long = 1000L

    val isDarkTheme = mutableStateOf(false)

    // Initialize timer state
    val timerState = mutableStateOf(
        TimerState(
            startTime = 0L,
            remainingTime = 25 * 60 * 1000L,
            workDuration = 25 * 60 * 1000L,
            breakDuration = 5 * 60 * 1000L,
            isRunning = false,
            isBreakTime = false
        )
    )

    private var timerJob: Job? = null

    // Démarrer le timer
    fun startTimer() {
        // Verify if timer is already running
        if (timerState.value.isRunning) return

        // Update Timer
        timerState.value = startTimerUseCase.execute(timerState.value.remainingTime)

        timerJob?.cancel()  // Cancel all running timer before starting a new one

        // Rune Coroutine to update timer
        timerJob = viewModelScope.launch {
            val initialStartTime = System.currentTimeMillis()
            var lastUpdateTime = initialStartTime

            while (timerState.value.isRunning && timerState.value.remainingTime > 0) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastUpdateTime

                // If a fully second has passed, update the timer
                if (elapsedTime >= oneSecond) {
                    val newRemainingTime = timerState.value.remainingTime - oneSecond
                    timerState.value = timerState.value.copy(remainingTime = newRemainingTime)
                    lastUpdateTime += oneSecond  // Update lastUpdateTime
                }

                // If timer is up, stop it
                if (timerState.value.remainingTime <= 0L) {
                    timerState.value = timerState.value.copy(
                        remainingTime = 0L,
                        isRunning = false
                    )
                    timerJob?.cancel()  // Cancel Coroutine
                }

                // Use mini delay instead of Thread.sleep
                delay(updateInterval)
            }
        }
    }


    fun pauseTimer() {
        timerJob?.cancel()
        timerState.value = pauseTimerUseCase.execute()
    }


    fun resetTimer() {
        timerJob?.cancel()
        timerState.value = resetTimerUseCase.execute()
    }


    fun updateTimerPreferences(workDuration: Long, breakDuration: Long) {
        timerState.value = setTimerPreferencesUseCase.execute(workDuration, breakDuration)
    }


    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}
