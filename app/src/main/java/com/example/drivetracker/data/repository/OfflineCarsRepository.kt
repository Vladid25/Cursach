package com.example.drivetracker.data.repository

import com.example.drivetracker.data.dao.CarDao
import com.example.drivetracker.data.entity.Car
import kotlinx.coroutines.flow.Flow

class OfflineCarsRepository(private val carDao: CarDao):CarRepository {
    override fun getAllCarsStream(): Flow<List<Car>> = carDao.getAllCars()

    override fun getCarStream(id: Int): Flow<Car?> = carDao.getCar(id)

    override suspend fun insertCar(car: Car) = carDao.insert(car)

    override suspend fun updateCar(car: Car) = carDao.update(car)

    override suspend fun deleteCar(car: Car) = carDao.delete(car)
}