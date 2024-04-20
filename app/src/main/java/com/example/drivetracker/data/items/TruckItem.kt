package com.example.drivetracker.data.items

import com.example.drivetracker.data.coments.Comment
import com.example.drivetracker.data.entity.Truck
import kotlin.math.round

class TruckItem (
    val truck: Truck=Truck(),
    override val uploadDate: String = "",
    override var rented:Boolean = false,
    override var comments:MutableList<Comment> = mutableListOf(),
    override var price:Double=0.0
): VehicleItem(uploadDate, rented, comments, price)