package com.example.drivetracker.data.items

import com.example.drivetracker.data.comments.Comment
import com.example.drivetracker.data.entity.Car

class CarItem (
    val car: Car = Car(),
    override val uploadDate: String="",
    override var rented:Boolean = false,
    override var comments:MutableList<Comment> = mutableListOf(),
    override var price:Double=0.0,
    override val pledge: Double = 0.0
):VehicleItem(uploadDate, rented, comments, price, pledge)