package com.example.drivetracker.ui.vehicleDetails

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.CarRecord
import com.example.drivetracker.data.TruckRecord
import com.example.drivetracker.data.VehicleRepository
import javax.inject.Inject


class VehicleDetailsViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
): ViewModel() {
    private lateinit var displayedCar: CarRecord
    private lateinit var displayedTruck: TruckRecord

    fun setCar(car: CarRecord){
        displayedCar = car
    }

    fun getDisplayedCar(): CarRecord {
        return displayedCar
    }

    fun setTruck(truckRecord: TruckRecord){
        displayedTruck = truckRecord
    }

    fun getDisplayedTruck(): TruckRecord {
        return displayedTruck
    }

    fun deleteCar(){
        vehicleRepository.deleteCar(displayedCar)
    }

    fun deleteTruck(){
        vehicleRepository.deleteTruck(displayedTruck)
    }

}