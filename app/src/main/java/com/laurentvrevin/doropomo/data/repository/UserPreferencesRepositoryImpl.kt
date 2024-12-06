package com.laurentvrevin.doropomo.data.repository

import com.laurentvrevin.doropomo.data.source.UserPreferencesStorage
import com.laurentvrevin.doropomo.domain.model.UserPreferences
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository
import javax.inject.Inject


class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesStorage: UserPreferencesStorage
) : UserPreferencesRepository {

    override fun getUserPreferences(): UserPreferences {
        return userPreferencesStorage.getUserPreferences()
    }

    override fun saveUserPreferences(preferences: UserPreferences) {
        userPreferencesStorage.saveUserPreferences(preferences)
    }

}
