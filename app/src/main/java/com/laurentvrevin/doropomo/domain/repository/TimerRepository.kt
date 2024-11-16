package com.laurentvrevin.doropomo.domain.repository

import com.laurentvrevin.doropomo.domain.entity.TimerState

interface TimerRepository {
    fun startTimer(duration: Long): TimerState
    fun pauseTimer(): TimerState
    fun resetTimer(): TimerState
    fun getTimerState(): TimerState
    fun setTimerPreferences(workDuration: Long, breakDuration: Long): TimerState // Nouvelle méthode

}