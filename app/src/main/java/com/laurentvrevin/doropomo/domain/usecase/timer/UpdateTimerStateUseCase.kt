package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository

class UpdateTimerStateUseCase(
    private val timerStateRepository: TimerStateRepository
) {
    fun execute(currentState: TimerState, elapsedTime: Long): TimerState {
        val newRemainingTime = currentState.remainingTime - elapsedTime
        val updatedState = currentState.copy(
            remainingTime = newRemainingTime.coerceAtLeast(0),
            isRunning = newRemainingTime > 0
        )
        timerStateRepository.updateTimerState(updatedState)
        return updatedState
    }
}