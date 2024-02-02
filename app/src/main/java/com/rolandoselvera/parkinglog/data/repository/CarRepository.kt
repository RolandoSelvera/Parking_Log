package com.rolandoselvera.parkinglog.data.repository

import com.rolandoselvera.parkinglog.data.local.db.CarDao
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.utils.ResultError
import com.rolandoselvera.parkinglog.utils.RegisterStatus
import kotlinx.coroutines.flow.Flow

class CarRepository(private val carDao: CarDao) {

    suspend fun insertCar(car: Car): ResultError {
        return try {
            carDao.insert(car)
            ResultError(RegisterStatus.SUCCESS, "Register successful!")
        } catch (e: Exception) {
            e.message
            ResultError(RegisterStatus.EXCEPTION, "Error: " + e.message)
        }
    }

    suspend fun updateCar(car: Car) {
        carDao.update(car)
    }

    suspend fun deleteCarById(carId: Long) {
        carDao.deleteCarById(carId)
    }

    fun getCarsByOrder(): Flow<List<Car>> {
        return carDao.getCarsByOrder()
    }
}
