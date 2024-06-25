package com.pew.weatherforecast.data

class DataOrException<T,Boolean,Exception> (
    val data: T? = null,
    var isLoding: Boolean? = null,
    val exception: Exception? = null,
)