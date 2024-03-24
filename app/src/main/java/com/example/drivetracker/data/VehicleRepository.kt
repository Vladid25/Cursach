package com.example.drivetracker.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VehicleRepository(
    private val firebase: FirebaseDatabase = FirebaseDatabase
    .getInstance("https://drivetracker-ecf96-default-rtdb.europe-west1.firebasedatabase.app/")) {
    private var carsList = mutableListOf<CarRecord>()
    fun getCars(callback: (List<CarRecord>?) -> Unit) {
        val ref = firebase.getReference("Cars")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                carsList.clear()
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

    fun addCar(car: CarRecord){
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

    fun addTruck(truck: TruckRecord){
        val db = firebase.getReference("Trucks")
        val truckId = db.push().key!!
        db.child(truckId).setValue(truck)
    }

    fun deleteCar(car: CarRecord){
        val ref = firebase.getReference("Cars")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (carSnapshot in snapshot.children) {
                    val temp = carSnapshot.getValue(CarRecord::class.java)
                    if(temp?.car == car.car){
                        val carKey = carSnapshot.key
                        Log.v("Debug", carKey.toString())
                        if (carKey != null) {
                            ref.child(carKey).removeValue()
                                .addOnSuccessListener {
                                    println("Об'єкт успішно видалено з бази даних.")
                                }
                                .addOnFailureListener { error ->
                                    println("Помилка при видаленні об'єкта: $error")
                                }
                        }
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Debug", "Cancel deleting")
            }
        })
    }

    fun deleteTruck(truck: TruckRecord){
        val ref = firebase.getReference("Trucks")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val temp = dataSnapshot.getValue(TruckRecord::class.java)
                    if(temp?.uploadDate==truck.uploadDate){
                        val key = dataSnapshot.key
                        Log.v("Debug", key.toString())
                        if (key != null) {
                            ref.child(key).removeValue()
                                .addOnSuccessListener {
                                    println("Об'єкт успішно видалено з бази даних.")
                                }
                                .addOnFailureListener { error ->
                                    println("Помилка при видаленні об'єкта: $error")
                                }
                        }
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Debug", "Cancel deleting")
            }
        })
    }



}