package com.kennelteam.factoria_client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.MainScreenFragmentBinding

class MainScreenFragment : Fragment() {

    private lateinit var binding: MainScreenFragmentBinding

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
        Log.i("123", "ono zhe rabotalo")

        binding.multiplayerButton.setOnClickListener {
            Log.i("123", "multi")
            this.findNavController().navigate(R.id.action_mainScreenFragment_to_multiplayerFragment2)
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
