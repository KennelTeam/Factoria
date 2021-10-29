package com.kennelteam.factoria_client

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.MainScreenFragmentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Thread.sleep
import java.net.InetAddress
import java.net.Socket
import java.nio.charset.StandardCharsets

class MainScreenFragment : Fragment() {

    private lateinit var binding: MainScreenFragmentBinding
    private lateinit var din: DataInputStream
    private lateinit var dout: DataOutputStream

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_screen_fragment,
            container,
            false
        )

        binding.multiplayerButton.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Game mode")

            dialogBuilder.setPositiveButton("Create game", DialogInterface.OnClickListener { _, _ ->
                Params.isJoining = false
                this.findNavController().navigate(R.id.action_mainScreenFragment_to_createGameFragment)
            })

            dialogBuilder.setNegativeButton("Join game", DialogInterface.OnClickListener { _, _ ->
                this.findNavController().navigate(R.id.action_mainScreenFragment_to_joinGameFragment)
            })

            dialogBuilder.show()
        }

        binding.singleplayerButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainScreenFragment_to_singlePlayer)
        }

        binding.settingsButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainScreenFragment_to_settingsFragment)
        }

        binding.statisticsButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainScreenFragment_to_statisticksFragment)
        }

        binding.aboutButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainScreenFragment_to_aboutFragment)
        }

        return binding.root
    }
}
