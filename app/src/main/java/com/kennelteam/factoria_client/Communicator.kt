package com.kennelteam.factoria_client

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket
import java.nio.charset.StandardCharsets
import kotlin.collections.HashMap
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

object Communicator {
    private lateinit var din: DataInputStream
    private lateinit var dout: DataOutputStream
    val data = MutableLiveData<HashMap<String, String>>()
    var connected = false
    private var serverAddr: String = ""

    private fun getServerAddr(appName: String): String? {
        val gitText =
            URL("https://raw.githubusercontent.com/KennelTeam/AppRoutes/main/README.md").readText()
        gitText.split("## Routes")[1].split('\n').forEach {
            val params = it.split(" - ")
            if (params[0] == appName) {
                return params[1]
            }
        }
        return null
    }

    private fun connectServer() {
        while(!connected) {
            try {
                val sock = Socket(
                    InetAddress.getByName(serverAddr.split(':')[0]),
                    serverAddr.split(':')[1].toInt()
                )
                dout = DataOutputStream(sock.getOutputStream())
                dout.flush()
                Thread.sleep(1000)
                din = DataInputStream(sock.getInputStream())
                connected = true
            } catch (e: Exception) {
                Thread.sleep(2000)
                continue
            }
        }
    }

    fun init() {
        GlobalScope.launch(Dispatchers.Default) {
            while (serverAddr == "") {
                try {
                    serverAddr = getServerAddr("factoria") ?: return@launch
                } catch (e: Exception) {
                    Thread.sleep(3000)
                }
            }
            while (true) {
                connectServer()
                procIncoming()
                Thread.sleep(5000)
            }
        }
//        Uncomment the code below to test monitoring connection with server feature
//        GlobalScope.launch {
//            while (true) {
//                Log.i("12345t", connected.toString())
//                Thread.sleep(500)
//            }
//        }
    }

    fun sendMessage(message: String) {
        GlobalScope.launch {
            dout.write(message.toByteArray())
        }
    }

    private fun procIncoming() {
        while (connected) {
            var msg: String
            try {
                msg = readMessage()
            }
            catch (e: Exception) {
                connected = false
                break
            }
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