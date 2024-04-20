package com.example.drivetracker.ui.order

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.model.OrderVehicleUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OrderVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val auth: FirebaseAuth
):ViewModel() {
    private val _uiState = MutableStateFlow(OrderVehicleUiState())
    val uiState:StateFlow<OrderVehicleUiState> = _uiState.asStateFlow()

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



    fun changeVehicle(index: Int){
        _uiState.update { currentState ->
            currentState.copy(isTruck = index!=0)
        }
    }

    fun getEnableCars(): MutableList<CarItem> {
        fetchCars()
        val enableCarList = mutableListOf<CarItem>()
        for (item in carList){
            if(!item.isRented()){
                enableCarList.add(item)
            }
        }
        return enableCarList
    }

    fun getEnableTrucks(): MutableList<TruckItem>{
        fetchTrucks()
        val enableTruckList = mutableListOf<TruckItem>()
        for (item in truckList){
            if(!item.isRented()){
                enableTruckList.add(item)
            }
        }
        return enableTruckList
    }

    fun addCar(car: CarItem){
        vehicleRepository.addCar(car)
    }

    fun addTruck(truck: TruckItem){
        vehicleRepository.addTruck(truck)
    }

    fun isAdmin():Boolean{
        return auth.currentUser?.email=="1@gmail.com"
    }

}