package com.pew.weatherforecast.screens.mainscreens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pew.weatherforecast.R
import com.pew.weatherforecast.components.DaysWeatherRow
import com.pew.weatherforecast.components.OnNotFound
import com.pew.weatherforecast.data.DataOrException
import com.pew.weatherforecast.model.Weather
import com.pew.weatherforecast.navigation.ScreenRoutes
import com.pew.weatherforecast.screens.favouritscreen.SettingViewModel
import com.pew.weatherforecast.ui.theme.fontFamily
import com.pew.weatherforecast.utils.dateFormat
import com.pew.weatherforecast.utils.temperatureFormat
import com.pew.weatherforecast.utils.timeFormat
import com.pew.weatherforecast.widgets.WeatherAppBar
import retrofit2.Response


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    navController: NavHostController,
    city: String?,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    // Weather Object State
    var weatherData by remember {
        mutableStateOf<DataOrException<Response<Weather>, Boolean, Exception>>(
            DataOrException(
                null, true, null
            )
        )
    }
    //OnNotFoundLottieState
    val onNotFoundLottie = rememberLottieComposition(LottieCompositionSpec.RawRes(resId = R.raw.lottieanim))

    // Units State
    val unitFromDb = settingViewModel.unitsList.collectAsState().value
    var units by remember { mutableStateOf("imperial") }
    var isImperial by remember { mutableStateOf(value = false) }


    if (!unitFromDb.isNullOrEmpty()) {
        units = unitFromDb[0].units.split(" ")[0].lowercase()
        isImperial = units == "imperial"
    }

    weatherData = viewModel.data.value
    LaunchedEffect(city,units) {
        viewModel.weatherReport(city ?: "delhi", units = units)
    }




    if (viewModel.data.value.isLoding == true) {
        CircularProgressIndicator(
            strokeWidth = 2.dp, color = Color.Black
        )
    }
    else if(weatherData.data?.code() == 404) {
           OnNotFound(navController,onNotFoundLottie.value)
    }
    else {
        if (weatherData != null) {
            weatherData.data?.body()
                ?.let { MainScaffold(weather = it, navController = navController, isImperial) }
        }
    }
}

//fetching Data
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavHostController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + "," + weather.city.country,
            navController = navController,
            elevation = 90.dp,
            onButtonClicked = {
                navController.navigate(ScreenRoutes.SearchScreen.name)
            },
        )
    }) {
        Column(modifier = Modifier.padding(it)) {
            MainContent(data = weather, isImperial)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val weatherImage = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}@2x.png"
    // Main Content
    Column(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        //Date and day
        Text(
            text = dateFormat(data.list[0].dt).toString(),
            style = MaterialTheme.typography.titleMedium,
            fontFamily = fontFamily,
            fontSize = 17.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(8.dp)

        )

        // Weather detail showing column
        Surface(
            modifier = Modifier
                .padding(0.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xffFFFF80),
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Session Image
                WeatherStateImage(imageUrl = weatherImage, isRowIcon = false)
                // Temperature
                Text(
                    text = temperatureFormat(data.list[0].temp.day) + "°C",
                    fontFamily = fontFamily,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(1.dp)
                )
                //Session
                Text(
                    text = data.list[0].weather[0].main,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleMedium,

                    )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        //IconRow_Humditiy_Wind_Pressure
        IconsRowsHumidtyWindPressure(data, isImperial = isImperial)

        Divider()

        //Sunrise and sunset
        sunRiseAndSunset(data)


        //Lazy Days Row
        Column {
            LazyColumn {
                items(data.list) {
                    key(data.list[0].weather[0].id) {
                        DaysWeatherRow(it)
                    }
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun sunRiseAndSunset(data: Weather) {
    Row(
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        //SunRise
        Row() {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.noun_sunrise_6927225),
                contentDescription = stringResource(R.string.humidity_icons),
                Modifier.size(40.dp)
            )
            Text(
                text = "${timeFormat(data.list[0].sunrise)}",
                style = MaterialTheme.typography.titleMedium
            )

        }
        //Sunset
        Row() {
            Text(
                text = "${timeFormat(data.list[0].sunset)}",
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.noun_sunrise_6927225),
                contentDescription = stringResource(R.string.humidity_icons),
                Modifier.size(40.dp)
            )
        }

    }

}


//IconsRow  Humidity Wind  Pressure

@Composable
fun IconsRowsHumidtyWindPressure(data: Weather, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

    ) {
        //HumidityRow
        Row() {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.noun_humidity_2136183),
                contentDescription = stringResource(R.string.humidity_icons),
                Modifier.size(40.dp)
            )
            Text(
                text = "${data.list[0].humidity}" + "%",
                style = MaterialTheme.typography.titleMedium
            )
        }
        //WindRow
        Row() {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.noun_wind_6812404),
                contentDescription = stringResource(R.string.humidity_icons),
                Modifier.size(40.dp)
            )
            Text(
                text = "${data.list[0].speed}".toString().split(".")[0] + if(isImperial) " mp/h" else " m/s",
                style = MaterialTheme.typography.titleMedium
            )

        }
        //PressureRow
        Row() {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.noun_pressure_6394868),
                contentDescription = stringResource(R.string.humidity_icons),
                Modifier.size(40.dp)
            )
            Text(
                text = "${data.list[0].pressure} psi",
                style = MaterialTheme.typography.titleMedium
            )

        }

    }

}

//Weather Image Composable
@Composable
fun WeatherStateImage(imageUrl: Any, isRowIcon: Boolean) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.weather_icon),
        modifier = if (isRowIcon) {
            Modifier
                .padding(1.dp)
                .size(50.dp)
        } else {
            Modifier
                .padding(1.dp)
                .size(65.dp)
        }
    )
}