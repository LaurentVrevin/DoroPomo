package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class SetTimerPreferencesUseCase(private val timerRepository: TimerRepository) {

    fun execute(workDuration: Long, breakDuration: Long): TimerState {
        // Met à jour les préférences du Timer dans le Repository
        return timerRepository.setTimerPreferences(workDuration, breakDuration)
    }
}