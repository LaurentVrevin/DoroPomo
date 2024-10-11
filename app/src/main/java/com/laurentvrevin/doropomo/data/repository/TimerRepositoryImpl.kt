package com.laurentvrevin.doropomo.data.repository

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class TimerRepositoryImpl : TimerRepository {
    private var timerState = TimerState()
    private var startTimeInMillis: Long = 0L

    override fun startTimer(duration: Long): TimerState {
        startTimeInMillis = System.currentTimeMillis()
        timerState = timerState.copy(
            startTime = startTimeInMillis,
            remainingTime = duration,
            isRunning = true  // Indicate that the timer is running
        )
        return timerState
    }

    override fun pauseTimer(): TimerState {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - timerState.startTime
        timerState = timerState.copy(
            remainingTime = timerState.remainingTime - elapsedTime,  // Update the remaining time
            isRunning = false  // Indicate that the timer is paused
        )
        return timerState
    }

    override fun resetTimer(): TimerState {
        timerState = timerState.copy(
            remainingTime = timerState.workDuration,  // Reset the remaining time to the work duration
            isRunning = false,  // Indicate that the timer is reset
            isBreakTime = false
        )
        return timerState
    }

    override fun getTimerState(): TimerState {
        return timerState
    }

    override fun setTimerPreferences(workDuration: Long, breakDuration: Long): TimerState {
        timerState = timerState.copy(
            workDuration = workDuration,
            breakDuration = breakDuration,
            remainingTime = workDuration,  // Update the remaining time to the new work duration
            isRunning = false  // Ensure that the timer is stopped when updating preferences
        )
        return timerState
    }
}