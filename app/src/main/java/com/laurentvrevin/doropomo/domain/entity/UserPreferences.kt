package com.laurentvrevin.doropomo.domain.entity

data class UserPreferences(
    val isDarkTheme: Boolean = false,               // Thème sombre activé ou non
    val isDoNotDisturbEnabled: Boolean = false,     // Mode "Don't Disturb" activé ou non
    val workDuration: Long = 25 * 60 * 1000L,       // Durée d'une session de travail (en ms)
    val breakDuration: Long = 5 * 60 * 1000L,       // Durée d'une pause courte (en ms)
    val longBreakDuration: Long = 15 * 60 * 1000L,  // Durée d'une pause longue (en ms)
    val cyclesBeforeLongBreak: Int = 4,             // Nombre de cycles avant une pause longue
    val alarmSoundEnabled: Boolean = true,          // Activer ou non le son d'alarme
    val notificationsEnabled: Boolean = true        // Activer ou non les notifications
)
