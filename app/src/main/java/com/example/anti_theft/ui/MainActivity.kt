package com.example.anti_theft.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.anti_theft.R
import com.example.anti_theft.databinding.ActivityMainBinding
import com.example.anti_theft.extensions.toast
import com.example.anti_theft.services.ChargerRemovalService
import com.example.anti_theft.services.MotionDetectionService
import com.example.anti_theft.services.PocketDetectionService
import com.example.anti_theft.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override val view: View get() = binding.root


    override fun onActivityCreated() {
        setClickListeners()
    }


    private fun setClickListeners() {
        binding.apply {
            pocketRmBtn.setOnClickListener {
                takePermission { isGranted ->
                    if (isGranted) {
                        startPocketRemovalService()
                    } else {
                        toast("Permission not granted!")
                    }
                }
            }
            chargerRmBtn.setOnClickListener {
                takePermission { isGranted ->
                    if (isGranted) {
                        startChargerRemovalService()
                    } else {
                        toast("Permission not granted!")
                    }
                }
            }
            motionDetectBtn.setOnClickListener {
                takePermission { isGranted ->
                    if (isGranted) {
                        startMotionDetectionService()
                    } else {
                        toast("Permission not granted!")
                    }

                }
            }
        }

    }


    override fun onActivityStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(
                serviceStatusReceiver, IntentFilter(Constants.SERVICE_STATUS),
                RECEIVER_NOT_EXPORTED
            )
        }
    }

    override fun onActivityRemoved() {
        unregisterReceiver(serviceStatusReceiver)
    }


    private fun startChargerRemovalService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(Intent(this, ChargerRemovalService::class.java))
        }
    }

    private fun startPocketRemovalService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(Intent(this, PocketDetectionService::class.java))
        }
    }

    private fun startMotionDetectionService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(Intent(this, MotionDetectionService::class.java))
        }
    }


    private val serviceStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Constants.SERVICE_STATUS -> {
                    if (intent.extras!!.getBoolean(Constants.POCKET_SERVICE_RUNNING)) {
                        binding.pocketRmBtn.text = "Pocket Removal Service Started"
                    } else if (intent.extras!!.getBoolean(Constants.MOTION_SERVICE_RUNNING)) {
                        binding.motionDetectBtn.text = "Motion Service Started"
                    } else if (intent.extras!!.getBoolean(Constants.CHARGING_SERVICE_RUNNING)) {
                        binding.chargerRmBtn.text = "Charging Service Started"
                    }
                }
            }
        }
    }


}