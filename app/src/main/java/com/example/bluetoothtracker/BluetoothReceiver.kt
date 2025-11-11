package com.example.bluetoothtracker

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BluetoothReceiver : BroadcastReceiver() {

    companion object {
        var startTime: Long? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

        if (device == null) return

        when (action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                // simple filter for headset classes
                val devClass = device.bluetoothClass?.deviceClass
                if (devClass == BluetoothDevice.DEVICE_CLASS_AUDIO_VIDEO_HEADPHONES ||
                    devClass == BluetoothDevice.DEVICE_CLASS_AUDIO_VIDEO_WEARABLE_HEADSET ||
                    devClass == BluetoothDevice.DEVICE_CLASS_AUDIO_VIDEO_LOUDSPEAKER) {
                    startTime = System.currentTimeMillis()
                    Log.d("BTTracker", "Headset connected at $startTime")
                }
            }

            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                val end = System.currentTimeMillis()
                startTime?.let { st ->
                    val minutes = (end - st) / 1000 / 60
                    // insert into DB
                    val db = UsageDatabase.getDatabase(context)
                    db.usageDao().insert(UsageSession(System.currentTimeMillis(), minutes))
                    // notify via NotificationHelper
                    NotificationHelper.sendUsageNotification(context, minutes)
                }
                startTime = null
            }
        }
    }
}
