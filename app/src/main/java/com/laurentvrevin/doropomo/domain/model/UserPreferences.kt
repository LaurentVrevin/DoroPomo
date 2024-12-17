package com.laurentvrevin.doropomo.domain.model

data class UserPreferences(
    val isDarkTheme: Boolean = false,
    val isFocusModeEnabled: Boolean = false,
    val workDuration: Long = 25 * 60 * 1000L,
    val breakDuration: Long = 5 * 60 * 1000L,
    val longBreakDuration: Long = 15 * 60 * 1000L,
    val cyclesBeforeLongBreak: Int = 4,
    val alarmSoundEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true
)
