package com.kennelteam.factoria_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Communicator.init()
        setContentView(R.layout.activity_main)
    }

}
