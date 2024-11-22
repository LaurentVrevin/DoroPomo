package com.laurentvrevin.doropomo.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("DoroPomoPreferences", Context.MODE_PRIVATE)

    companion object {
        private const val WORK_DURATION_KEY = "work_duration"
        private const val BREAK_DURATION_KEY = "break_duration"
        private const val CYCLES_KEY = "cycles_before_long_break"
        private const val DO_NOT_DISTURB_KEY = "do_not_disturb"
    }

    fun savePomodoroMode(mode: PomodoroMode, cycles: Int) {
        sharedPreferences.edit()
            .putLong(WORK_DURATION_KEY, mode.workDuration)
            .putLong(BREAK_DURATION_KEY, mode.breakDuration)
            .putInt(CYCLES_KEY, cycles)
            .apply()
    }

    fun getSavedTimerState(): TimerState {
        val workDuration = sharedPreferences.getLong(WORK_DURATION_KEY, 25 * 60 * 1000L)
        val breakDuration = sharedPreferences.getLong(BREAK_DURATION_KEY, 5 * 60 * 1000L)
        val cycles = sharedPreferences.getInt(CYCLES_KEY, 4)
        return TimerState(
            workDuration = workDuration,
            breakDuration = breakDuration,
            remainingTime = workDuration,
            cyclesBeforeLongBreak = cycles
        )
    }

    private fun getSavedPomodoroMode(): PomodoroMode {
        val workDuration = sharedPreferences.getLong(WORK_DURATION_KEY, 25 * 60 * 1000L)
        val breakDuration = sharedPreferences.getLong(BREAK_DURATION_KEY, 5 * 60 * 1000L)
        return PomodoroMode(workDuration, breakDuration, "${workDuration / 1000 / 60}/${breakDuration / 1000 / 60}")
    }

    fun getPomodoroModeFlow(): Flow<PomodoroMode> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key in listOf(WORK_DURATION_KEY, BREAK_DURATION_KEY, CYCLES_KEY)) {
                trySend(getSavedPomodoroMode())
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    fun saveDoNotDisturbPreference(isEnabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean("DO_NOT_DISTURB_KEY", isEnabled)
            .apply()
    }

    fun isDoNotDisturbEnabled(): Boolean {
        return sharedPreferences.getBoolean(DO_NOT_DISTURB_KEY, false)
    }

}
