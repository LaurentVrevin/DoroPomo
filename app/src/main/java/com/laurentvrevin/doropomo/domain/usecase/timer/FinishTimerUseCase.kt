package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.model.TimerState
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase

class FinishTimerUseCase(
    private val playAlarmUseCase: PlayAlarmUseCase,
) {
    fun execute(currentState: TimerState): TimerState {
        playAlarmUseCase.execute()

        val isBreak = !currentState.isBreakTime
        return currentState.copy(
            isRunning = false,
            isBreakTime = isBreak,
            remainingTime = if (isBreak) currentState.breakDuration else currentState.workDuration
        )
    }
}