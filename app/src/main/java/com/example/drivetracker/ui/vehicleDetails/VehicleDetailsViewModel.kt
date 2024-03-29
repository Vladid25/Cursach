package com.example.drivetracker.ui.vehicleDetails

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.records.CarRecord
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class VehicleDetailsViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val auth: FirebaseAuth
): ViewModel() {
    private lateinit var displayedCar: CarItem
    private lateinit var displayedTruck: TruckItem

    fun setCar(car: CarItem){
        displayedCar = car
    }

    fun getDisplayedCar(): CarItem {
        return displayedCar
    }

    fun setTruck(truckItem: TruckItem){
        displayedTruck = truckItem
    }

    fun getDisplayedTruck(): TruckItem {
        return displayedTruck
    }

    fun deleteCar(){
        vehicleRepository.deleteCar(displayedCar)
    }

    fun deleteTruck(){
        vehicleRepository.deleteTruck(displayedTruck)
    }

    fun getUserEmail():String{
        return auth.currentUser?.email.toString()
    }

    fun addCarRecord(carRecord: CarRecord){
        vehicleRepository.addCarRecord(carRecord)
    }

}