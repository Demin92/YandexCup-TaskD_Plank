package ru.demin.taskd_plank

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAccel: Sensor
    private lateinit var sensorMagnet: Sensor

    private var valuesAccel = FloatArray(3)
    private var valuesMagnet = FloatArray(3)
    private var valuesResult = FloatArray(3)

    private var r = FloatArray(9)

    private val timer = Timer()
    private val task = object : TimerTask() {
        override fun run() {
            getDeviceOrientation()
        }
    }

    private val listener = object : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent?) {
            if (event == null) return
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> valuesAccel = event.values
                Sensor.TYPE_MAGNETIC_FIELD -> valuesMagnet = event.values
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        sensorManager.registerListener(listener, sensorAccel, SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(listener, sensorMagnet, SENSOR_DELAY_NORMAL)


        timer.schedule(task, 0, 400)
    }

    fun getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet)
        SensorManager.getOrientation(r, valuesResult)
        valuesResult[0] = Math.toDegrees(valuesResult[0].toDouble()).toFloat()
        valuesResult[1] = Math.toDegrees(valuesResult[1].toDouble()).toFloat()
        valuesResult[2] = Math.toDegrees(valuesResult[2].toDouble()).toFloat()

        Log.d("Povarity", "${valuesResult[0]} ${valuesResult[1]} ${valuesResult[2]}")
    }
}