package com.example.drivetracker.data

import com.example.drivetracker.data.entity.Car
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

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
}