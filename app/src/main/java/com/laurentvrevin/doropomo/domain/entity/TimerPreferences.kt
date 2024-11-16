package com.laurentvrevin.doropomo.domain.entity

data class TimerPreferences(
    val workDuration: Long = 25 * 60 * 1000L,      // Durée de travail
    val breakDuration: Long = 5 * 60 * 1000L,      // Durée de pause
    val longBreakDuration: Long = 15 * 60 * 1000L, // Durée longue pause
    val cyclesBeforeLongBreak: Int = 4            // Cycles avant pause longue
)