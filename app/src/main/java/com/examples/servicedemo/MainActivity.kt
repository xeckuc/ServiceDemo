package com.examples.servicedemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , ServiceConnection{

    private var numberService: RandomNumberService? = null
    private var numbers = listOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUI()
    }

    private fun bindUI() {

        btnTriggerService.setOnClickListener {
            val intent = Intent(this, RandomNumberService::class.java)
            applicationContext.startService(intent)
        }

        btnUpdateNumbers.setOnClickListener {

            numberService?.let {
                numbers = it.getNumbers()
                updateNumbers()
            }
        }
    }

    private fun updateNumbers() {
        tvNumbers.text = numbers.toString()
    }

    override fun onResume() {
        super.onResume()

        val intent = Intent(this, RandomNumberService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        unbindService(this)
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        numberService = null
    }

    override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder?) {

        val b = iBinder as RandomNumberService.ServiceBinder

        numberService = b.getService()
        Toast.makeText(this, "Service Connected", Toast.LENGTH_SHORT).show()
    }
}
