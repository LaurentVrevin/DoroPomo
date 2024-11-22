package com.laurentvrevin.doropomo.data.repository

import com.laurentvrevin.doropomo.data.manager.DoNotDisturbManager
import com.laurentvrevin.doropomo.data.manager.PreferenceManager
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.PreferenceRepository
import javax.inject.Inject


class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager, // Injection du PreferencesManager
    private val doNotDisturbManager: DoNotDisturbManager
) : PreferenceRepository {

    override fun savePomodoroPreferences(mode: PomodoroMode, cycles: Int) {
        preferenceManager.savePomodoroMode(mode, cycles)
    }

    override fun getSavedTimerState(): TimerState {
        return preferenceManager.getSavedTimerState()
    }

    override fun saveDoNotDisturbPreference(enabled: Boolean) {
        // Sauvegarde l'état dans les préférences
        preferenceManager.saveDoNotDisturbPreference(enabled)

        // Applique l'état avec DoNotDisturbManager
        if (enabled) {
            doNotDisturbManager.enableDoNotDisturb()
        } else {
            doNotDisturbManager.disableDoNotDisturb()
        }
    }

    override fun isDoNotDisturbEnabled(): Boolean {
        return preferenceManager.isDoNotDisturbEnabled()
    }


}