package com.example.drivetracker.data.items

import com.example.drivetracker.data.coments.Comment
import com.example.drivetracker.data.entity.Truck
import kotlin.math.round

class TruckItem (
    val truck: Truck=Truck(),
    val uploadDate: String = "",
    private var rented:Boolean = false,
    var comments:MutableList<Comment> = mutableListOf(),
    private var price:Double=0.0
){

    fun getPrice():Double{
        return price
    }

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

    fun getRating():Double{
        if(comments.size==0){
            return 0.0
        }
        var sum = 0
        for(item in comments){
            sum+=item.rating
        }

        return round(sum/comments.size.toDouble()*10) /10
    }

    fun setPrice(price: Double){
        this.price = price
    }
}