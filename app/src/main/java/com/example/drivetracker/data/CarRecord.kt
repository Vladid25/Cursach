package com.example.drivetracker.data

import com.example.drivetracker.data.entity.Car
import java.time.LocalDate
import java.util.Date

class CarRecord (
    val car: Car = Car(),
    val rating: Double=0.0,
    val uploadDate: String="",
    val rented:Boolean = false
)