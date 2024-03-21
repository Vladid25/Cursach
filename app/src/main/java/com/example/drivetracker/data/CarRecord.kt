package com.example.drivetracker.data

import com.example.drivetracker.data.entity.Car
import java.util.Date

class CarRecord (
    val car: Car = Car(),
    val rating: Double=0.0,
    val uploadDate: Date = Date(),
    val rented:Boolean = false
)