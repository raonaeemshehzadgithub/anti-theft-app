package com.example.anti_theft.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

abstract class BaseActivity : AppCompatActivity() {


    private var isPermissionTaken: (Boolean) -> Unit = {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view)
        onActivityCreated()
    }

    override fun onStart() {
        super.onStart()
        onActivityStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        onActivityRemoved()
    }


    abstract val view: View

    abstract fun onActivityCreated(): Unit
    abstract fun onActivityStart(): Unit
    abstract fun onActivityRemoved(): Unit


    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            // :: TODO Check If Permission Granted
            if (result) {
                isPermissionTaken.invoke(true)
            }
        }


    fun takePermission(isTaken: (Boolean) -> Unit) {
        this.isPermissionTaken = isTaken
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                isPermissionTaken.invoke(false)
            }
        } else {
            isPermissionTaken.invoke(true)
        }

    }

}