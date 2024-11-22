package com.laurentvrevin.doropomo.domain.usecase.preferences

import com.laurentvrevin.doropomo.domain.repository.PreferenceRepository

class ToggleDoNotDisturbUseCase(
    private val preferencesRepository: PreferenceRepository
) {
    fun execute(isEnabled: Boolean) {
        preferencesRepository.saveDoNotDisturbPreference(isEnabled)
    }
}