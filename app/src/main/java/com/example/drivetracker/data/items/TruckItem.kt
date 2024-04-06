package com.example.drivetracker.data.items

import com.example.drivetracker.data.entity.Truck

class TruckItem (
    val truck: Truck=Truck(),
    val rating: Double=0.0,
    val uploadDate: String = "",
    private var rented:Boolean = false
){
    fun setRent(){
        rented = true
    }

    fun unRent(){
        rented = false
    }

    fun isRented():Boolean{
        return rented
    }
}