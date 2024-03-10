package com.example.drivetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

class Truck(
    override val brand: String,
    override val model: String,
    override val year: Int,
    override var rented: Boolean,
    val cargoCapacity: Double
): Vehicle(brand, model, year, rented)