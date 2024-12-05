package com.laurentvrevin.doropomo.presentation.viewmodel


import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.model.TimerState
import com.laurentvrevin.doropomo.domain.model.UserPreferences
import com.laurentvrevin.doropomo.domain.usecase.timer.FinishTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.PauseTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.ResetTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.StartTimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerStateViewModel @Inject constructor(
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase,
    private val finishTimerUseCase: FinishTimerUseCase,
    private val playAlarmUseCase: PlayAlarmUseCase,
    private val stopAlarmUseCase: StopAlarmUseCase
) : ViewModel() {

    private var timerJob: Job? = null

    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState

    private val _timeRemaining = MutableStateFlow(60L) // Temps restant en secondes
    val timeRemaining: StateFlow<Long> = _timeRemaining

    private val _isRunning = MutableStateFlow(false) // État du décompte
    val isRunning: StateFlow<Boolean> = _isRunning

    private var timer: CountDownTimer? = null


    fun observePreferences(preferencesFlow: StateFlow<UserPreferences>) {
        viewModelScope.launch {
            preferencesFlow.collect { preferences ->
                if (_timerState.value.workDuration != preferences.workDuration ||
                    _timerState.value.breakDuration != preferences.breakDuration
                ) {
                    _timerState.value = _timerState.value.copy(
                        workDuration = preferences.workDuration,
                        breakDuration = preferences.breakDuration,
                        remainingTime = preferences.workDuration // Réinitialise le temps restant
                    )

                    // Si le timer est en cours d'exécution, redémarre avec les nouvelles valeurs
                    if (_isRunning.value) {
                        timer?.cancel()
                        resetCountDown()


                    }

                    Log.d("TimerStateViewModel", "Updated work duration to ${preferences.workDuration}")
                }
            }
        }
    }

    fun startCountdown() {
        if (_isRunning.value) return // Évite de démarrer un nouveau timer si déjà en cours

        val remainingTime = _timerState.value.remainingTime
        timer?.cancel() // Annule le timer précédent pour éviter tout conflit

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
    /*
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
    }*/

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


}