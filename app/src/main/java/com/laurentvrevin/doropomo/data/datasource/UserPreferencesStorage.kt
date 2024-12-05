package com.laurentvrevin.doropomo.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.laurentvrevin.doropomo.domain.model.UserPreferences


class UserPreferencesStorage(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("DoroPomoPreferences", Context.MODE_PRIVATE)

    companion object {
        private const val WORK_DURATION_KEY = "work_duration"
        private const val BREAK_DURATION_KEY = "break_duration"
        private const val LONG_BREAK_KEY = "long_break_duration"
        private const val CYCLES_KEY = "cycles_before_long_break"
        private const val DO_NOT_DISTURB_KEY = "do_not_disturb_key"
        private const val DARK_THEME_KEY = "dark_theme"
    }

    fun getUserPreferences(): UserPreferences {
        return UserPreferences(
            workDuration = sharedPreferences.getLong(WORK_DURATION_KEY, 25 * 60 * 1000L),
            breakDuration = sharedPreferences.getLong(BREAK_DURATION_KEY, 5 * 60 * 1000L),
            longBreakDuration = sharedPreferences.getLong(LONG_BREAK_KEY, 15 * 60 * 1000L),
            cyclesBeforeLongBreak = sharedPreferences.getInt(CYCLES_KEY, 4),
            isDarkTheme = sharedPreferences.getBoolean(DARK_THEME_KEY, false),
            isDoNotDisturbEnabled = sharedPreferences.getBoolean(DO_NOT_DISTURB_KEY, false)
        )
    }

    fun saveUserPreferences(preferences: UserPreferences) {
        sharedPreferences.edit()
            .putLong(WORK_DURATION_KEY, preferences.workDuration)
            .putLong(BREAK_DURATION_KEY, preferences.breakDuration)
            .putLong(LONG_BREAK_KEY, preferences.longBreakDuration)
            .putInt(CYCLES_KEY, preferences.cyclesBeforeLongBreak)
            .putBoolean(DARK_THEME_KEY, preferences.isDarkTheme)
            .putBoolean(DO_NOT_DISTURB_KEY, preferences.isDoNotDisturbEnabled)
            .apply()
    }
}