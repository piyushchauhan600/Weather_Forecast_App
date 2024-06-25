package com.pew.weatherforecast.screens.favouritscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pew.weatherforecast.model.Units
import com.pew.weatherforecast.repository.databaserepository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(val weatherDbRepository: WeatherDbRepository) :
    ViewModel() {

    private val _unitsList = MutableStateFlow<List<Units>>(emptyList())
    val unitsList = _unitsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepository.getAllUnits().distinctUntilChanged()
                .collect { unitsList ->
                    if (unitsList.isNullOrEmpty()) {
                        _unitsList.value = unitsList
                        Log.d("favouriteviewmodel", "List Empty !!")
                    } else {
                        _unitsList.value = unitsList
                    }
                }
        }
    }

    fun insertUnits(units: Units) {
        viewModelScope.launch(Dispatchers.IO) { weatherDbRepository.insertUnits(units) }
    }

    fun deleteUnits(units: Units) {
        viewModelScope.launch(Dispatchers.IO) { weatherDbRepository.deleteUnits(units) }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) { weatherDbRepository.deleteall() }
    }
}