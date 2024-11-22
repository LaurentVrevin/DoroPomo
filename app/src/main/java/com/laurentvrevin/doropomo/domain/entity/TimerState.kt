package com.laurentvrevin.doropomo.domain.entity

data class TimerState(
    val startTime: Long = 0L,
    val remainingTime: Long = 25 * 60 * 1000L,
    val workDuration: Long = 25 * 60 * 1000L,
    val breakDuration: Long = 5 * 60 * 1000L,
    val cyclesBeforeLongBreak: Int = 4,
    val isRunning: Boolean = false,
    val isBreakTime: Boolean = false
)