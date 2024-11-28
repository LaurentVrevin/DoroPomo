package com.laurentvrevin.doropomo.domain.usecase.preferences

import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository

class UpdateCycleCountUseCase (
    private val userPreferencesRepository: UserPreferencesRepository
    ) {
        fun execute(currentState: TimerState, newCycleCount: Int): TimerState {
            // Sauvegarder les cycles dans les préférences
            //userPreferencesRepository.saveUserPreferences()

            // Retourner un état mis à jour avec le nouveau nombre de cycles
            return currentState.copy(cyclesBeforeLongBreak = newCycleCount)
        }
}