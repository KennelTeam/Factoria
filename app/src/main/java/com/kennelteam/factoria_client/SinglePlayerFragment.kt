package com.kennelteam.factoria_client

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.SinglePlayerFragmentBinding
import com.kennelteam.factoria_client.game.Game

class SinglePlayerFragment : Fragment() {

    private lateinit var binding: SinglePlayerFragmentBinding

    private lateinit var game: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.single_player_fragment,
            container,
            false
        )

        binding.backButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        game = Game()

        binding.gameProgress.max = game.get_hardness()
        update_game()

        binding.button1.setOnClickListener {
            check_answer_and_update(binding.button1.text.toString().toInt())
        }
        binding.button2.setOnClickListener {
            check_answer_and_update(binding.button2.text.toString().toInt())
        }
        binding.button3.setOnClickListener {
            check_answer_and_update(binding.button3.text.toString().toInt())
        }

        return binding.root
    }

    fun update_score() {
        binding.scoreView.text = "Score: " + game.get_score().toString()
    }

    fun update_progress() {
        binding.gameProgress.progress = game.get_progress()
    }

    fun update_number() {
        binding.numberView.text = game.get_num().toString()
    }

    fun update_question() {
        val question = game.get_question()
        binding.button1.text = question[0].toString()
        binding.button2.text = question[1].toString()
        binding.button3.text = question[2].toString()
    }

    fun update_game() {
        update_score()
        update_number()
        update_progress()
        update_question()
    }

    fun check_answer_and_update(answer: Int) {
        game.check_answer(answer)
        if (game.end_game()) {
            binding.numberView.text = "You won!"
            binding.scoreView.text = "Score: " + game.get_score().toString()
            update_progress()
        } else {
            update_game()
        }
    }

}
