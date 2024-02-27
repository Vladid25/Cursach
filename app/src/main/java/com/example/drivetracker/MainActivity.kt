package com.example.drivetracker

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.ui.DriveTrackerApp
import com.example.drivetracker.ui.theme.DriveTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DriveTrackerTheme {
                DriveTrackerApp()
            }
        }
    }
}

