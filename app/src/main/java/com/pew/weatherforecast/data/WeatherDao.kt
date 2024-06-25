package com.pew.weatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pew.weatherforecast.model.City
import com.pew.weatherforecast.model.Favourite
import com.pew.weatherforecast.model.Units
import java.util.concurrent.Flow


@Dao
interface WeatherDao {

    //Get all favorites
    @Query("Select * from fav_tbl")
    fun getAllFavourites(): kotlinx.coroutines.flow.Flow<List<Favourite>>

    //Search Item By City
    @Query("Select * from fav_tbl where city =:city")
    suspend fun getFavouriteById(city: String): Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCity(favourite: Favourite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavouriteCity(favourite: Favourite)

    @Query("Delete from fav_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(favourite: Favourite): Unit


    //Setting Dao
    @Query("Select * from Setting_tbl")
    fun getUnits(): kotlinx.coroutines.flow.Flow<List<Units>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(favourite: Units)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnits(favourite: Units)

    @Query("Delete from Setting_tbl")
    suspend fun deleteall()

    @Delete
    suspend fun delete(units: Units): Unit













}