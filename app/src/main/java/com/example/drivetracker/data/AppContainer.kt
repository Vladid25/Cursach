package com.example.drivetracker.data

import android.content.Context
import com.example.drivetracker.data.repository.CarRepository
import com.example.drivetracker.data.repository.OfflineCarsRepository

interface AppContainer {
    val carRepository: CarRepository
}

class AppDataContainer(private val context: Context):AppContainer{
    override val carRepository: CarRepository by lazy {
        OfflineCarsRepository(DriveTrackerDatabase.getDatabase(context).carDao())
    }
}