package com.laurentvrevin.doropomo.domain.repository


import com.laurentvrevin.doropomo.domain.model.UserPreferences

interface UserPreferencesRepository {
    fun getUserPreferences(): UserPreferences
    fun saveUserPreferences(preferences: UserPreferences)
}