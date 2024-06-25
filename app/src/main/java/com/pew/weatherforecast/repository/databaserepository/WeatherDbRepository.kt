package com.pew.weatherforecast.repository.databaserepository

import com.pew.weatherforecast.data.WeatherDao
import com.pew.weatherforecast.model.City
import com.pew.weatherforecast.model.Favourite
import com.pew.weatherforecast.model.Units
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(val weatherDao: WeatherDao) {

    //Get all favourite item
    fun getAllFavourite(): Flow<List<Favourite>> = weatherDao.getAllFavourites()
    //Insert Function in db
    suspend fun insertFavourite(favourite: Favourite) = weatherDao.insertFavouriteCity(favourite)
    //DeleteAll Favourite
    suspend fun deleteAll() = weatherDao.deleteAll()
    //DeleteBy Id
    suspend fun deleteById(favourite: Favourite) = weatherDao.delete(favourite)
    //Select By Id
    suspend fun selectById(city: String) = weatherDao.getFavouriteById(city)

    //Setting Repository
    fun getAllUnits(): Flow<List<Units>> = weatherDao.getUnits()
    //Insert Function in db
    suspend fun insertUnits(units: Units) = weatherDao.insertUnits(units)
    //DeleteAll Favourite
    suspend fun deleteall() = weatherDao.deleteall()
    //DeleteBy Id
    suspend fun deleteUnits(units: Units) = weatherDao.delete(units)




}