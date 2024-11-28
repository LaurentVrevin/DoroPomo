package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository

class ResetTimerUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    fun execute(): TimerState {
        val userPreferences = userPreferencesRepository.getUserPreferences()
        return TimerState(
            workDuration = userPreferences.workDuration,
            remainingTime = userPreferences.workDuration, // Synchronisé avec workDuration
            breakDuration = userPreferences.breakDuration,
            cyclesBeforeLongBreak = userPreferences.cyclesBeforeLongBreak,
            isRunning = false,
            isBreakTime = false
        )
    }

}