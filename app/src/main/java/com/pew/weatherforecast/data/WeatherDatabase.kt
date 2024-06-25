package com.pew.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pew.weatherforecast.model.Favourite
import com.pew.weatherforecast.model.Units

@Database(entities = [Favourite::class, Units::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}