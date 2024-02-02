package com.rolandoselvera.parkinglog.viewmodels.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.utils.ResultError
import com.rolandoselvera.parkinglog.data.repository.CarRepository
import com.rolandoselvera.parkinglog.utils.RegisterStatus
import kotlinx.coroutines.launch

class RegisterCarViewModel(private val carRepository: CarRepository) :
    ViewModel() {

    val registerCar = MutableLiveData<ResultError?>()

    fun registerCar(car: Car) {
        viewModelScope.launch {
            try {
                val resultError = carRepository.insertCar(car)
                if (resultError.status == RegisterStatus.SUCCESS) {
                    registerCar.postValue(resultError)
                } else {
                    registerCar.postValue(resultError)
                }
            } catch (e: Exception) {
                registerCar.postValue(ResultError(RegisterStatus.EXCEPTION, e.message))
            }
        }
    }
}