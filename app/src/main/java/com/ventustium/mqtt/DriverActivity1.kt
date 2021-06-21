package com.ventustium.mqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.eclipse.paho.client.mqttv3.MqttMessage

class DriverActivity1 : AppCompatActivity() {

    lateinit var broker : String
    lateinit var topic: String

    val mqttClient : MqttClient by lazy{
        MqttClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver1)

        connectAndSubscribe()
    }

    private fun connectAndSubscribe() {
        broker = "tcp://iot.ventustium.com:1883"
        topic = "/driver/heartrate"
        mqttClient.connect(broker)
        mqttClient.setCallBack(arrayOf(topic),::updateButton)
    }

    private fun updateButton(topic : String, message : MqttMessage){
        val heartrate = String(message.payload)

        findViewById<TextView>(R.id.textViewDriver).apply{
            text = heartrate
        }
    }
}