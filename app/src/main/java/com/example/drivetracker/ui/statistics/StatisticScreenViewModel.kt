package com.example.drivetracker.ui.statistics

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import javax.inject.Inject

class StatisticScreenViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
): ViewModel() {
    private var carList = mutableListOf<CarItem>()
    private var truckList = mutableListOf<TruckItem>()

    init {
        fetchCars()
        fetchTrucks()
    }

    private fun fetchCars() {
        vehicleRepository.getCars { cars ->
            cars?.let {
                carList.clear()
                carList.addAll(cars)
            }
        }
    }

    private fun fetchTrucks() {
        vehicleRepository.getTrucks { trucks ->
            trucks?.let {
                truckList.clear()
                truckList.addAll(trucks)
            }
        }
    }

}