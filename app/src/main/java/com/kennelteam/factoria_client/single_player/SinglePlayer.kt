package com.kennelteam.factoria_client.single_player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.kennelteam.factoria_client.R
import com.kennelteam.factoria_client.game.Game

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SinglePlayer.newInstance] factory method to
 * create an instance of this fragment.
 */
class SinglePlayer : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var game: Game
    private lateinit var numberView : TextView
    private lateinit var answer1 : Button
    private lateinit var answer2 : Button
    private lateinit var answer3 : Button
    private lateinit var scoreView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_player, container, false)
    }

    fun update_score() {
        scoreView.text = "Score: " + game.get_score().toString()
    }

    fun update_number() {
        numberView.text = game.get_num().toString()
    }

    fun update_question() {
        val question = game.get_question()
        answer1.text = question[0].toString()
        answer2.text = question[1].toString()
        answer3.text = question[2].toString()
    }

    fun update_game() {
        update_score()
        update_number()
        update_question()
    }

    fun check_answer_and_update(answer: Int) {
        game.check_answer(answer)
        if (game.end_game()) {
            numberView.text = "You won!"
            scoreView.text = "Score: " + game.get_score().toString()
        } else {
            update_game()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        game = Game()
        numberView = view.findViewById<TextView>(R.id.numberView)
        answer1 = view.findViewById<Button>(R.id.button1)
        answer2 = view.findViewById<Button>(R.id.button2)
        answer3 = view.findViewById<Button>(R.id.button3)
        scoreView = view.findViewById<TextView>(R.id.scoreView)

        update_game()

        answer1.setOnClickListener {
            check_answer_and_update(answer1.text.toString().toInt())
        }
        answer2.setOnClickListener {
            check_answer_and_update(answer2.text.toString().toInt())
        }
        answer3.setOnClickListener {
            check_answer_and_update(answer3.text.toString().toInt())
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SinglePlayer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SinglePlayer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}