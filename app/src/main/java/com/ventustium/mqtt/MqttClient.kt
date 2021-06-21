package com.ventustium.mqtt

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.MqttClient
import java.lang.Exception

class MqttClient (private val context: Context) {
   lateinit var client: MqttAndroidClient

   companion object{
      const val TAG = "MqttClient"
   }

   fun connect(broker: String) {
      client = MqttAndroidClient(context, broker, MqttClient.generateClientId())
      client.connect()
      Log.d(TAG, "Connection Status" + client.isConnected)
      }

   fun publishMessage(topic: String, msg: String) {
      try {
         val message = MqttMessage()
         message.payload = msg.toByteArray()
         Log.d(TAG, "$msg published to $topic")
         client.publish(topic, message.payload, 0, true)
      } catch (e: Exception) {
         Log.d(TAG, "Error publishing to $topic" + e.message) // e.reasonCode
         e.printStackTrace()
      }
   }

   fun subscribeTopic(topic: String, qos: Int = 0) {
      client.subscribe(topic, qos).actionCallback = object : IMqttActionListener {
         override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d(TAG, "Subscribed to $topic")
         }

         override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d(TAG, "Failed to subscribe to $topic")
            exception?.printStackTrace()
         }
      }
   }

   fun setCallBack(topics: Array<String> ? = null,
                   messageCallback: ((topic: String, message: MqttMessage)->Unit)? = null) {
      client.setCallback(object : MqttCallbackExtended {
         override fun connectComplete(reconnect: Boolean, serverURI: String?) {
            topics?.forEach {
               subscribeTopic(it)
            }
            Log.d(TAG, "Connected to: $serverURI")
         }
         @Throws(Exception::class)
         override fun messageArrived(topic: String, message: MqttMessage) {

            messageCallback?.invoke(topic, message)
            Log.d(TAG, "Incoming message from $topic : "+ message.toString())
            }

         override fun connectionLost(cause: Throwable?) {
            Log.d(TAG, "Connection lost")
         }

         override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d(TAG, "Message delivery complete")
         }
      })
   }

   fun disconnect() {
      if (client.isConnected)
         client.disconnect()
   }

   fun close(){
      client.apply {
         unregisterResources()
         close()
      }
   }
}