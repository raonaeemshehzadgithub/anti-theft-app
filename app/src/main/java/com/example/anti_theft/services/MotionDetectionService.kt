package com.example.anti_theft.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import com.example.anti_theft.R
import com.example.anti_theft.sensors.AccelerometerSensor
import com.example.anti_theft.utils.AppUtils
import com.example.anti_theft.utils.Constants
import com.example.anti_theft.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MotionDetectionService : Service() {


    @Inject
    lateinit var accelerometerSensor: AccelerometerSensor

    val REQUEST_CODE = 100


    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // :: TODO Register accelerometer sensor for detecting motion detection
        accelerometerSensor.start()
        sendServiceiSStared()
        return START_STICKY
    }


    private fun sendServiceiSStared() {
        sendBroadcast(Intent(Constants.SERVICE_STATUS).apply {
            putExtra(Constants.MOTION_SERVICE_RUNNING, true)
        })
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {

        ServiceCompat.startForeground(
            this,
            0,
            NotificationUtils.createNotification(
                this,
                Constants.MOTION_DETECTION_SERVICE_CHANNEL_ID,
                Constants.MOTION_DETECTION_SERVICE_CHANNEL,
                REQUEST_CODE,
                Constants.MOTION_DETECTION_SERVICE_TITLE,
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
        accelerometerSensor.stop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
