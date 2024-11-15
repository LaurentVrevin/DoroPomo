package com.laurentvrevin.doropomo.utils

import android.content.Context
import android.content.SharedPreferences
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("DoroPomoPreferences", Context.MODE_PRIVATE)

    companion object {
        private const val WORK_DURATION_KEY = "work_duration"
        private const val BREAK_DURATION_KEY = "break_duration"
        private const val CYCLES_KEY = "cycles_before_long_break"
    }

    // Sauvegarder les durées de travail et de pause
    fun savePomodoroMode(mode: PomodoroMode, cycles: Int) {
        sharedPreferences.edit()
            .putLong(WORK_DURATION_KEY, mode.workDuration)
            .putLong(BREAK_DURATION_KEY, mode.breakDuration)
            .putInt(CYCLES_KEY, cycles)
            .apply()
    }

    // Récupérer le mode Pomodoro sauvegardé
    fun getSavedPomodoroMode(): PomodoroMode {
        val workDuration = sharedPreferences.getLong(WORK_DURATION_KEY, 25 * 60 * 1000L)
        val breakDuration = sharedPreferences.getLong(BREAK_DURATION_KEY, 5 * 60 * 1000L)
        return PomodoroMode(workDuration, breakDuration, "${workDuration / 1000 / 60}/${breakDuration / 1000 / 60}")
    }

    // Obtenir un Flow pour observer les changements
    fun getPomodoroModeFlow(): Flow<PomodoroMode> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == WORK_DURATION_KEY || key == BREAK_DURATION_KEY) {
                trySend(getSavedPomodoroMode())
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }
    fun getSavedCycles(): Int {
        return sharedPreferences.getInt(CYCLES_KEY, 4) // Valeur par défaut : 4 cycles
    }
}