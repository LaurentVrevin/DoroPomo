package com.laurentvrevin.doropomo.domain.usecase

import com.laurentvrevin.doropomo.domain.repository.AlarmRepository
import javax.inject.Inject

class PlayAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    fun execute() {
        alarmRepository.playAlarm()
    }
}