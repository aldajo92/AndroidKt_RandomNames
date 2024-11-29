package com.roboticamedellin.helloapp

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.roboticamedellin.domain.RandomNames
import kotlin.random.Random

class RandomNameService : Service() {

    private val handler = Handler()
    private val nameList = RandomNames.getNameList()
    private var isRunning = false

    private val randomNameTask = object : Runnable {
        override fun run() {
            if (isRunning) {
                val randomName = nameList[Random.nextInt(nameList.size)]

                val intent = Intent("com.example.RandomNameBroadcast")
                intent.putExtra("randomName", randomName)

                sendBroadcast(intent)

                handler.postDelayed(this, 2000) // Execute every 2 seconds
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        isRunning = true
        handler.post(randomNameTask)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        handler.removeCallbacks(randomNameTask)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
