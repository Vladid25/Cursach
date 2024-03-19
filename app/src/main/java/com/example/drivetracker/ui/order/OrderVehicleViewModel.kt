package com.example.drivetracker.ui.order

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.CarRecord
import com.example.drivetracker.data.TruckRecord
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.data.entity.Truck
import com.example.drivetracker.model.OrderVehicleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OrderVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
):ViewModel() {
    private val _uiState = MutableStateFlow(OrderVehicleUiState())
    val uiState:StateFlow<OrderVehicleUiState> = _uiState.asStateFlow()

    private var carList = mutableListOf<CarRecord>()
    private var truckList = mutableListOf<TruckRecord>()

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



    fun changeVehicle(index: Int){
        _uiState.update { currentState ->
            currentState.copy(isTruck = index!=0)
        }
    }

    fun getCars(): MutableList<CarRecord> {
        fetchCars()
        return carList
    }

    fun getTrucks(): MutableList<TruckRecord>{
        fetchTrucks()
        return truckList
    }

}