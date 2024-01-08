package com.example.drivetracker.data.entity

abstract class Vehicle(
    open val id:Int,
    open val brand: String,
    open val model:String,
    open val year:Int,
    open var rented: Boolean
){
    open fun startRent(){
        rented = true
    }

    open fun endRent(){
        rented = false
    }
}