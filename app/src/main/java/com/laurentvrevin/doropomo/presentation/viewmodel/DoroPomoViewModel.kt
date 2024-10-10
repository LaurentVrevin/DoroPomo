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

    private val oneSecond: Long = 1000L

    val isDarkTheme = mutableStateOf(false)

    // Initialize the timer with default values and `isRunning` set to `false`
    val timerState = mutableStateOf(
        TimerState(
            startTime = 0L,
            remainingTime = 25 * 60 * 1000L,  // Default to 25 minutes
            workDuration = 25 * 60 * 1000L,
            breakDuration = 5 * 60 * 1000L,
            isRunning = false,
            isBreakTime = false
        )
    )

    private var timerJob: Job? = null

    // Start the timer
    fun startTimer() {
        // Check if the timer is already running, if so, do nothing
        if (timerState.value.isRunning) return

        // Start the timer with the current state of TimerState
        timerState.value = startTimerUseCase.execute(timerState.value.remainingTime)

        // Launch a coroutine to update the timer every second
        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            val initialRemainingTime = timerState.value.remainingTime

            while (timerState.value.isRunning && timerState.value.remainingTime > 0) {
                delay(oneSecond)  // Wait for 1 second

                // Calculate the elapsed time
                val elapsedTime = System.currentTimeMillis() - startTime
                val newRemainingTime = initialRemainingTime - elapsedTime

                // Update the timer state with the calculated remaining time
                timerState.value = timerState.value.copy(remainingTime = newRemainingTime)

                // Stop the timer if the time is up
                if (newRemainingTime <= 0L) {
                    timerState.value = timerState.value.copy(
                        remainingTime = 0L,
                        isRunning = false
                    )
                    timerJob?.cancel()  // Cancel the coroutine
                }
            }
        }
    }

    // Pause the timer
    fun pauseTimer() {
        timerJob?.cancel()  // Cancel the ongoing coroutine
        timerState.value = pauseTimerUseCase.execute()
    }

    // Reset the timer
    fun resetTimer() {
        timerJob?.cancel()  // Cancel the ongoing coroutine
        timerState.value = resetTimerUseCase.execute()
    }

    // Update the timer preferences
    fun updateTimerPreferences(workDuration: Long, breakDuration: Long) {
        timerState.value = setTimerPreferencesUseCase.execute(workDuration, breakDuration)
    }

    // Witch between dark and light theme
    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}
