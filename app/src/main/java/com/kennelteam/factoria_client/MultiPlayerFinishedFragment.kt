package com.kennelteam.factoria_client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.location.LocationRequestCompat
import androidx.navigation.fragment.findNavController
import com.kennelteam.factoria_client.databinding.MultiPlayerFinishedFragmentBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlin.math.round

class MultiPlayerFinishedFragment : Fragment() {

    private lateinit var binding: MultiPlayerFinishedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.multi_player_finished_fragment,
            container,
            false
        )

        binding.okButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_multiPlayerFinishedFragment_to_mainScreenFragment)
        }

        Log.i("12345t", Params.resultsMe)
        Log.i("12345t", Params.resultsEnemy)
        Params.resultsMe = Params.resultsMe.replace("'", "\"")
        Params.resultsEnemy = Params.resultsEnemy.replace("'", "\"")
        val myResults = Json.decodeFromString<HashMap<String, String>>(Params.resultsMe)
        val enemyResults = Json.decodeFromString<HashMap<String, String>>(Params.resultsEnemy)

        Log.i("12345t", "1")
        binding.timeMe.text = "Time (you): " + myResults["time"].toString()
        binding.mistakesMe.text = "Mistakes (you): " + myResults["mistakes"].toString()
        Log.i("12345t", "2")
        binding.timeEnemy.text = "Time (" + Params.enemyName + ") " + enemyResults["time"].toString()
        binding.mistakesEnemy.text = "Mistakes (" + Params.enemyName + ") " + enemyResults["mistakes"]

        Log.i("12345t", "3")
        val myScore = Params.dividersCount * 10 - (myResults["mistakes"]!!).toInt() * 20 - round(myResults["time"]!!.toFloat())
        val enemyScore = Params.dividersCount * 10 - enemyResults["mistakes"]!!.toInt() * 20 - round(enemyResults["time"]!!.toFloat())
        Log.i("12345t", "4")
        binding.scoreMe.text = "Score (you): " + myScore.toString()
        binding.scoreEnemy.text = "Score (" + Params.enemyName + "): " + enemyScore.toString()
        Log.i("12345t", "5")
        var winnerName = ""
        if (myScore > enemyScore) {
            winnerName = Params.myName
        } else if (myScore < enemyScore) {
            winnerName = Params.enemyName
        } else if (myScore == enemyScore){
            winnerName = "both"
        }
        Log.i("12345t", "6")
        binding.winnerText.text = "Winner: " + winnerName + "!!"
        return binding.root
    }

}
