package com.example.drivetracker.data.records

import com.example.drivetracker.data.items.CarItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CarRecord(
    val carItem: CarItem = CarItem(),
    val ownerEmail:String="",
    val startRentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val endRentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    var isActive:Boolean = true
){
    init {
        carItem.setRent()
    }

    fun setPassive(){
        isActive = false
    }

}