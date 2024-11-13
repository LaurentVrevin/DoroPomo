package com.laurentvrevin.doropomo.utils

import android.content.Context
import android.content.SharedPreferences
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("DoroPomoPreferences", Context.MODE_PRIVATE)

    companion object {
        private const val WORK_DURATION_KEY = "work_duration"
        private const val BREAK_DURATION_KEY = "break_duration"
    }

    // Sauvegarder les durées de travail et de pause
    fun savePomodoroMode(mode: PomodoroMode) {
        sharedPreferences.edit()
            .putLong(WORK_DURATION_KEY, mode.workDuration)
            .putLong(BREAK_DURATION_KEY, mode.breakDuration)
            .apply()
    }

    // Récupérer le mode Pomodoro sauvegardé, avec valeurs par défaut
    fun getSavedPomodoroMode(): PomodoroMode {
        val workDuration = sharedPreferences.getLong(WORK_DURATION_KEY, 25 * 60 * 1000L)
        val breakDuration = sharedPreferences.getLong(BREAK_DURATION_KEY, 5 * 60 * 1000L)
        return PomodoroMode(workDuration, breakDuration, "${workDuration / 1000 / 60}/${breakDuration / 1000 / 60}")
    }
}