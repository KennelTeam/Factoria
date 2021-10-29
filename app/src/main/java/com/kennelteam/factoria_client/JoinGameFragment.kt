package com.kennelteam.factoria_client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.JoinGameFragmentBinding

class JoinGameFragment : Fragment() {

    private lateinit var binding: JoinGameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.join_game_fragment,
            container,
            false
        )

        binding.joinButton.setOnClickListener {
            Params.isJoining = true
            Communicator.data.observe(viewLifecycleOwner, Observer {
                Log.i("12345r", it.toString())
                if (it["message_type"] == "response") {
                    Log.i("12345r", it.toString())
                    if (it["status"] == "True") {
                        this.findNavController().navigate(R.id.action_joinGameFragment_to_createGameFragment)
                    }
                }
                if (it["message_type"] == "connected_player") {
                    Params.enemyName = it["nickname"]!!
                }
            })
            Communicator.sendMessage("""
                {
                    "message_type": "connect_to_room",
                    "room_id": 1,
                    "nickname": "impostor"
                }
            """.trimIndent())
        }

        binding.backButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        return binding.root
    }

}
