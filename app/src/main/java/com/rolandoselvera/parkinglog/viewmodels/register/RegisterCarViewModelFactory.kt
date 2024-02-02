package com.rolandoselvera.parkinglog.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rolandoselvera.parkinglog.data.local.db.CarDao
import com.rolandoselvera.parkinglog.data.repository.CarRepository
import java.lang.IllegalArgumentException

class RegisterCarViewModelFactory(private val carDao: CarDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterCarViewModel::class.java)) {
            val carRepository = CarRepository(carDao)
            @Suppress("UNCHECKED_CAST")
            return RegisterCarViewModel(carRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}