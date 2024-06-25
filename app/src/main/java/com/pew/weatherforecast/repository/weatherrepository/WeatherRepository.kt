package com.pew.weatherforecast.repository.weatherrepository


import android.util.Log
import com.pew.weatherforecast.data.DataOrException
import com.pew.weatherforecast.model.Weather
import com.pew.weatherforecast.network.WeatherApi
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getWeather(queryCity: String, units: String): DataOrException<Response<Weather>, Boolean, Exception> {
        val response = try {
                 weatherApi.getWeather(queryCity, units = units)
        } catch (e: Exception) {
            return DataOrException(exception = e)
        }
        return DataOrException(data = response)
    }
}