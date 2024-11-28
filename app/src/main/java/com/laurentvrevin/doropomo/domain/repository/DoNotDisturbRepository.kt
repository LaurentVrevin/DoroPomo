package com.laurentvrevin.doropomo.domain.repository

interface DoNotDisturbRepository {
    fun isDoNotDisturbEnabled(): Boolean
    fun setDoNotDisturb(enabled: Boolean)
    fun requestPermission()
}