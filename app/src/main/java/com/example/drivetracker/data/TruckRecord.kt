package com.example.drivetracker.data

import com.example.drivetracker.data.entity.Truck
import java.util.Date

class TruckRecord (
    val truck: Truck=Truck(),
    val rating: Double=0.0,
    val uploadDate: Date = Date(),
    val rented:Boolean = false
)