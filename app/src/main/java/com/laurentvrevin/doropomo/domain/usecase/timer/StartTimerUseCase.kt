package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.model.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import kotlinx.coroutines.delay

class StartTimerUseCase(
    private val timerStateRepository: TimerStateRepository,
) {
    suspend operator fun invoke(
        timerState: TimerState,
        onTick: (TimerState) -> Unit,
        onFinish: () -> Unit
    ) {
        var currentState = timerStateRepository.startTimer(timerState.remainingTime)

        while (currentState.isRunning && currentState.remainingTime > 0) {
            val elapsedTime = System.currentTimeMillis() - currentState.startTime

            currentState = currentState.copy(
                remainingTime = timerState.remainingTime - elapsedTime
            )

            onTick(currentState)

            if (currentState.remainingTime <= 0) {
                onFinish()
                break
            }

            delay(1000) // Attente d'une seconde entre chaque mise à jour
        }
    }
}