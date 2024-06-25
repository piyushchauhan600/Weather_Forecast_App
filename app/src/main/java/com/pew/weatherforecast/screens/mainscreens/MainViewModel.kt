package com.pew.weatherforecast.screens.mainscreens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pew.weatherforecast.data.DataOrException
import com.pew.weatherforecast.model.Weather
import com.pew.weatherforecast.repository.weatherrepository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(val weatherRepository: WeatherRepository) : ViewModel() {
    //Splash Screen State
    private val isReady = MutableStateFlow(true)
    val isReadys = isReady.asStateFlow()

    init {
        viewModelScope.launch {
            isReady.value = false
        }
    }

    //MutableState Of Weather Data
    val data: MutableState<DataOrException<Response<Weather>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

//    init {
//        weatherReport(city = "delhi")
//    }

        fun weatherReport(city: String, units: String) {
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            data.value.isLoding = true
            data.value = weatherRepository.getWeather(queryCity = city, units = units)
            if (data.value.data.toString().isNotEmpty()) data.value.isLoding = false

       }
    }
}
