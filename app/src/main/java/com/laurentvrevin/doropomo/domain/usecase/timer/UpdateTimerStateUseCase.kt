package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.model.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository

class UpdateTimerStateUseCase(
    private val timerStateRepository: TimerStateRepository
) {
    fun execute(timerState: TimerState, elapsedTime: Long): TimerState {
        val remainingTime = timerState.remainingTime - elapsedTime

        val updatedState = timerState.copy(
            remainingTime = remainingTime,
            isRunning = remainingTime > 0
        )

        timerStateRepository.updateTimerState(updatedState)
        return updatedState
    }
}