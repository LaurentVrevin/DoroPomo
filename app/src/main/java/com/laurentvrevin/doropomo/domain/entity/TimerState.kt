package com.laurentvrevin.doropomo.domain.entity

data class TimerState(
    val startTime: Long = 0L,                   // Heure de début (timestamp)
    val remainingTime: Long = 25 * 60 * 1000L, // Temps restant en millisecondes
    val workDuration: Long = 25 * 60 * 1000L,  // Durée de travail
    val breakDuration: Long = 5 * 60 * 1000L,  // Durée de pause
    val cyclesBeforeLongBreak: Int = 4,        // Nombre de cycles avant une longue pause
    val isRunning: Boolean = false,            // Indique si le minuteur est actif
    val isBreakTime: Boolean = false           // Indique si c'est une pause

)