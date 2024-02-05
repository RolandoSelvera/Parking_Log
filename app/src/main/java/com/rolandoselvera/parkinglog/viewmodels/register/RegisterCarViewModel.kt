package com.rolandoselvera.parkinglog.viewmodels.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.data.models.results.ResultError
import com.rolandoselvera.parkinglog.data.repository.CarRepository
import com.rolandoselvera.parkinglog.data.models.enums.RegisterStatus
import kotlinx.coroutines.launch
import com.rolandoselvera.parkinglog.data.models.results.Result

class RegisterCarViewModel(private val carRepository: CarRepository) :
    ViewModel() {

    val registerCar = MutableLiveData<ResultError?>()
    val updateCar = MutableLiveData<ResultError?>()
    val retrieveCar = MutableLiveData<Result<Car?>>()
    val deleteCar = MutableLiveData<ResultError>()

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

    fun updateCar(car: Car) {
        viewModelScope.launch {
            try {
                val resultError = carRepository.updateCar(car)
                if (resultError.status == RegisterStatus.SUCCESS) {
                    updateCar.postValue(resultError)
                } else {
                    updateCar.postValue(resultError)
                }
            } catch (e: Exception) {
                updateCar.postValue(ResultError(RegisterStatus.EXCEPTION, e.message))
            }
        }
    }

    fun getCarById(carId: Int) {
        viewModelScope.launch {
            try {
                val result = carRepository.getCarById(carId)
                retrieveCar.postValue(result)
            } catch (e: Exception) {
                retrieveCar.postValue(Result.Error("Error: ${e.message}"))
            }
        }
    }

    fun deleteCarById(carId: Int) {
        viewModelScope.launch {
            try {
                val resultError = carRepository.deleteCarById(carId)
                if (resultError.status == RegisterStatus.SUCCESS) {
                    deleteCar.postValue(resultError)
                } else {
                    deleteCar.postValue(resultError)
                }
            } catch (e: Exception) {
                deleteCar.postValue(ResultError(RegisterStatus.EXCEPTION, e.message))
            }
        }
    }
}