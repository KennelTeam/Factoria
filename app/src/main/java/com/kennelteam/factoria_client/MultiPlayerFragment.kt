package com.kennelteam.factoria_client

import android.R.attr
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
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
import android.R.attr.delay




class MultiPlayerFragment : Fragment() {

    private lateinit var binding: MultiplayerFragmentBinding
    private lateinit var enemyName: String
    private var mistakesMe: Int = 0
    private var mistakesEnemy: Int = 0
    private var answersMe: Int = 0
    private var answersEnemy: Int = 0
    private var variants = listOf<Int>()
    private lateinit var myName : String
    private final var handler: Handler = Handler()
    private var myScore = 0
    private var enemyScore = 0

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

        handler.postDelayed(object : Runnable  {
            override fun run() {
                binding.scoreView.text = "You: " + myScore.toString()
                if (dividersCount != answersMe) {
                    myScore -= 1
                }


                binding.scoreView2.text = enemyName + ": " + enemyScore.toString()
                if (dividersCount != answersEnemy) {
                    enemyScore -= 1
                }

                handler.postDelayed(this, 1000)
            }
        }, 1000)

        enemyName = Params.enemyName
        binding.scoreView2.text = "$enemyName: 0"
        Communicator.data.observe(viewLifecycleOwner) {
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
                    enemyScore += 100
                } else {
                    myName = name!!
                    Params.myName = name!!
                    answersMe++
                    myScore += 100
                    Communicator.sendMessage("""{"message_type": "get_question"}""")
                }
                updateProgress()
            } else if (it["message_type"] == "mistake_info") {
                val name = it["mistaken_player"]
                if (name == enemyName) {
                    mistakesEnemy++
                    enemyScore -= 100
                } else {
                    mistakesMe++
                    myScore -= 100
                }
            } else if (it["message_type"] == "finished") {
                Log.i("12345", "finished!!")
                Log.i("12345", it["results"]!!)

                val strings = it["results"]!!.split("\"")
                val str1 = strings[1]
                val str2 = strings[3]

                if (strings[0].contains(myName)) {
                    Params.resultsMe = str1
                    Params.resultsEnemy = str2
                } else {
                    Params.resultsEnemy = str1
                    Params.resultsMe = str2
                }

                Log.i("12345", "test")
                this.findNavController()
                    .navigate(R.id.action_multiPlayerFragment_to_multiPlayerFinishedFragment)
            } else if (it["message_type"] == "result") {
                Log.i("12345", "result!")
                val action = MultiPlayerFragmentDirections.actionMultiPlayerFragmentToMultiPlayerFinishedFragment()
                action.message = it["result"]!!
                findNavController().navigate(action)
            }
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
