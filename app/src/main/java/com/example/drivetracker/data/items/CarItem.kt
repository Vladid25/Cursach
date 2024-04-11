package com.example.drivetracker.data.items

import com.example.drivetracker.data.coments.Comment
import com.example.drivetracker.data.entity.Car

class CarItem (
    val car: Car = Car(),
    val rating: Double=0.0,
    val uploadDate: String="",
    private var rented:Boolean = false,
    var comments:MutableList<Comment> = mutableListOf()
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

    fun addComment(comment: Comment){
        comments.add(comment)
    }
}