package com.example.drivetracker.data.entity

abstract class Vehicle(
    open val brand: String,
    open val model:String,
    open val year:Int,
)