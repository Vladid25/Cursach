package com.example.drivetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Car(
    override val brand: String = "",
    override val model:String = "",
    override val year:Int = 0,
    override var rented: Boolean = false,
    val numberSeats: Int = 0
): Vehicle(brand, model, year, rented)