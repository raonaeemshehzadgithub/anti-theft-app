package com.example.anti_theft.extensions

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.bumptech.glide.util.Util.isOnMainThread

fun Activity.toast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), length)
}

fun Activity.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    try {
        if (isOnMainThread()) {
            doToast(this, msg, length)
        } else {
            Handler(Looper.getMainLooper()).post {
                doToast(this, msg, length)
            }
        }
    }catch (_:java.lang.Exception){}
}

private fun doToast(context: Activity, message: String, length: Int) {
    if (true) {
        if (!context.isFinishing && !context.isDestroyed) {
            Toast.makeText(context, message, length).show()
        }
    } else {
        Toast.makeText(context, message, length).show()
    }
}
