package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository

class PauseTimerUseCase(private val timerStateRepository: TimerStateRepository) {
    fun execute(): TimerState {
        return timerStateRepository.pauseTimer()
        println("verifycycles - PauseTimerUseCase")
    }
}