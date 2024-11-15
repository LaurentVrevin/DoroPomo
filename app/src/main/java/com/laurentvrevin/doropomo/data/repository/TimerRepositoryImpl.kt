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
            isRunning = true
        )
        return timerState
    }

    override fun pauseTimer(): TimerState {
        val elapsedTime = System.currentTimeMillis() - timerState.startTime
        timerState = timerState.copy(
            remainingTime = timerState.remainingTime - elapsedTime,
            isRunning = false
        )
        return timerState
    }

    override fun resetTimer(): TimerState {
        timerState = timerState.copy(
            remainingTime = timerState.workDuration,
            isRunning = false,
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
            remainingTime = workDuration,
            isRunning = false
        )
        return timerState
    }
}