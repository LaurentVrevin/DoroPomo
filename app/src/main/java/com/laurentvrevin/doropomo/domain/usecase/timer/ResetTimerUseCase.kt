package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.model.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository

class ResetTimerUseCase(
    private val timerStateRepository: TimerStateRepository
) {
    fun execute(): TimerState {
        return timerStateRepository.resetTimer()
    }
}