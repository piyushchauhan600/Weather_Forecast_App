package com.pew.weatherforecast.screens.settingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pew.weatherforecast.model.Units
import com.pew.weatherforecast.screens.favouritscreen.SettingViewModel
import com.pew.weatherforecast.widgets.WeatherAppBar

@Composable
fun SettingScreen(
    navHostController: NavHostController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {


    //Toggle Button Condition


    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDb = settingViewModel.unitsList.collectAsState().value
    val defaultChoice = if (choiceFromDb.isNullOrEmpty())  measurementUnits[0] else choiceFromDb[0].units
    val choiceState = remember { mutableStateOf(defaultChoice) }
    var unitToogleState by remember(defaultChoice) { mutableStateOf(defaultChoice != "Imperial (F)") }

    




    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            navController = navHostController,
            elevation = 90.dp,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            isMainScreen = false,
        ){
            navHostController.popBackStack()
        }
    }) {paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change units of measurement",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = unitToogleState,
                    onCheckedChange = {
                             unitToogleState = !unitToogleState
                        choiceState.value = if (unitToogleState) "Metric (C)" else "Imperial (F)"
                    },
                    modifier = Modifier
                        .fillMaxWidth(fraction = .5f)
                        .padding(4.dp)
                        .clip(shape = RoundedCornerShape(corner = CornerSize(1.dp)))
                        .background(Color.Yellow.copy(0.5f))
                        .border(1.dp, Color.LightGray.copy(0.2f))
                ) {
                      Text(text = if (unitToogleState) "Celsius ℃" else "Fahrenheit ℉",
                          fontWeight = FontWeight.Normal,
                          color = Color.Black
                      )
                }
                //Button
                Button(onClick = {
                    settingViewModel.deleteAll()
                    settingViewModel.insertUnits(units = Units(units = choiceState.value))
                },
                    modifier = Modifier
                        .padding(4.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    contentPadding = PaddingValues(start = 40.dp, end = 40.dp),
                    colors = ButtonDefaults.buttonColors(Color.DarkGray)
                    ) {
                      Text(text = "Save",
                          fontSize = 16.sp,
                          fontWeight = FontWeight.Normal)
                }


            }

        }
    }
}