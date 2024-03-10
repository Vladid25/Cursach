package com.example.drivetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Car(
    override val brand: String,
    override val model:String,
    override val year:Int,
    override var rented: Boolean,
    val numberSeats: Int
): Vehicle(brand, model, year, rented)