package com.laurentvrevin.doropomo.domain.usecase.preferences

import com.laurentvrevin.doropomo.domain.model.UserPreferences
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository

class GetUserPreferencesUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    fun execute(): UserPreferences {
        return userPreferencesRepository.getUserPreferences()
    }
}