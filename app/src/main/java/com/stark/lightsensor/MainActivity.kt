package com.stark.lightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity(), SensorEventListener {

    var lightSensor : Sensor? = null
    var sensorManager : SensorManager? = null
    var constraintLayoutBackground: ConstraintLayout? = null
    var textView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground)
        textView = findViewById(R.id.textView)

    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
        constraintLayoutBackground?.setBackgroundColor(resources.getColor(R.color.black))
    }

    var isSensorRunning = false


    override fun onSensorChanged(event: SensorEvent?) {
        textView!!.setText("" + event!!.values[0])

        if (event!!.values[0] > 33 && !isSensorRunning) {
            isSensorRunning = true
            var mediaPlayer = MediaPlayer.create(this, R.raw.sound)
            constraintLayoutBackground!!.background = getDrawable(R.drawable.monster)

            try {
                   mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                    isSensorRunning = false
                }
            } catch (e: Exception) {
            }

        } else constraintLayoutBackground?.setBackgroundColor(resources.getColor(R.color.black))
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}