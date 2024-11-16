package com.example.anti_theft.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.anti_theft.ui.MainActivity

object NotificationUtils {

    var notification: Notification? = null

    fun createNotification(
        context: Context,
        ChannelID: String?,
        channelName: String?,
        requestCode: Int,
        taskTitle: String,
        icon: Int
    ): Notification? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelName, ChannelID, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.setSound(null, null)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
            val intent = Intent(context, MainActivity::class.java) //start activity in notification
            val pendingIntent = PendingIntent.getActivity(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            if (taskTitle != "") {
                notification = NotificationCompat.Builder(context, ChannelID!!)
                    .setAutoCancel(false)
                    .setContentTitle(taskTitle)
                    .setContentText("Running...")
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(icon)
                    .build()
                val notificationManagerCompat = NotificationManagerCompat.from(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null
                }
                notificationManagerCompat.notify(123, notification!!)
            }
        }
        return notification
    }


}