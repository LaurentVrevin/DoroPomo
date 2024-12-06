package com.laurentvrevin.doropomo.data.repository

import com.laurentvrevin.doropomo.data.source.DoNotDisturbManager
import com.laurentvrevin.doropomo.domain.repository.DoNotDisturbRepository
import javax.inject.Inject

class DoNotDisturbRepositoryImpl @Inject constructor(
    private val doNotDisturbManager: DoNotDisturbManager
    ) : DoNotDisturbRepository {

        override fun isDoNotDisturbEnabled(): Boolean {
            return doNotDisturbManager.isPermissionGranted()
        }

        override fun setDoNotDisturb(enabled: Boolean) {
            if (enabled) {
                doNotDisturbManager.enableDoNotDisturb()
            } else {
                doNotDisturbManager.disableDoNotDisturb()
            }
        }

        override fun requestPermission() {
            doNotDisturbManager.requestPermission()
        }
}