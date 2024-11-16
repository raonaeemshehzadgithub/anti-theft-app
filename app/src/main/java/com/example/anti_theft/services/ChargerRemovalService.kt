package com.example.anti_theft.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import com.example.anti_theft.R
import com.example.anti_theft.broadcastreceivers.ChargingStatusReceiver
import com.example.anti_theft.utils.AppUtils
import com.example.anti_theft.utils.Constants
import com.example.anti_theft.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChargerRemovalService : Service() {


    @Inject
    lateinit var chargerReceiver: ChargingStatusReceiver
    val REQUEST_CODE = 101

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val chargerDisconnected = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(chargerReceiver, chargerDisconnected)
        sendServiceiSStared()
        return START_STICKY
    }


    private fun sendServiceiSStared() {
        sendBroadcast(Intent(Constants.SERVICE_STATUS).apply {
            putExtra(Constants.CHARGING_SERVICE_RUNNING, true)
        })
    }


    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {

        ServiceCompat.startForeground(
            this,
            0,
            NotificationUtils.createNotification(
                this,
                Constants.CHARGER_REMOVAL_SERVICE_CHANNEL_ID,
                Constants.CHARGER_REMOVAL_SERVICE_CHANNEL,
                REQUEST_CODE,
                Constants.CHARGER_REMOVAL_SERVICE_TITLE,
                R.drawable.ic_charge
            )!!,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            } else {
                0
            }
        )
    }


    // :: TODO On Destroy service unregister your receiver
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(chargerReceiver)
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
