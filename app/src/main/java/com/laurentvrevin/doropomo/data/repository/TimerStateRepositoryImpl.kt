    package com.laurentvrevin.doropomo.data.repository

    import com.laurentvrevin.doropomo.domain.model.TimerState
    import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository
    import javax.inject.Inject

    class TimerStateRepositoryImpl @Inject constructor() : TimerStateRepository {
        private var timerState = TimerState()

        override fun startTimer(duration: Long): TimerState {
            timerState = timerState.copy(
                startTime = System.currentTimeMillis(),
                isRunning = true
            )
            return timerState
        }

        override fun updateTimerState(timerState: TimerState) {
            this.timerState = timerState
        }

        override fun pauseTimer(): TimerState {
            if (timerState.isRunning) {
                // Calcule le temps écoulé depuis le démarrage
                val elapsedTime = System.currentTimeMillis() - timerState.startTime

                timerState = timerState.copy(
                    remainingTime = timerState.remainingTime - elapsedTime,
                    isRunning = false,
                    startTime = 0L // Réinitialise startTime car le timer est en pause
                )
            }
            return timerState
        }

        override fun resetTimer(): TimerState {
            timerState = timerState.copy(
                remainingTime = timerState.workDuration, // Réinitialise le temps restant à la durée de travail
                isRunning = false,
                isBreakTime = false
            )
            return timerState
        }

        override fun getTimerState(): TimerState {
            return timerState
        }

        override fun setTimerPreferences(workDuration: Long, breakDuration: Long): TimerState {
            timerState = timerState.copy(
                workDuration = workDuration,
                breakDuration = breakDuration,
                remainingTime = if (timerState.isRunning) timerState.remainingTime else workDuration,
                isRunning = false
            )
            return timerState
        }
    }