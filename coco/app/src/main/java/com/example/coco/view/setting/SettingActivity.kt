package com.example.coco.view.setting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coco.R
import com.example.coco.databinding.ActivitySettingBinding
import com.example.coco.service.PriceForegroundService

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startForeground.setOnClickListener {
            Toast.makeText(this, "start", Toast.LENGTH_LONG).show()

            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "START"
            startService(intent)
        }

        binding.stopForeground.setOnClickListener {
            Toast.makeText(this, "stop", Toast.LENGTH_LONG).show()
            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "STOP"
            startService(intent)
        }
    }
}