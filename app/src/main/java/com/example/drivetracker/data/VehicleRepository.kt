package com.example.drivetracker.data

import android.util.Log
import com.example.drivetracker.data.items.CarItem
import com.example.drivetracker.data.items.TruckItem
import com.example.drivetracker.data.records.CarRecord
import com.example.drivetracker.data.records.TruckRecord
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VehicleRepository(
    private val firebase: FirebaseDatabase = FirebaseDatabase
    .getInstance("https://drivetracker-ecf96-default-rtdb.europe-west1.firebasedatabase.app/")
) {
    private var carsList = mutableListOf<CarItem>()
    fun getCars(callback: (List<CarItem>?) -> Unit) {
        val ref = firebase.getReference("Cars")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                carsList.clear()
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(CarItem::class.java)
                    car?.let { carsList.add(it) }
                }
                callback(carsList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun addCar(car: CarItem){
        val db = firebase.getReference("Cars")
        val carId = db.push().key!!
        db.child(carId).setValue(car)
    }

    fun getTrucks(callback: (List<TruckItem>?) -> Unit) {
        val ref = firebase.getReference("Trucks")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val trucksList = mutableListOf<TruckItem>()
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(TruckItem::class.java)
                    car?.let { trucksList.add(it) }
                }
                callback(trucksList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun addTruck(truck: TruckItem){
        val db = firebase.getReference("Trucks")
        val truckId = db.push().key!!
        db.child(truckId).setValue(truck)
    }

    fun deleteCar(car: CarItem){
        val ref = firebase.getReference("Cars")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (carSnapshot in snapshot.children) {
                    val temp = carSnapshot.getValue(CarItem::class.java)
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

    fun deleteTruck(truck: TruckItem){
        val ref = firebase.getReference("Trucks")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val temp = dataSnapshot.getValue(TruckItem::class.java)
                    if(temp?.uploadDate == truck.uploadDate){
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

    fun addCarRecord(carRecord: CarRecord){
        val db = firebase.getReference("CarRecords")
        val carId = db.push().key!!
        db.child(carId).setValue(carRecord)
    }

    fun addTruckRecord(truckRecord: TruckRecord){
        val db = firebase.getReference("TruckRecords")
        val truckId = db.push().key!!
        db.child(truckId).setValue(truckRecord)
    }

    fun updateCarItem(car: CarItem){
        deleteCar(car)
        car.setRent()
        addCar(car)
    }

    fun updateTruckItem(truck: TruckItem){
        deleteTruck(truck)
        truck.setRent()
        addTruck(truck)
    }

    fun getCarRecordByEmail(email:String, callback: (List<CarRecord>?) -> Unit){
        val list = mutableListOf<CarRecord>()
        val ref = firebase.getReference("CarRecords")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(CarRecord::class.java)
                    if (car != null && car.ownerEmail == email) {
                        list.add(car)
                    }
                    callback(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun getTruckRecordByEmail(email:String, callback: (List<TruckRecord>?) -> Unit){
        val list = mutableListOf<TruckRecord>()
        val ref = firebase.getReference("TruckRecords")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (truckSnapshot in snapshot.children) {
                    val truck = truckSnapshot.getValue(TruckRecord::class.java)
                    if (truck != null && truck.ownerEmail == email) {
                        list.add(truck)
                    }
                    callback(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }



}