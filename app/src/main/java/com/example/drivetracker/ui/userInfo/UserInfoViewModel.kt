package com.example.drivetracker.ui.userInfo

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.records.CarRecord
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserInfoViewModel@Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    private var carList = mutableListOf<CarRecord>()
    fun getUserEmail():String{
        return auth.currentUser?.email.toString()
    }

    private fun fetchCarRecords(){
        vehicleRepository.getCarRecordByEmail(auth.currentUser?.email.toString()){
                cars->
            cars?.let {
                carList.clear()
                carList.addAll(cars)
            }
        }
    }
    fun getCarRecords(): List<CarRecord>{
        fetchCarRecords()
        return carList
    }
}