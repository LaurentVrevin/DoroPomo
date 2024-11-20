package com.laurentvrevin.doropomo.domain.repository

import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState

interface PreferencesRepository {
    fun savePomodoroMode(mode: PomodoroMode, cycles: Int)
    fun getSavedTimerState(): TimerState
}