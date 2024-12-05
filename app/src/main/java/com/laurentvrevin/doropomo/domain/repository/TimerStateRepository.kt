package com.laurentvrevin.doropomo.domain.repository

import com.laurentvrevin.doropomo.domain.model.TimerState

interface TimerStateRepository {
    fun startTimer(duration: Long): TimerState
    fun pauseTimer(): TimerState
    fun resetTimer(): TimerState
    fun getTimerState(): TimerState
    fun setTimerPreferences(workDuration: Long, breakDuration: Long): TimerState
    fun updateTimerState(timerState: TimerState) // Nouvelle méthode
}