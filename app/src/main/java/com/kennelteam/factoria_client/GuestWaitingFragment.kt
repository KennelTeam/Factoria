package com.kennelteam.factoria_client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.GuestWaitingFragmentBinding

class GuestWaitingFragment : Fragment() {

    private lateinit var binding: GuestWaitingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.guest_waiting_fragment,
            container,
            false
        )

        Communicator.data.observe(viewLifecycleOwner) {
            if (it["message_type"] == "start_game") {
                val dividersCount = it["dividers_count"]!!.toInt()
                Params.dividersCount = dividersCount
//                Params.enemyName = enemyName
                this.findNavController()
                    .navigate(R.id.action_guestWaitingFragment_to_multiPlayerFragment)
            } else if (it["message_type"] == "result") {
                Log.i("12345", "result!")
                val action = MultiPlayerFragmentDirections.actionMultiPlayerFragmentToMultiPlayerFinishedFragment()
                action.message = it["result"]!!
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

}