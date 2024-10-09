package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class StartTimerUseCase(private val timerRepository: TimerRepository) {
    fun execute(duration: Long): TimerState {
        return timerRepository.startTimer(duration)
    }
}