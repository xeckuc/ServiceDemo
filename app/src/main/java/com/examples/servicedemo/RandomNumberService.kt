package com.examples.servicedemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class RandomNumberService: Service() {

    private val randomNumbers = mutableListOf<Int>()
    private val binder: IBinder = ServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("RandomNumberService", "onStartCommand: Called")

        addRandomNumber()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("RandomNumberService", "onBind: Called")

        addRandomNumber()

        return binder
    }

    public fun getNumbers() : List<Int> = randomNumbers

    private fun addRandomNumber() {

        val random = Random()

        randomNumbers.add(random.nextInt(10))
    }

    inner class ServiceBinder: Binder() {

        fun getService() : RandomNumberService {
            return this@RandomNumberService
        }
    }
}