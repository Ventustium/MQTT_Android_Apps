package com.ventustium.mqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.eclipse.paho.client.mqttv3.MqttMessage

class BikeActivity1 : AppCompatActivity() {

    lateinit var broker: String

    val mqttClient: MqttClient by lazy {
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
            "/bike/estimation"
            )
        mqttClient.connect(broker)

        mqttClient.setCallBack(topics, ::updateButton)

    }

    private fun updateButton(topic: String, message: MqttMessage) {
        when (topic) {
            "/driver/heartrate" -> {
                findViewById<TextView>(R.id.textViewDriver).apply {
                    text = String(message.payload) + " BPM"
                }
            }
            "/driver/temperature" -> {
                findViewById<TextView>(R.id.textViewDriverTemp).apply {
                    text = String(message.payload) + "°C"
                }
            }
            "/driver/spo2" -> {
                findViewById<TextView>(R.id.textViewDriver5).apply {
                    text = String(message.payload) + "%"
                }
            }
            "/driver/status" -> {
                findViewById<TextView>(R.id.textViewDriver6).apply {
                    text = String(message.payload)
                }
            }

            "/bike/status" -> {
                findViewById<TextView>(R.id.textViewBikeStatus).apply {
                    text = String(message.payload)
                }
            }
            "/bike/fwheel" -> {
                findViewById<TextView>(R.id.textViewBikeFW).apply {
                    text = String(message.payload) + "psi"
                }
            }
            "/bike/bwheel" -> {
                findViewById<TextView>(R.id.textViewBikeBW).apply {
                    text = String(message.payload) + "psi"
                }
            }
            "/bike/battery" -> {
                findViewById<TextView>(R.id.textViewBikeBattery).apply {
                    text = String(message.payload) + "Wh"
                }
            }
            "/bike/voltage" -> {
                findViewById<TextView>(R.id.textViewBikeVoltage).apply {
                    text = String(message.payload) + "V"
                }
            }
            "/bike/amps" -> {
                findViewById<TextView>(R.id.textViewBikeCurrent).apply {
                    text = String(message.payload) + "A"
                }
            }
            "/bike/power" -> {
                findViewById<TextView>(R.id.textViewBikePower).apply {
                    text = String(message.payload) + "W"
                }
            }
            "/bike/speed" -> {
                findViewById<TextView>(R.id.textViewBikeSpeed).apply {
                    text = String(message.payload) + "KM/H"
                }
            }
            "/bike/range" -> {
                findViewById<TextView>(R.id.textViewBikeRange).apply {
                    text = String(message.payload) +"KM"
                }
            }
            "/bike/estimation" -> {
                findViewById<TextView>(R.id.textViewEstimation).apply {
                    text = String(message.payload) + "KM"
                }
            }
        }
    }
}
