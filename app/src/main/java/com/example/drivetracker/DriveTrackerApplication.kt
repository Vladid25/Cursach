package com.example.drivetracker

import android.app.Application
import com.example.drivetracker.data.AppContainer
import com.example.drivetracker.data.AppDataContainer

class DriveTrackerApplication: Application() {
    lateinit var container:AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}