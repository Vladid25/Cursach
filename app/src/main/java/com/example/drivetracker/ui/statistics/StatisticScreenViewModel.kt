package com.example.drivetracker.ui.statistics

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.records.CarRecord
import com.example.drivetracker.data.records.TruckRecord
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class StatisticScreenViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val auth: FirebaseAuth
): ViewModel() {
    private var carList = mutableListOf<CarItem>()
    private var carRecordList = mutableListOf<CarRecord>()
    private var truckList = mutableListOf<TruckItem>()
    private var truckRecordList = mutableListOf<TruckRecord>()

    init {
        fetchCars()
        fetchCarRecords()
        fetchTrucks()
        fetchTruckRecords()
    }

    private fun fetchCars() {
        vehicleRepository.getCars { cars ->
            cars?.let {
                carList.clear()
                carList.addAll(cars)
            }
        }
    }

    private fun fetchCarRecords() {
        vehicleRepository.getCarRecord { cars ->
            cars?.let {
                carRecordList.clear()
                carRecordList.addAll(cars)
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

    private fun fetchTruckRecords() {
        vehicleRepository.getTruckRecord { trucks ->
            trucks?.let {
                truckRecordList.clear()
                truckRecordList.addAll(trucks)
            }
        }
    }

    fun getCars():List<CarItem>{
        fetchCars()
        return carList
    }

    fun getTrucks():List<TruckItem>{
        fetchTrucks()
        return truckList
    }

    fun getNumberOfRent(carItem: CarItem): Int{
        fetchCarRecords()
        var number=0
        for(item in carRecordList){
            if(carItem.car==item.carItem.car){
                number++
            }
        }
        return number
    }

    fun getNumberOfRent(truckItem: TruckItem): Int{
        fetchTruckRecords()
        var number=0
        for(item in truckRecordList){
            if(truckItem.truck.brand==item.truckItem.truck.brand){
                number++
            }
        }
        return number
    }

    fun isAdmin():Boolean{
        return auth.currentUser?.email == "1@gmail.com"
    }

    fun exit(){
        auth.signOut()
    }

    fun getCarOwner(carItem: CarItem):String{
        for(item in carRecordList){
            if(item.carItem.car==carItem.car){
                return item.ownerEmail
            }
        }
        return ""
    }


}