package com.example.anti_theft.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.anti_theft.utils.AppUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AccelerometerSensor @Inject constructor(@ApplicationContext private val context: Context) : SensorEventListener {

    private var lastZ: Float = 0f
    private var threshold = 9.0f


    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer get()=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    fun start() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }



    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            // :: TODO Calculate the magnitude of acceleration (magnitude of the gravity vector)
            val magnitude = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            //:: TODO Check if the device is being moving
            if (magnitude > threshold && lastZ < threshold) {
                // :: TODO Play Serene
                AppUtils.playSeiren(context)
            }
            // :: TODO Store the last z-value (to compare with the current value)
            lastZ = z

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}