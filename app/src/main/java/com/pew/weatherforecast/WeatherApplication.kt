package com.pew.weatherforecast

import android.app.Application
import com.pew.weatherforecast.data.DataOrException
import com.pew.weatherforecast.model.WeatherObject
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow

@HiltAndroidApp
class WeatherApplication: Application() {

}