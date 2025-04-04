package com.example.posturemonitor

import android.app.Application
import com.example.posturemonitor.ble.BleManager

class PostureMonitorApplication : Application() {
    lateinit var bleManager: BleManager
        private set

    override fun onCreate() {
        super.onCreate()
        bleManager = BleManager(this)
    }
}