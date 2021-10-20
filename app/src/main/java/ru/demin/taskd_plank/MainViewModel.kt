package ru.demin.taskd_plank

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.demin.taskd_plank.recycler.AddItem
import ru.demin.taskd_plank.recycler.TimeItem
import java.lang.Math.abs
import java.util.*

class MainViewModel(private val context: Application) : ViewModel() {
    private var sensorManager: SensorManager =
        context.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
    private var sensorAccel: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var sensorMagnet: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private var valuesAccel = FloatArray(3)
    private var valuesMagnet = FloatArray(3)
    private var valuesResult = FloatArray(3)

    private var r = FloatArray(9)

    private val handler = Handler(Looper.getMainLooper())
    private val tickRunnable = object : Runnable {
        override fun run() {
            getDeviceOrientation()
            handler.postDelayed(this, TICK_PERIOD)
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

    val viewState = MutableLiveData(ViewState(listOf(AddItem())))

    init {
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun onAddClick() {
        if (viewState.value?.attemptState != null) return
        viewState.value = viewState.value!!.copy(attemptState = ViewState.AttemptState.Prepare)
        tickRunnable.run()
    }

    fun getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet)
        SensorManager.getOrientation(r, valuesResult)
        valuesResult[0] = kotlin.math.abs(Math.toDegrees(valuesResult[0].toDouble()).toFloat())
        valuesResult[1] = kotlin.math.abs(Math.toDegrees(valuesResult[1].toDouble()).toFloat())
        valuesResult[2] = kotlin.math.abs(Math.toDegrees(valuesResult[2].toDouble()).toFloat())

        Log.d("Povarity", "${valuesResult[0]} ${valuesResult[1]} ${valuesResult[2]}")

        val isPlank =
            valuesResult[1] <= PLANK_X_ANGLE + PLANK_X_DELTA && valuesResult[1] >= PLANK_X_ANGLE - PLANK_X_DELTA
                && valuesResult[2] <= PLANK_Y_ANGLE + PLANK_Y_DELTA && valuesResult[2] >= PLANK_Y_ANGLE - PLANK_Y_DELTA

        when {
            isPlank && viewState.value!!.attemptState is ViewState.AttemptState.Prepare -> {
                viewState.value = viewState.value!!.copy(attemptState = ViewState.AttemptState.InProgress(0))
            }

            isPlank && viewState.value!!.attemptState is ViewState.AttemptState.InProgress -> {
                viewState.value =
                    viewState.value!!.copy(attemptState = ViewState.AttemptState.InProgress(getCurrentPlankTime() + (TICK_PERIOD / MILLIS_IN_SECOND).toInt()))

            }
            !isPlank && viewState.value!!.attemptState is ViewState.AttemptState.InProgress -> {
                viewState.value = viewState.value!!.copy(
                    attemptState = null,
                    items = viewState.value!!.items.toMutableList().apply { add(0,TimeItem(getCurrentPlankTime())) })
                handler.removeCallbacks(tickRunnable)
            }
        }
    }

    private fun getCurrentPlankTime() =
        (viewState.value!!.attemptState as? ViewState.AttemptState.InProgress)?.currentTimeInSeconds ?: 0

    companion object {
        private const val TICK_PERIOD = 1000L
        private const val MILLIS_IN_SECOND = 1000L
        private const val PLANK_X_ANGLE = 30
        private const val PLANK_X_DELTA = 20
        private const val PLANK_Y_ANGLE = 90
        private const val PLANK_Y_DELTA = 30
    }
}