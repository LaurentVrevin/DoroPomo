package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class UpdateTimerStateUseCase(
    private val timerRepository: TimerRepository
) {
    fun execute(currentState: TimerState, elapsedTime: Long): TimerState {
        val newRemainingTime = currentState.remainingTime - elapsedTime
        val updatedState = currentState.copy(
            remainingTime = newRemainingTime.coerceAtLeast(0),
            isRunning = newRemainingTime > 0
        )
        timerRepository.updateTimerState(updatedState)
        return updatedState
    }
}