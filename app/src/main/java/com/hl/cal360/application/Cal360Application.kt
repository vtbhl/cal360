package com.hl.cal360.application

import android.app.Application
import android.support.v7.app.AppCompatDelegate

class Cal360Application : Application() {
    companion object {
        lateinit var instance: Cal360Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

}