package com.example.drivetracker.ui.userInfo

import androidx.lifecycle.ViewModel
import com.example.drivetracker.data.VehicleRepository
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.records.CarRecord
import com.example.drivetracker.data.records.TruckRecord
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class UserInfoViewModel@Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    private var carList = mutableListOf<CarRecord>()
    private var truckList = mutableListOf<TruckRecord>()
    fun getUserEmail():String{
        return auth.currentUser?.email.toString()
    }

    private fun fetchCarRecords(){
        vehicleRepository.getCarRecordByEmail(auth.currentUser?.email.toString()){
                cars->
            cars?.let {
                carList.clear()
                carList.addAll(cars)
            }
        }
    }

    private fun fetchTruckRecords(){
        vehicleRepository.getTruckRecordByEmail(auth.currentUser?.email.toString()){
                trucks->
            trucks?.let {
                truckList.clear()
                truckList.addAll(trucks)
            }
        }
    }
    fun getCarRecords(): List<CarRecord>{
        fetchCarRecords()
        return carList
    }

    fun updateCar(car:CarItem){
        vehicleRepository.updateCarItemUnRent(car)
    }

    fun updateTruck(truckItem: TruckItem){
        vehicleRepository.updateTruckItemUnRent(truckItem)
    }

    fun getTruckRecords():List<TruckRecord>{
        fetchTruckRecords()
        return truckList
    }

    fun updateCarRecord(carRecord: CarRecord){
        vehicleRepository.updateCarRecord(carRecord)
    }

    fun updateTruckRecord(truckRecord: TruckRecord){
        vehicleRepository.updateTruckRecord(truckRecord)
    }

    fun isAdmin():Boolean{
        return auth.currentUser?.email == "1@gmail.com"
    }

    fun isCarDateEnd(carRecord: CarRecord):Boolean{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.now().isAfter(LocalDate.parse(carRecord.endRentDate, formatter))
    }

    fun isTruckDateEnd(truckRecord: TruckRecord):Boolean{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.now().isAfter(LocalDate.parse(truckRecord.endRentDate, formatter))
    }
}