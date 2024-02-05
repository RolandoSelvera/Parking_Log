package com.rolandoselvera.parkinglog.data.repository

import com.rolandoselvera.parkinglog.data.local.db.CarDao
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.data.models.results.ResultError
import com.rolandoselvera.parkinglog.data.models.enums.RegisterStatus
import kotlinx.coroutines.flow.Flow
import com.rolandoselvera.parkinglog.data.models.results.Result

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

    suspend fun updateCar(car: Car): ResultError {
        return try {
            carDao.update(car)
            ResultError(RegisterStatus.SUCCESS, "Update car successful!")
        } catch (e: Exception) {
            e.message
            ResultError(RegisterStatus.EXCEPTION, "Error: " + e.message)
        }
    }

    suspend fun deleteCarById(carId: Int): ResultError {
        return try {
            carDao.deleteCarById(carId)
            ResultError(RegisterStatus.SUCCESS, "Car removed successfully!")
        } catch (e: Exception) {
            ResultError(RegisterStatus.EXCEPTION, "Error: " + e.message)
        }
    }

    suspend fun getCarById(id: Int): Result<Car?> {
        return try {
            val car = carDao.getCarById(id)
            Result.Success(car)
        } catch (e: Exception) {
            Result.Error("Error: ${e.message}")
        }
    }

    fun getCarsByOrder(): Flow<List<Car>> {
        return carDao.getCarsByOrder()
    }
}
