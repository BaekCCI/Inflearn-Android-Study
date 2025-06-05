package com.example.coco.view.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.coco.MainActivity
import com.example.coco.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private val viewModel: IntroViewModel by viewModels()
    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkFirstFlag()

        viewModel.first.observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                binding.fragmentContainerView.visibility = View.VISIBLE
                binding.animationView.visibility = View.INVISIBLE
            }
        })

    }
}