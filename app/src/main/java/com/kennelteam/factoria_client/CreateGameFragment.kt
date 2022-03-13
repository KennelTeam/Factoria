package com.kennelteam.factoria_client

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.CreateGameFragmentBinding

class CreateGameFragment : Fragment() {

    private lateinit var binding: CreateGameFragmentBinding
    private var enemyName = Params.enemyName

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.create_game_fragment,
            container,
            false
        )

        Communicator.sendMessage("""{"message_type": "create_room", "nickname": "Host"}""")
        Params.dividersCount = -1
        Params.enemyName = ""

        Communicator.data.observe(viewLifecycleOwner, Observer {
            if (it["message_type"] == "room_created") {
                binding.roomIdText.text = "ID: " + it["room_id"]
            } else if (it["message_type"] == "connected_player") {
                enemyName = it["nickname"]!!
                binding.startButton.visibility = View.VISIBLE
                binding.backButton.visibility = View.INVISIBLE
                binding.EnemyName.text = enemyName
                Log.i("1234", "Welcome, $enemyName")
            } else if (it["message_type"] == "disconnected_player") {
                Log.i("1234", "Goodbye, $enemyName")
                enemyName = ""
                binding.EnemyName.text = "waiting..."
                binding.backButton.visibility = View.VISIBLE
                binding.startButton.visibility = View.INVISIBLE
            }
        })

        Communicator.data.observe(viewLifecycleOwner) {
            if (it["message_type"] == "start_game") {
                val dividersCount = it["dividers_count"]!!.toInt()
                Params.dividersCount = dividersCount
                Params.enemyName = enemyName
                this.findNavController()
                    .navigate(R.id.action_createGameFragment_to_multiPlayerFragment)
            }
        }

        binding.startButton.setOnClickListener {
            Communicator.sendMessage("""{"message_type": "start_game"}""")
        }

        binding.backButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        return binding.root
    }

}
