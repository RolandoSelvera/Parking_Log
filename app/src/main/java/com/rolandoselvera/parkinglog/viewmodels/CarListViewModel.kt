package com.rolandoselvera.parkinglog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rolandoselvera.parkinglog.data.local.db.CarDao
import com.rolandoselvera.parkinglog.data.models.Car

class CarListViewModel(private val carDao: CarDao) : ViewModel() {

    val allCarsDb: LiveData<List<Car>> = carDao.getCarsByOrder().asLiveData()
}