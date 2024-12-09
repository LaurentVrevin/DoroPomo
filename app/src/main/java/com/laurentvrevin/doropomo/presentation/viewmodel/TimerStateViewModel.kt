package com.laurentvrevin.doropomo.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.model.TimerState
import com.laurentvrevin.doropomo.domain.model.UserPreferences
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerStateViewModel @Inject constructor(
    private val playAlarmUseCase: PlayAlarmUseCase,
    private val stopAlarmUseCase: StopAlarmUseCase
) : ViewModel() {

    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState

    private val _isRunning = MutableStateFlow(false) // État du décompte
    val isRunning: StateFlow<Boolean> = _isRunning

    private var timer: CountDownTimer? = null

    fun observePreferences(preferencesFlow: StateFlow<UserPreferences>) {
        viewModelScope.launch {
            preferencesFlow.collect { preferences ->

                if (_timerState.value.workDuration != preferences.workDuration ||
                    _timerState.value.breakDuration != preferences.breakDuration ||
                    _timerState.value.cyclesBeforeLongBreak != preferences.cyclesBeforeLongBreak
                ) {
                    _timerState.value = _timerState.value.copy(
                        workDuration = preferences.workDuration,
                        breakDuration = preferences.breakDuration,
                        remainingTime = preferences.workDuration,
                        cyclesBeforeLongBreak = preferences.cyclesBeforeLongBreak
                    )

                    if (_isRunning.value) {
                        timer?.cancel()
                        resetCountDown()
                    }
                }
            }
        }
    }

    fun startCountdown() {
        if (_isRunning.value) return

        val remainingTime = _timerState.value.remainingTime
        timer?.cancel()

        timer = object : CountDownTimer(remainingTime, 200) {
            override fun onTick(millisUntilFinished: Long) {
                _timerState.value = _timerState.value.copy(
                    remainingTime = millisUntilFinished
                )
            }
            override fun onFinish() {
                _isRunning.value = false
                _timerState.value = _timerState.value.copy(
                    remainingTime = 0L
                )
                playAlarm()
            }
        }
        timer?.start()
        _isRunning.value = true
    }

    fun pauseCountdown() {
        timer?.cancel()
        _isRunning.value = false
    }

    fun resetCountDown() {
        timer?.cancel()
        _timerState.value = _timerState.value.copy(
            remainingTime = _timerState.value.workDuration
        )
        _isRunning.value = false
    }

    private fun playAlarm() {
        playAlarmUseCase.execute()
    }

    fun stopAlarm() {
        stopAlarmUseCase.execute()
    }

}