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
import com.kennelteam.factoria_client.databinding.MultiplayerFragmentBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MultiPlayerFragment : Fragment() {

    private lateinit var binding: MultiplayerFragmentBinding
    private lateinit var enemyName: String
    private var mistakesMe: Int = 0
    private var mistakesEnemy: Int = 0
    private var answersMe: Int = 0
    private var answersEnemy: Int = 0
    private var variants = listOf<Int>()
    private lateinit var myName : String

    @SuppressLint("SetTextI18n")
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

        val dividersCount = Params.dividersCount as Int
        binding.gameProgress.max = dividersCount
        binding.gameProgress2.max = dividersCount
        binding.gameProgress.progress = 0
        binding.gameProgress2.progress = 0

        enemyName = Params.enemyName
        binding.scoreView2.text = "$enemyName: 0"
        Communicator.data.observe(viewLifecycleOwner, {
            Log.i("12345", it.toString())
            if (it["message_type"] == "question") {
                Log.i("12345", "question processing")
                binding.numberView.text = it["number"]!!
                variants = Json.decodeFromString(it["variants"]!!)
                binding.button1.text = "${variants[0]}"
                binding.button2.text = "${variants[1]}"
                binding.button3.text = "${variants[2]}"

            } else if (it["message_type"] == "answer_info") {
                val name = it["answered_player"]
                if (name == enemyName) {
                    answersEnemy++
                } else {
                    myName = name!!
                    answersMe++
                    Communicator.sendMessage("""{"message_type": "get_question"}""")
                }
                updateProgress()
            } else if (it["message_type"] == "mistake_info") {
                val name = it["mistaken_player"]
                if (name == enemyName) {
                    mistakesEnemy++
                } else {
                    mistakesMe++
                }
            } else if (it["message_type"] == "finish") {
                Params.resultsEnemy = it[enemyName]!!
                Params.resultsMe = it[myName]!!

                //TODO: implement finish multiplayer
            }
        })

        binding.backButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.button1.setOnClickListener { submitAnswer(0) }
        binding.button2.setOnClickListener { submitAnswer(1) }
        binding.button3.setOnClickListener { submitAnswer(2) }

        return binding.root
    }

    fun submitAnswer(btnId: Int) {
        Communicator.sendMessage("""{"message_type": "answer", "answer": ${variants[btnId]}}""")
    }

    fun updateProgress() {
        binding.gameProgress.progress = answersMe
        binding.gameProgress2.progress = answersEnemy
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Communicator.sendMessage("""{"message_type": "get_question"}""")
    }
}
