package com.example.anti_theft.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import com.example.anti_theft.R
import com.example.anti_theft.sensors.PocketDetectionSensor
import com.example.anti_theft.utils.Constants
import com.example.anti_theft.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PocketDetectionService : Service() {

    @Inject
    lateinit var pocketDetectionSensor: PocketDetectionSensor

    val REQUEST_CODE = 102


    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // :: TODO Register proximity sensor for detecting motion detection
        pocketDetectionSensor.start()
        sendServiceiSStared()
        return START_STICKY
    }

    private fun sendServiceiSStared() {
        sendBroadcast(Intent(Constants.SERVICE_STATUS).apply {
            putExtra(Constants.POCKET_SERVICE_RUNNING, true)
        })
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {

        ServiceCompat.startForeground(
            this,
            0,
            NotificationUtils.createNotification(
                this,
                Constants.POCKET_SERVICE_CHANNEL_ID,
                Constants.POCKET_SERVICE_CHANNEL,
                REQUEST_CODE,
                Constants.POCKET_SERVICE_TITLE,
                R.drawable.ic_launcher_foreground
            )!!,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            } else {
                0
            }
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        pocketDetectionSensor.stop()
    }
}
