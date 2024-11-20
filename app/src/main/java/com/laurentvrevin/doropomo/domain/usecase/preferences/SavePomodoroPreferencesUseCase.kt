package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.manager.PreferenceManager
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class SavePomodoroPreferencesUseCase(
    private val preferenceManager: PreferenceManager,
    private val timerRepository: TimerRepository // Si on veut y sauvegarder l'état
) {
    fun execute(mode: PomodoroMode, cycles: Int): TimerState {
        // Sauvegarder les préférences
        preferenceManager.savePomodoroMode(mode, cycles)

        // Créer un nouvel état
        val updatedState = TimerState(
            workDuration = mode.workDuration,
            breakDuration = mode.breakDuration,
            remainingTime = mode.workDuration,
            cyclesBeforeLongBreak = cycles,
            isRunning = false,
            isBreakTime = false
        )

        // (Optionnel) Persister l'état dans le repository
        timerRepository.updateTimerState(updatedState)

        return updatedState
    }
}