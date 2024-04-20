package com.example.drivetracker.data.items

import com.example.drivetracker.data.coments.Comment
import com.google.firebase.database.Exclude
import kotlin.math.round

open class VehicleItem(
    open val uploadDate: String="",
    open var rented:Boolean = false,
    open var comments:MutableList<Comment> = mutableListOf(),
    open var price:Double=0.0
) {
    fun getCarPrice():Double{
        return price
    }

    fun setRent(){
        rented = true
    }

    fun unRent(){
        rented = false
    }
    @Exclude
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

    fun setCarPrice(price: Double){
        this.price = price
    }
}