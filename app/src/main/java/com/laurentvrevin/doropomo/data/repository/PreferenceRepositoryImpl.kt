package com.laurentvrevin.doropomo.data.repository

import com.laurentvrevin.doropomo.data.layer.PreferencesManager
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.PreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager // Injection du PreferencesManager
) : PreferencesRepository {

    override fun savePomodoroMode(mode: PomodoroMode, cycles: Int) {
        preferencesManager.savePomodoroMode(mode, cycles)
    }

    override fun getSavedTimerState(): TimerState {
        return preferencesManager.getSavedTimerState()
    }
}