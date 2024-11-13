package com.laurentvrevin.doropomo.domain.entity

// Représente un mode Pomodoro
data class PomodoroMode(
    val workDuration: Long,  // Durée de travail en millisecondes
    val breakDuration: Long, // Durée de pause en millisecondes
    val label: String        // Nom du mode
)

// Liste des modes prédéfinis
val predefinedModes = listOf(
    PomodoroMode(1 * 60 * 1000L, 5 * 60 * 1000L, "25/5"),
    PomodoroMode(50 * 60 * 1000L, 10 * 60 * 1000L, "50/10"),
    PomodoroMode(90 * 60 * 1000L, 20 * 60 * 1000L, "90/20")
)
