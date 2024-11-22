package com.laurentvrevin.doropomo.data.repository

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import com.laurentvrevin.doropomo.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val context: Context
) : AlarmRepository {

    private var ringtone: Ringtone? = null

    override fun playAlarm() {
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone?.play()
    }

    override fun stopAlarm() {
        ringtone?.stop()
    }
}