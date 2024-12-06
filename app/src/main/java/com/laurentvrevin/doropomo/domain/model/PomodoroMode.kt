package com.laurentvrevin.doropomo.domain.model

data class PomodoroMode(
    val workDuration: Long,
    val breakDuration: Long,
    val label: String
)

val predefinedModes = listOf(
    PomodoroMode(25 * 60 * 1000L, 5 * 60 * 1000L, "25/5"),
    PomodoroMode(50 * 60 * 1000L, 10 * 60 * 1000L, "50/10"),
    PomodoroMode(90 * 60 * 1000L, 20 * 60 * 1000L, "90/20")
)
