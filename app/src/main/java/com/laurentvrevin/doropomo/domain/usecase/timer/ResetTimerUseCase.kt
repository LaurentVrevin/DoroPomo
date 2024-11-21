package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.manager.PreferenceManager
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class ResetTimerUseCase(
    private val timerRepository: TimerRepository,
    private val preferenceManager: PreferenceManager
) {
    fun execute(): TimerState {

        val savedState = preferenceManager.getSavedTimerState()
        return timerRepository.resetTimer().copy(
            remainingTime = savedState.workDuration,
            isRunning = false,
            isBreakTime = false
        )
    }

}