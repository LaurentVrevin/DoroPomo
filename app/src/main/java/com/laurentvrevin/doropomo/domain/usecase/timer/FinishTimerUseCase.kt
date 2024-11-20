package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerRepository

class FinishTimerUseCase(
    private val playAlarmUseCase: PlayAlarmUseCase,
    private val stopAlarmUseCase: StopAlarmUseCase,
    private val timerRepository: TimerRepository
) {
    fun execute(currentState: TimerState): TimerState {
        // Jouer l'alarme
        playAlarmUseCase.execute()
        stopAlarmUseCase.execute()

        // Calculer l'état suivant (pause ou travail)
        val isBreak = !currentState.isBreakTime
        return currentState.copy(
            isRunning = false,
            isBreakTime = isBreak,
            remainingTime = if (isBreak) currentState.breakDuration else currentState.workDuration
        )
    }
}