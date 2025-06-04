package com.example.coco.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.coco.R
import timber.log.Timber

class SelectActivity : AppCompatActivity() {
    private val viewModel : SelectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        viewModel.getCurrentCoinList()

        viewModel.currentPriceResult.observe(this, Observer {
            Timber.tag("SelectActivity").d(it.toString())
        })

    }
}