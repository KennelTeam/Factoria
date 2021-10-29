package com.kennelteam.factoria_client

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Contextual
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Thread.sleep
import java.net.InetAddress
import java.net.Socket
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Communicator {
    private lateinit var din: DataInputStream
    private lateinit var dout: DataOutputStream
    val data = MutableLiveData<HashMap<String, String>>()

    fun init() {


        GlobalScope.launch(Dispatchers.Default) {
            val sock = Socket(InetAddress.getByName("100.92.211.196"), 10001)
            dout = DataOutputStream(sock.getOutputStream())
            dout.flush()
            Thread.sleep(1000)
            din = DataInputStream(sock.getInputStream())
            procIncoming()
        }
    }

    fun sendMessage(message: String) {
        GlobalScope.launch {
            dout.write(message.toByteArray())
        }
    }

    private fun procIncoming() {
        while (true) {
            val msg = readMessage()
            val spl = msg.split("}{").toMutableList()
            for(i in spl.indices) {
                if (i != 0) {
                    spl[i] = "{" + spl[i]
                }
                if (i != spl.size - 1) {
                    spl[i] = spl[i] + "}"
                }
                val dec = Json.decodeFromString<HashMap<String, String>>(spl[i])
                data.postValue(dec)
                Log.i("1234e", dec.toString())
            }
        }
    }

    private fun readMessage(): String {
        val buf = ByteArray(1024)
        val size = din.read(buf)
        val msg = String(buf, 0, size, StandardCharsets.UTF_8)
        Log.i("1234i", msg)
        return msg
    }
}