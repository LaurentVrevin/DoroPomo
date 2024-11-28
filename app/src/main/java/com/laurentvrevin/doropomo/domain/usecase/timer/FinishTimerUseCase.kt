package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase

class FinishTimerUseCase(
    private val playAlarmUseCase: PlayAlarmUseCase,
    private val stopAlarmUseCase: StopAlarmUseCase,
    private val timerStateRepository: TimerStateRepository
) {
    fun execute(currentState: TimerState): TimerState {
        // Jouer l'alarme
        playAlarmUseCase.execute()


        // Calculer l'état suivant (pause ou travail)
        val isBreak = !currentState.isBreakTime
        return currentState.copy(
            isRunning = false,
            isBreakTime = isBreak,
            remainingTime = if (isBreak) currentState.breakDuration else currentState.workDuration
        )
    }
}