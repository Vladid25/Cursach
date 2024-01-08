package com.example.drivetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Truck")
class Truck(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override var rented: Boolean,
    val cargoCapacity: Double
): Vehicle(id, brand, model, year, rented)