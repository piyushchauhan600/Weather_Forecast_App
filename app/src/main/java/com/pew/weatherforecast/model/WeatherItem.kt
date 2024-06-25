package com.pew.weatherforecast.model

data class WeatherItem(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)