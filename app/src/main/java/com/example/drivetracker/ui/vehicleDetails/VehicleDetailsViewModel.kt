package com.example.drivetracker.ui.vehicleDetails

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.CarRecord
import com.example.drivetracker.data.TruckRecord


class VehicleDetailsViewModel: ViewModel() {
    private lateinit var displayedCar:CarRecord
    private lateinit var displayedTruck:TruckRecord

    fun setCar(car:CarRecord){
        displayedCar = car
    }

    fun getDisplayedCar(): CarRecord{
        return displayedCar
    }

    fun setTruck(truckRecord: TruckRecord){
        displayedTruck = truckRecord
    }

    fun getDisplayedTruck(): TruckRecord{
        return displayedTruck
    }
}