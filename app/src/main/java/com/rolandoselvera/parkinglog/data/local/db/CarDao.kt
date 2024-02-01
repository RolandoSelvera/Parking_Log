package com.rolandoselvera.parkinglog.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rolandoselvera.parkinglog.data.models.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: Car)

    @Update
    suspend fun update(car: Car)

    @Query("DELETE FROM Cars WHERE id = :carId")
    suspend fun deleteCarById(carId: Long)

    @Query("SELECT * FROM Cars ORDER BY id DESC")
    fun getCarsByOrder(): Flow<List<Car>>
}