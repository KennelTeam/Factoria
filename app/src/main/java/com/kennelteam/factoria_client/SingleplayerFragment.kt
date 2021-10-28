package com.kennelteam.factoria_client

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennelteam.factoria_client.databinding.SingleplayerFragmentBinding

class SingleplayerFragment : Fragment() {

    private lateinit var binding: SingleplayerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.singleplayer_fragment,
            container,
            false
        )

        return binding.root
    }

}
