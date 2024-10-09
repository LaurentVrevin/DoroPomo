package com.laurentvrevin.doropomo.domain.entity

data class TimerState(
    val startTime: Long = 0L,
    val remainingTime: Long = 25 * 60 * 1000L,  // Temps restant par défaut (25 minutes)
    val workDuration: Long = 25 * 60 * 1000L,   // Durée de travail personnalisée par l'utilisateur
    val breakDuration: Long = 5 * 60 * 1000L,   // Durée de pause personnalisée par l'utilisateur
    val isRunning: Boolean = false,
    val isBreakTime: Boolean = false            // Indique si c'est le moment de la pause
)