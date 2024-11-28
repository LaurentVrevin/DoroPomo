package com.laurentvrevin.doropomo.domain.usecase.preferences

import com.laurentvrevin.doropomo.domain.repository.DoNotDisturbRepository

class ToggleDoNotDisturbUseCase(
    private val doNotDisturbRepository: DoNotDisturbRepository
) {
    fun execute(isEnabled: Boolean) {
        doNotDisturbRepository.setDoNotDisturb(isEnabled)
    }
}