package com.kennelteam.factoria_client

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.MultiplayerFragmentBinding

class MultiPlayerFragment : Fragment() {

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

        binding.backButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        return binding.root
    }

}
