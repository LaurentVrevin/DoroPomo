package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class PauseTimerUseCase(private val timerRepository: TimerRepository) {
    fun execute(): TimerState {
        return timerRepository.pauseTimer()
        println("verifycycles - PauseTimerUseCase")
    }
}