package com.example.drivetracker.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VehicleRepository(
    private val firebase: FirebaseDatabase = FirebaseDatabase
    .getInstance("https://drivetracker-ecf96-default-rtdb.europe-west1.firebasedatabase.app/")) {
    fun getCars(callback: (List<CarRecord>?) -> Unit) {
        val ref = firebase.getReference("Cars")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val carsList = mutableListOf<CarRecord>()
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(CarRecord::class.java)
                    car?.let { carsList.add(it) }
                }
                callback(carsList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun addCar(car:CarRecord){
        val db = firebase.getReference("Cars")
        val carId = db.push().key!!
        db.child(carId).setValue(car)
    }

    fun getTrucks(callback: (List<TruckRecord>?) -> Unit) {
        val ref = firebase.getReference("Trucks")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val trucksList = mutableListOf<TruckRecord>()
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(TruckRecord::class.java)
                    car?.let { trucksList.add(it) }
                }
                callback(trucksList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun addTruck(truck:TruckRecord){
        val db = firebase.getReference("Trucks")
        val truckId = db.push().key!!
        db.child(truckId).setValue(truck)
    }

}