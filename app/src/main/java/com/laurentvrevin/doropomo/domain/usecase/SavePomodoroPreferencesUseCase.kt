package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.data.layer.PreferencesManager
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode

class SavePomodoroPreferencesUseCase(
    private val preferencesManager: PreferencesManager
) {
    fun execute(mode: PomodoroMode, cycles: Int) {
        preferencesManager.savePomodoroMode(mode, cycles)
    }
}