package com.example.drivetracker.ui.vehicleDetails

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.records.CarRecord
import com.example.drivetracker.data.records.TruckRecord
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

    fun updateCarItem(){
        displayedCar.setRent()
        vehicleRepository.updateCarItem(displayedCar)
    }

    fun updateCarPrice(price:Double){
        displayedCar.setCarPrice(price)
        vehicleRepository.updateCarItem(displayedCar)
    }

    fun updateTruckPrice(price:Double){
        displayedTruck.setCarPrice(price)
        vehicleRepository.updateTruckItem(displayedTruck)
    }


    fun addTruckRecord(truckRecord: TruckRecord){
        vehicleRepository.addTruckRecord(truckRecord)
    }

    fun updateTruckItem(){
        displayedTruck.setRent()
        vehicleRepository.updateTruckItem(displayedTruck)
    }

    fun isAdmin():Boolean{
        return auth.currentUser?.email == "1@gmail.com"
    }
}