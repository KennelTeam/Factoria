package com.kennelteam.factoria_client

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Thread.sleep
import java.net.InetAddress
import java.net.Socket
import java.nio.charset.StandardCharsets

object Communicator {
    private lateinit var din: DataInputStream
    private lateinit var dout: DataOutputStream

    fun init() {
        GlobalScope.launch {
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

        }
    }

    private fun readMessage(): String {
        val buf = ByteArray(1024)
        val size = din.read(buf)
        return String(buf, 0, size, StandardCharsets.UTF_8)
    }
}