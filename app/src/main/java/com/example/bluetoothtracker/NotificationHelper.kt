package com.example.bluetoothtracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    const val CHANNEL_ID = "usage_channel"
    const val CHANNEL_NAME = "Headset usage notifications"

    fun createChannel(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            nm.createNotificationChannel(channel)
        }
    }

    fun sendUsageNotification(context: Context, minutes: Long) {
        createChannel(context)
        val title = "Headset session ended"
        val text = "Last session: ${'$'}{minutes} min"

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify((System.currentTimeMillis()%10000).toInt(), builder.build())
    }

    fun sendDailySummary(context: Context, totalMinutes: Long) {
        createChannel(context)
        val title = "Daily usage summary"
        val hours = totalMinutes / 60
        val mins = totalMinutes % 60
        val text = "You've used headset for ${'$'}hours h ${'$'}mins m today"

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(9999, builder.build())
    }
}
