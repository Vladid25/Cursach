package com.example.drivetracker.ui.order

import androidx.lifecycle.ViewModel
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
}