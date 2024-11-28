package com.laurentvrevin.doropomo.data.repository

import com.laurentvrevin.doropomo.data.manager.UserPreferencesStorage
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.UserPreferences
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository
import javax.inject.Inject


class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesStorage: UserPreferencesStorage
) : UserPreferencesRepository {


    fun savePomodoroPreferences(mode: PomodoroMode, cycles: Int) {
        userPreferencesStorage.saveUserPreferences(
            UserPreferences(
                workDuration = mode.workDuration,
                breakDuration = mode.breakDuration,
                cyclesBeforeLongBreak = cycles
            )
        )
    }

    override fun getUserPreferences(): UserPreferences {
        return userPreferencesStorage.getUserPreferences()
    }

    override fun saveUserPreferences(preferences: UserPreferences) {
        userPreferencesStorage.saveUserPreferences(preferences)
    }
}
