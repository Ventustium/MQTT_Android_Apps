package com.ventustium.mqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.eclipse.paho.client.mqttv3.MqttMessage

class BikeActivity1 : AppCompatActivity() {

    private lateinit var broker: String

    private val mqttClient: MqttClient by lazy {
        MqttClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike)

        connectAndSubscribe()
    }

    private fun connectAndSubscribe() {
        broker = "tcp://iot.ventustium.com:1883"
        val topics = arrayOf(
            "/driver/heartrate",
            "/driver/temperature",
            "/driver/spo2",
            "/driver/status",

            "/bike/status",
            "/bike/fwheel",
            "/bike/bwheel",
            "/bike/battery",
            "/bike/voltage",
            "/bike/amps",
            "/bike/power",
            "/bike/speed",
            "/bike/range",
            "/bike/estimation",
            "count"
            )
        mqttClient.connect(broker)

        mqttClient.setCallBack(topics, ::updateButton)

    }

    private fun updateButton(topic: String, message: MqttMessage) {
        when (topic) {
            "count" -> {
                findViewById<TextView>(R.id.count).apply {
                    val msg = "Ventustium Count" + String(message.payload)
                    text = msg
                }
            }
            "/driver/heartrate" -> {
                findViewById<TextView>(R.id.textViewDriver).apply {
                    val msg = String(message.payload) + " BPM"
                    text = msg
                }
            }
            "/driver/temperature" -> {
                findViewById<TextView>(R.id.textViewDriverTemp).apply {
                    val msg = String(message.payload) + "°C"
                    text = msg
                }
            }
            "/driver/spo2" -> {
                findViewById<TextView>(R.id.textViewDriver5).apply {
                    val msg = String(message.payload) + "%"
                    text = msg
                }
            }
            "/driver/status" -> {
                findViewById<TextView>(R.id.textViewDriver6).apply {
                    val msg = String(message.payload)
                    text = msg
                }
            }

            "/bike/status" -> {
                findViewById<TextView>(R.id.textViewBikeStatus).apply {
                    val msg = String(message.payload)
                    text = msg
                }
            }
            "/bike/fwheel" -> {
                findViewById<TextView>(R.id.textViewBikeFW).apply {
                    val msg = String(message.payload) + "psi"
                    text = msg
                }
            }
            "/bike/bwheel" -> {
                findViewById<TextView>(R.id.textViewBikeBW).apply {
                    val msg = String(message.payload) + "psi"
                    text = msg
                }
            }
            "/bike/battery" -> {
                findViewById<TextView>(R.id.textViewBikeBattery).apply {
                    val msg = String(message.payload) + "Wh"
                    text = msg
                }
            }
            "/bike/voltage" -> {
                findViewById<TextView>(R.id.textViewBikeVoltage).apply {
                    val msg = String(message.payload) + "V"
                    text = msg
                }
            }
            "/bike/amps" -> {
                findViewById<TextView>(R.id.textViewBikeCurrent).apply {
                    val msg = String(message.payload) + "A"
                    text = msg
                }
            }
            "/bike/power" -> {
                findViewById<TextView>(R.id.textViewBikePower).apply {
                    val msg = String(message.payload) + "W"
                    text = msg
                }
            }
            "/bike/speed" -> {
                findViewById<TextView>(R.id.textViewBikeSpeed).apply {
                    val msg = String(message.payload) + "KM/H"
                    text = msg
                }
            }
            "/bike/range" -> {
                findViewById<TextView>(R.id.textViewBikeRange).apply {
                    val msg = String(message.payload) +"KM"
                    text = msg
                }
            }
            "/bike/estimation" -> {
                findViewById<TextView>(R.id.textViewEstimation).apply {
                    val msg = String(message.payload) + "KM"
                    text = msg
                }
            }
        }
    }
}
