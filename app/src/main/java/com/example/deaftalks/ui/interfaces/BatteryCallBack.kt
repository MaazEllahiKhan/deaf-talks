package com.example.deaftalks.ui.interfaces

interface BatteryCallback {
    fun onBatteryPercentageUpdated(percentage: Int)
    fun onBatteryChargingStatusUpdated(isCharging: Boolean)
}