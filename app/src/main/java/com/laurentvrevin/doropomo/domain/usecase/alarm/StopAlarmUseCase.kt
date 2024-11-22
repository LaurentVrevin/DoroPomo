package com.laurentvrevin.doropomo.domain.usecase.alarm

import com.laurentvrevin.doropomo.domain.repository.AlarmRepository
import javax.inject.Inject

class StopAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    fun execute() {
        alarmRepository.stopAlarm()
    }
}