package com.kennelteam.factoria_client

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennelteam.factoria_client.databinding.MultiplayerFragmentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class MultiplayerFragment : Fragment() {

    private lateinit var binding: MultiplayerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.multiplayer_fragment,
            container,
            false
        )

        GlobalScope.launch {
            serverComms()
        }

        return binding.root
    }

    private fun serverComms() {
        val addr = "192.168.0.1"
        val port = 8080

        val sock = Socket(addr, port)
        val dout = DataOutputStream(sock.getOutputStream())
        val din = DataInputStream(sock.getInputStream())

        val input = byteArrayOf()
        din.readFully(input)
    }

}