package com.ayberk.ordercoffee

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OrderCoffeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
