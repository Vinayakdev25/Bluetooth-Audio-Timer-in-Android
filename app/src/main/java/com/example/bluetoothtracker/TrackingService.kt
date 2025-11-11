package com.example.bluetoothtracker

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.content.IntentFilter
import android.os.Build

class TrackingService : Service() {

    private val receiver = BluetoothReceiver()

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)

        val mainIntent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification: Notification = NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID)
            .setContentTitle("Aravind's Timer — tracking")
            .setContentText("Listening for headset connect/disconnect")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pi)
            .build()

        startForeground(1, notification)

        val filter = IntentFilter()
        filter.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(receiver)
        } catch (e: Exception) {}
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
