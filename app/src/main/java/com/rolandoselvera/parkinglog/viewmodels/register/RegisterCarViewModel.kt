package com.rolandoselvera.parkinglog.viewmodels.register

import android.util.Log
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

    private val resultStatus = MutableLiveData<ResultError?>()
    val registerCar: MutableLiveData<ResultError?> get() = resultStatus

    fun registerCar(car: Car) {
        viewModelScope.launch {
            try {
                val resultError = carRepository.insertCar(car)
                if (resultError.status == RegisterStatus.SUCCESS) {
                    resultStatus.postValue(resultError)
                    Log.d("TAG_SUCCESS", resultError.toString())
                } else {
                    resultStatus.postValue(resultError)
                    Log.d("TAG_ERROR", resultError.toString())
                }
            } catch (e: Exception) {
                resultStatus.postValue(ResultError(RegisterStatus.EXCEPTION, e.message))
                Log.d("TAG_EXCEPT", e.message.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG_ONCLEAR", "onCleared called")
        resultStatus.postValue(null)
    }
}