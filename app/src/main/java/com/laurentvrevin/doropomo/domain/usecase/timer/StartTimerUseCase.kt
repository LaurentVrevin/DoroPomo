package com.laurentvrevin.doropomo.domain.usecase.timer

import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.repository.TimerRepository
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import kotlinx.coroutines.delay

class StartTimerUseCase(
    private val timerRepository: TimerRepository,
    private val updateTimerStateUseCase: UpdateTimerStateUseCase,
    private val playAlarmUseCase: PlayAlarmUseCase
) {
    suspend operator fun invoke(
        timerState: TimerState,
        onTick: (TimerState) -> Unit,
        onFinish: () -> Unit
    ) {
        var currentState = timerRepository.startTimer(timerState.remainingTime)
        onTick(currentState)

        val startTime = System.currentTimeMillis()
        var lastUpdateTime = startTime

        while (currentState.isRunning && currentState.remainingTime > 0) {
            val currentTime = System.currentTimeMillis()
            val elapsed = currentTime - lastUpdateTime
            if (elapsed >= 1000) {
                currentState = updateTimerStateUseCase.execute(currentState, 1000)
                onTick(currentState)
                lastUpdateTime += 1000
            }
            if (!currentState.isRunning || currentState.remainingTime <= 0) {
                playAlarmUseCase.execute() // Sonner l'alarme
                onFinish()
                break
            }
            delay(50)
        }
    }
}