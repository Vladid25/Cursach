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

    fun changeVehicle(index: Int){
        _uiState.update { currentState ->
            currentState.copy(isTruck = index!=0)
        }
    }

    fun getCars(): MutableList<Car> {
        val car = Car(0,"AUDI", "Q7", 2019, false, 4)
        val list= mutableListOf(car)

        for(i in 1..20){
            list.add(car)
        }
        return list
    }

    fun getTrucks(): MutableList<Truck>{
        val trucks = Truck(0,"Bugr", "Q7", 2019, false, 182364.3)
        val list= mutableListOf(trucks)

        for(i in 1..20){
            list.add(trucks)
        }
        return list
    }
}