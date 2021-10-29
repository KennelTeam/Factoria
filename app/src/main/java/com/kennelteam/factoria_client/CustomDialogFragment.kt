package com.kennelteam.factoria_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.CustomDialogFragmentBinding

class CustomDialogFragment: DialogFragment() {

    private lateinit var binding: CustomDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.custom_dialog_fragment,
            container,
            false
        )

        dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_round_corner)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.createGameButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainScreenFragment_to_createGameFragment)
            this.dialog?.cancel()
        }
        binding.joinGameButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_mainScreenFragment_to_joinGameFragment)
            this.dialog?.cancel()
        }
    }

}
