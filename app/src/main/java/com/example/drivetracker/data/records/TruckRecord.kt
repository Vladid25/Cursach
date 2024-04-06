package com.example.drivetracker.data.records

import com.example.drivetracker.data.items.TruckItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TruckRecord(
    val truckItem: TruckItem = TruckItem(),
    val ownerEmail:String="",
    val startRentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val endRentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
){
    init {
        truckItem.setRent()
    }
}