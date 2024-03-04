package com.example.drivetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

class Car(
    override val id:Int,
    override val brand: String,
    override val model:String,
    override val year:Int,
    override var rented: Boolean,
    val numberSeats: Int
): Vehicle(id, brand, model, year, rented)