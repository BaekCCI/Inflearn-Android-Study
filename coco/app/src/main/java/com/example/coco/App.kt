package com.example.coco

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {
    init {

    }

    companion object {
        private var instance: App? = null

        fun context(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())
    }
}