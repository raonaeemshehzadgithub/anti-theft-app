package com.example.anti_theft.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.anti_theft.utils.AppUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PocketDetectionSensor @Inject constructor(@ApplicationContext private val context: Context) :
    SensorEventListener {

    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val proximitySensor get()=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)


    fun start() {
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] == proximitySensor!!.maximumRange) {
                // :: TODO When device will not be in pocket then play seiren
               AppUtils.playSeiren(context)
            } else {
                // :: TODO When device will be in pocket
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }
}