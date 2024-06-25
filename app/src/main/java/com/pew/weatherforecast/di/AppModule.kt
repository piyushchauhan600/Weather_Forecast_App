package com.pew.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.pew.weatherforecast.data.WeatherDao
import com.pew.weatherforecast.data.WeatherDatabase
import com.pew.weatherforecast.model.Favourite
import com.pew.weatherforecast.model.Weather
import com.pew.weatherforecast.network.WeatherApi
import com.pew.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    //Weather Dao Provider
    @Singleton
    @Provides
    fun providesWeatherDao(weatherDatabase: WeatherDatabase)
    =  weatherDatabase.weatherDao()


    @Singleton
    @Provides
    fun providesWeatherDatabase(@ApplicationContext context: Context)
    =  Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        name = ("Weather Database")
    ).fallbackToDestructiveMigration()
        .build()



    //WeatherApi Retrofit Object
    @Singleton
    @Provides
    fun ProvidesWeatherApi() : WeatherApi  {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

}