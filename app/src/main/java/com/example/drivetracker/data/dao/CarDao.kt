package com.example.drivetracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.drivetracker.data.entity.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("Select * from car order by brand ASC")
    fun getAllCars(): Flow<List<Car>>

    @Query("Select * from car where id = :id")
    fun getCar(id:Int):Flow<Car>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(car: Car)

    @Update
    suspend fun update(car: Car)

    @Delete
    suspend fun delete(car: Car)
}