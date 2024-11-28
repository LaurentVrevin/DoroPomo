package com.laurentvrevin.doropomo.presentation.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.domain.entity.UserPreferences
import com.laurentvrevin.doropomo.domain.usecase.timer.FinishTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.PauseTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.GetUserPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.UpdateCycleCountUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.ResetTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.SetTimerStateUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.StartTimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoroPomoViewModel @Inject constructor(
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase,
    private val finishTimerUseCase: FinishTimerUseCase,
    private val playAlarmUseCase: PlayAlarmUseCase,
    private val stopAlarmUseCase: StopAlarmUseCase
) : ViewModel() {

    val isDarkTheme = mutableStateOf(false) // Gestion du thème

    private var timerJob: Job? = null

    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState

    fun observePreferences(preferencesFlow: StateFlow<UserPreferences>) {
        viewModelScope.launch {
            preferencesFlow.collect { preferences ->
                if (_timerState.value.isRunning) {
                    resetTimer()
                } else {
                    _timerState.value = _timerState.value.copy(
                        workDuration = preferences.workDuration,
                        remainingTime = preferences.workDuration, // Mettre à jour remainingTime
                        breakDuration = preferences.breakDuration,
                        cyclesBeforeLongBreak = preferences.cyclesBeforeLongBreak
                    )
                }
            }
        }
    }

    //--- GESTION DU TIMER ---//

    fun startTimer() {
        if (_timerState.value.isRunning) return
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            startTimerUseCase(
                timerState = _timerState.value,
                onTick = { updatedState ->
                    _timerState.value = updatedState
                },
                onFinish = {
                    onTimerFinish()
                }
            )
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _timerState.value = pauseTimerUseCase.execute()
    }


    fun resetTimer() {
        timerJob?.cancel()
        _timerState.value = resetTimerUseCase.execute()
    }

    private fun onTimerFinish() {
        val updatedState = finishTimerUseCase.execute(_timerState.value)
        _timerState.value = updatedState
    }


    private fun playAlarm() {
        playAlarmUseCase.execute()
    }

    fun stopAlarm() {
        stopAlarmUseCase.execute()
    }

    //--- GESTION DU THÈME ---//

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}