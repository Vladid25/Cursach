package com.example.drivetracker.data.repository

import com.example.drivetracker.data.entity.Car
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun getAllCarsStream():Flow<List<Car>>

    fun getCarStream(id:Int):Flow<Car?>

    suspend fun insertCar(car: Car)

    suspend fun deleteCar(car: Car)

    suspend fun updateCar(car: Car)
}