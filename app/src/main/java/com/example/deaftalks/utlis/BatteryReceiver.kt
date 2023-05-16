package com.example.deaftalks.utlis

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.widget.Toast
import com.example.deaftalks.ui.interfaces.BatteryCallback

class BatteryReceiver(private val callback: BatteryCallback) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }

        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val batteryPercentage = (level.toFloat() / scale.toFloat() * 100).toInt()

        callback.onBatteryPercentageUpdated(batteryPercentage)

        // Show battery percentage
       // Toast.makeText(context, "Battery Percentage: $batteryPercentage%", Toast.LENGTH_SHORT).show()

        // Show battery status
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
        callback.onBatteryChargingStatusUpdated(isCharging = isCharging)
//        if (isCharging) {
//            Toast.makeText(context, "Battery is charging", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Battery is not charging", Toast.LENGTH_SHORT).show()
//        }
    }
}
