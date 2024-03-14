package com.example.drivetracker.data

import com.example.drivetracker.data.entity.Car
import java.util.Date

class CarRecord (
    val car: Car,
    val photoUrl: String?=null,
    val rating: Double=0.0,
    val uploadDate: Date,
    val rented:Boolean = false
)