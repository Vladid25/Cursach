package com.example.drivetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drivetracker.data.dao.CarDao
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.data.entity.Truck

@Database(entities = [Car::class, Truck::class], version = 1, exportSchema = false)
abstract class DriveTrackerDatabase:RoomDatabase() {
    abstract fun carDao():CarDao

    companion object{
        @Volatile
        private var Instance: DriveTrackerDatabase ?= null

        fun getDatabase(context: Context):DriveTrackerDatabase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context,DriveTrackerDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}