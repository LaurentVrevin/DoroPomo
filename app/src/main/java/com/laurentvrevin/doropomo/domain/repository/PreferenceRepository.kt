package com.laurentvrevin.doropomo.domain.repository

import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState

interface PreferenceRepository {
    fun savePomodoroPreferences(mode: PomodoroMode, cycles: Int)
    fun getSavedTimerState(): TimerState
    fun saveDoNotDisturbPreference(enabled: Boolean)
    fun isDoNotDisturbEnabled(): Boolean
}