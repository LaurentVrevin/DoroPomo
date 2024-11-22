package com.laurentvrevin.doropomo.domain.usecase.preferences

import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.PreferenceRepository

class UpdateCycleCountUseCase (
    private val preferenceRepository: PreferenceRepository
    ) {
        fun execute(currentState: TimerState, newCycleCount: Int): TimerState {
            // Sauvegarder les cycles dans les préférences
            preferenceRepository.savePomodoroPreferences(
                PomodoroMode(
                    currentState.workDuration,
                    currentState.breakDuration,
                    "${currentState.workDuration / 1000 / 60}/${currentState.breakDuration / 1000 / 60}"
                ),
                newCycleCount
            )

            // Retourner un état mis à jour avec le nouveau nombre de cycles
            return currentState.copy(cyclesBeforeLongBreak = newCycleCount)
        }
}