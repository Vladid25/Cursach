package com.example.drivetracker.data.records

import com.example.drivetracker.data.items.CarItem
import java.time.LocalDate

class CarRecord(
    val carItem: CarItem = CarItem(),
    val ownerEmail:String="",
    val startRentDate: LocalDate = LocalDate.now(),
    val endRentDate: LocalDate = LocalDate.now()
){
    init {
        carItem.setRent()
    }
}