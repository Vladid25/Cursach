package com.example.drivetracker.ui.commenting

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.VehicleRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CommentScreenViewModel @Inject constructor(
    private val auth:FirebaseAuth,
    private val vehicleRepository: VehicleRepository
): ViewModel() {
    fun getEmail():String{
        return auth.currentUser?.email.toString()
    }
}