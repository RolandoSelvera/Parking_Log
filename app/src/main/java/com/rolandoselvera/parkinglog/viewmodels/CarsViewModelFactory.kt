package com.rolandoselvera.parkinglog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rolandoselvera.parkinglog.data.local.db.CarDao
import java.lang.IllegalArgumentException

class CarsViewModelFactory(private val carDao: CarDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return CarListViewModel(carDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}