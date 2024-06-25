package com.pew.weatherforecast.screens.favouritscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pew.weatherforecast.model.Favourite
import com.pew.weatherforecast.repository.databaserepository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(val weatherDbRepository: WeatherDbRepository) :
    ViewModel() {

    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepository.getAllFavourite().distinctUntilChanged()
                .collect { fav_List ->
                    if (fav_List.isNullOrEmpty()) {
                        _favList.value = fav_List
                        Log.d("favouriteviewmodel", "List Empty !!")
                    } else {
                        _favList.value = fav_List
                    }
                }
          }
      }

    fun insertFavourite(favourite: Favourite) {
        viewModelScope.launch(Dispatchers.IO) { weatherDbRepository.insertFavourite(favourite) }
    }
    fun deleteFavourite(favourite: Favourite) {
        viewModelScope.launch(Dispatchers.IO) { weatherDbRepository.deleteById(favourite) }
    }
    fun findByIdFavourite(city: String) = viewModelScope.launch(Dispatchers.IO) { weatherDbRepository.selectById(city)  }
    }