package com.example.anti_theft.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.anti_theft.R

object AppUtils {

    lateinit var mediaPlayer: MediaPlayer

    fun playSeiren(context: Context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.charge_complete_ringtone)
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun stopSeiren() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

}