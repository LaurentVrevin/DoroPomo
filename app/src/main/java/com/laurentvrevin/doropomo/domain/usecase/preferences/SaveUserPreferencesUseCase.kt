package com.laurentvrevin.doropomo.domain.usecase.preferences

import com.laurentvrevin.doropomo.domain.model.UserPreferences
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository

class SaveUserPreferencesUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    fun execute(preferences: UserPreferences) {
        userPreferencesRepository.saveUserPreferences(preferences)
    }
}