package com.ventustium.mqtt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.eclipse.paho.client.mqttv3.MqttMessage


class MainActivity : AppCompatActivity() {

    lateinit var broker: String
    lateinit var topic: String

    val mqttClient: MqttClient by lazy{
        MqttClient(this)
    }

    lateinit var m1 : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectAndSubscribe()

        findViewById<Button>(R.id.button3).setOnClickListener {
            val i = Intent(this, DriverActivity1::class.java).apply {

            }
            startActivity(i)
        }
    }


    fun getMessage(view: View) {
        val msgEditText = findViewById<EditText>(R.id.editTextMessage)
        mqttClient.publishMessage(topic, msgEditText.text.toString())
//        val msg = msgEditText.text.toString()
        }


    private fun connectAndSubscribe() {
        broker = "tcp://iot.ventustium.com:1883"
        topic = "AndroidMqtt"
        mqttClient.connect(broker)
        mqttClient.setCallBack(arrayOf(topic),::updateButton)
    }

    private fun updateButton(topic: String, message : MqttMessage) { //tampilkan message
        m1 = String(message.payload)
        findViewById<TextView>(R.id.textView).apply {
            text=m1
        }
    }
}