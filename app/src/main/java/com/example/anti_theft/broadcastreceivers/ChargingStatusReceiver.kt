package com.example.anti_theft.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.anti_theft.utils.AppUtils
import javax.inject.Inject

class ChargingStatusReceiver @Inject constructor() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_POWER_DISCONNECTED) {
            context?.let { AppUtils.playSeiren(it) }
        }
    }
}