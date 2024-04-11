package com.example.drivetracker.ui.commenting

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.coments.Comment
import com.example.drivetracker.data.items.CarItem
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.properties.Delegates

class CommentScreenViewModel @Inject constructor(
    private val auth:FirebaseAuth,
    private val vehicleRepository: VehicleRepository
): ViewModel() {
    private lateinit var car:CarItem
    private var isCar by Delegates.notNull<Boolean>()
    fun setCar(carItem: CarItem){
        car = carItem
        isCar = true
    }

    fun getCar():CarItem{
        return car
    }
    fun getEmail():String{
        return auth.currentUser?.email.toString()
    }

    fun updateCarWithComment(comment: Comment){
        vehicleRepository.updateCarWithComment(car,comment)
    }
}