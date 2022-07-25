package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isStarted = false

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
    }

    private fun start() {
        binding.btnStart.text = getString(R.string.stop)
        isStarted = true
    }

    private fun stop() {
        binding.btnStart.text = getString(R.string.start)
        isStarted = false
    }

    private fun reset() {

    }

    private fun startOrStop() {
        if (isStarted) {
            stop()
        } else
            start()
    }
}