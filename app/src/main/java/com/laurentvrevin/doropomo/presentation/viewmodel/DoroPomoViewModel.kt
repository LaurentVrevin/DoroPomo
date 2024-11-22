package com.laurentvrevin.doropomo.presentation.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
import com.laurentvrevin.doropomo.domain.entity.TimerState
import com.laurentvrevin.doropomo.data.manager.PreferenceManager
import com.laurentvrevin.doropomo.domain.usecase.timer.FinishTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.PauseTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.SavePomodoroPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.SetTimerPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.UpdateCycleCountUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.ResetTimerUseCase
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
    private val savePomodoroPreferencesUseCase: SavePomodoroPreferencesUseCase,
    private val setTimerPreferencesUseCase: SetTimerPreferencesUseCase,
    private val updateCycleCountUseCase: UpdateCycleCountUseCase,
    private val preferenceManager: PreferenceManager,
    private val finishTimerUseCase: FinishTimerUseCase,
    private val playAlarmUseCase: PlayAlarmUseCase,
    private val stopAlarmUseCase: StopAlarmUseCase
) : ViewModel() {

    val isDarkTheme = mutableStateOf(false) // Gestion du thème

    private var timerJob: Job? = null

    private val _timerState = MutableStateFlow(preferenceManager.getSavedTimerState())
    val timerState: StateFlow<TimerState> = _timerState


    init {
        viewModelScope.launch {

            loadSavedTimerState()

            preferenceManager.getPomodoroModeFlow().collect { mode ->
                _timerState.value = _timerState.value.copy(
                    workDuration = mode.workDuration,
                    breakDuration = mode.breakDuration,
                    remainingTime = mode.workDuration
                )
            }
        }
    }

    private fun loadSavedTimerState(){
        _timerState.value = preferenceManager.getSavedTimerState()
    }

    //--- GESTION DU TIMER ---//

    fun startTimer() {
        if (_timerState.value.isRunning) return

        timerJob?.cancel() // Annuler tout job précédent

        timerJob = viewModelScope.launch {
            startTimerUseCase(
                timerState = _timerState.value,
                onTick = { updatedState ->
                    _timerState.value = updatedState
                },
                onFinish = {
                    onTimerFinish() // Réinitialiser ou effectuer une autre action
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

    //--- GESTION DES PRÉFÉRENCES ---//


    fun savePomodoroPreferences(mode: PomodoroMode, cycles: Int) {
        _timerState.value = savePomodoroPreferencesUseCase.execute(mode, cycles)
        stopTimerAfterSaved() // Assure que le timer est arrêté avec l'ancien état
    }

    fun updateCycleCount(newCycleCount: Int) {
        _timerState.value = updateCycleCountUseCase.execute(_timerState.value, newCycleCount)
    }

    private fun stopTimerAfterSaved() {
        timerJob?.cancel() // Annuler le chrono en cours
    }

    fun updateTimerPreferences(workDuration: Long, breakDuration: Long, cycles: Int) {
        val newTimerState = setTimerPreferencesUseCase.execute(workDuration, breakDuration)
        _timerState.value = newTimerState.copy(remainingTime = newTimerState.workDuration)
        cycles
        savePomodoroPreferences(PomodoroMode(workDuration, breakDuration, "${workDuration / 1000 / 60}/${breakDuration / 1000 / 60}"), cycles)
    }

    //--- GESTION DU CYCLE ET DES PAUSES ---//

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