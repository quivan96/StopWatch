package com.example.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatch.StopWatchService.Companion.CURRENT_TIME
import com.example.stopwatch.StopWatchService.Companion.UPDATED_TIME
import com.example.stopwatch.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var serviceIntent: Intent
    private var isStarted = false
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startOrStop()
        }

        binding.btnReset.setOnClickListener {
            reset()
        }

        serviceIntent = Intent(applicationContext, StopWatchService::class.java)

        registerReceiver(updateTime, IntentFilter(UPDATED_TIME))
    }

    private fun start() {
        serviceIntent.putExtra(CURRENT_TIME, time)
        startService(serviceIntent)
        binding.btnStart.text = getString(R.string.stop)
        isStarted = true
    }

    private fun stop() {
        stopService(serviceIntent)
        binding.btnStart.text = getString(R.string.start)
        isStarted = false
    }

    private fun reset() {
        stop()
        time = 0.0
        binding.tvTime.text = getFormattedTime(time)
    }

    private fun startOrStop() {
        if (isStarted) {
            stop()
        } else
            start()
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(content: Context, intent: Intent) {
            time = intent.getDoubleExtra(CURRENT_TIME, 0.0)
            binding.tvTime.text = getFormattedTime(time)
        }
    }

    private fun getFormattedTime(time: Double): String {
        val timeInt = time.roundToInt()
        val hours = timeInt % 86400 / 3600
        val minutes = timeInt % 86400 % 3600 / 60
        val seconds = timeInt % 86400 % 3600 % 60

        return getString(R.string.timeFormat, hours, minutes, seconds)
    }
}