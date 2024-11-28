package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository

class SetTimerStateUseCase(private val timerStateRepository: TimerStateRepository) {

    fun execute(workDuration: Long, breakDuration: Long): TimerState {
        // Met à jour les préférences du Timer dans le Repository
        return timerStateRepository.setTimerPreferences(workDuration, breakDuration)
    }
}