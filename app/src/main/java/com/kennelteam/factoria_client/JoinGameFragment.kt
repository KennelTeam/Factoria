package com.kennelteam.factoria_client

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.startButton.setOnClickListener {
            // start game
            this.findNavController().navigate(R.id.action_joinGameFragment_to_multiPlayerFragment)
        }

        binding.backButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        return binding.root
    }

}
