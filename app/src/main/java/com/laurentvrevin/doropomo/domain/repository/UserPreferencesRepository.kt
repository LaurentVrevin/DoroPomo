package com.laurentvrevin.doropomo.domain.repository


import com.laurentvrevin.doropomo.domain.entity.UserPreferences

interface UserPreferencesRepository {

    fun getUserPreferences(): UserPreferences
    fun saveUserPreferences(preferences: UserPreferences)
}