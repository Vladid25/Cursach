package com.example.drivetracker.data

import android.util.Log
import com.example.drivetracker.data.coments.Comment
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
        addCar(car)
    }

    fun updateCarItemUnRent(car: CarItem){
        deleteCar(car)
        car.unRent()
        addCar(car)
    }
    fun updateTruckItemUnRent(truck: TruckItem){
        deleteTruck(truck)
        truck.unRent()
        addTruck(truck)
    }

    fun updateTruckItem(truck: TruckItem){
        deleteTruck(truck)
        truck.setRent()
        addTruck(truck)
    }

    fun getCarRecordByEmail(email: String, callback: (List<CarRecord>?) -> Unit) {
        val list = mutableListOf<CarRecord>()
        val ref = firebase.getReference("CarRecords")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(CarRecord::class.java)
                    println("car: active = ${car?.isActive}")
                    if (car != null && car.ownerEmail == email && car.isActive) {
                        list.add(car)
                    }
                }
                callback(list)
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
                    if (truck != null && truck.ownerEmail == email&& truck.isActive) {
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

    private fun deleteCarRecord(carRecord: CarRecord) {
        val ref = firebase.getReference("CarRecords")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (carSnapshot in snapshot.children) {
                    val temp = carSnapshot.getValue(CarRecord::class.java)
                    println("temp: $temp, ${temp?.carItem?.car?.brand}, active = ${temp?.isActive}")
                    println("carRecord: $carRecord, ${carRecord.carItem.car.brand}, active = ${carRecord.isActive}")
                    if (temp?.carItem?.car ==carRecord.carItem.car&& temp.isActive) {
                        println("Deleting car record")
                        val carKey = carSnapshot.key
                        if (carKey != null) {
                            println("Deleting car key: $carKey")
                            ref.child(carKey).removeValue()
                                .addOnSuccessListener {
                                    println("Car record $carRecord deleted from the database.")
                                }
                                .addOnFailureListener { error ->
                                    println("Error deleting object: $error")
                                }
                        } else {
                            println("Car key is null")
                        }
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Cancel deleting: $error")
            }
        })
    }

    private fun deleteTruckRecord(truckRecord: TruckRecord) {
        val ref = firebase.getReference("TruckRecords")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (truckSnapshot in snapshot.children) {
                    val temp = truckSnapshot.getValue(TruckRecord::class.java)
                    println("temp: ${temp?.truckItem?.truck}, ${temp?.truckItem?.truck?.brand}, active = ${temp?.isActive}")
                    println("carRecord: ${truckRecord.truckItem.truck}, ${truckRecord.truckItem.truck.brand}, active = ${truckRecord.isActive}")
                    if (temp?.truckItem?.truck?.brand == truckRecord.truckItem.truck.brand&& temp.isActive) {
                        val truckKey = truckSnapshot.key
                        if (truckKey != null) {
                            ref.child(truckKey).removeValue()
                                .addOnSuccessListener {
                                    println("Truck record $truckRecord deleted from the database.")
                                }
                                .addOnFailureListener { error ->
                                    println("Error deleting object: $error")
                                }
                        } else {
                            println("Truck key is null")
                        }
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Cancel deleting: $error")
            }
        })
    }

    fun updateCarRecord(carRecord: CarRecord) {
        deleteCarRecord(carRecord)
        carRecord.setPassive()
        carRecord.carItem.unRent()
        addCarRecord(carRecord)
    }

    fun updateCarWithComment(car: CarItem,comment: Comment){
        deleteCar(car)
        car.addComment(comment)
        addCar(car)
    }

    fun updateTruckRecord(truckRecord: TruckRecord){
        deleteTruckRecord(truckRecord)
        truckRecord.setPassive()
        truckRecord.truckItem.unRent()
        addTruckRecord(truckRecord)
    }

    fun updateTruckWithComment(truck: TruckItem, comment: Comment){
        deleteTruck(truck)
        truck.addComment(comment)
        addTruck(truck)
    }

}