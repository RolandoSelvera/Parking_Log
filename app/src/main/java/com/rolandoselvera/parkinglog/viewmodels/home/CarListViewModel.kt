package com.rolandoselvera.parkinglog.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.data.repository.CarRepository

class CarListViewModel(private val carRepository: CarRepository) :
    ViewModel() {

    val allCarsDb: LiveData<List<Car>> = carRepository.getCarsByOrder().asLiveData()
}