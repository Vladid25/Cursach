package com.example.drivetracker.ui.order

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.entity.Car
import com.example.drivetracker.data.entity.Truck
import com.example.drivetracker.model.OrderVehicleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderVehicleViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(OrderVehicleUiState())
    val uiState:StateFlow<OrderVehicleUiState> = _uiState.asStateFlow()

    private var carList = mutableListOf<Car>()
    private var truckList = mutableListOf<Truck>()


    fun changeVehicle(index: Int){
        _uiState.update { currentState ->
            currentState.copy(isTruck = index!=0)
        }
    }

    fun getCars(): MutableList<Car> {
        return carList
    }

    fun getTrucks(): MutableList<Truck>{
        return truckList
    }

    fun addCar(car: Car){
        carList.add(car)
    }

    fun addTruck(truck: Truck){
        truckList.add(truck)
    }


}