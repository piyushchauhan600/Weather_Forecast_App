package com.pew.weatherforecast.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.pew.weatherforecast.ui.theme.fontFamily
import com.pew.weatherforecast.widgets.WeatherAppBar


@Composable
fun OnNotFound(navHostController: NavHostController, onNotFoundLottie: LottieComposition?) {

    Scaffold (topBar = {
        WeatherAppBar(
            title = "",
            navController = navHostController,
            isMainScreen = false,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            elevation = 90.dp) {
            navHostController.popBackStack()
        }
    })
    {
        Spacer(modifier = Modifier.padding(it))

        Surface(modifier = Modifier
            .padding()
            .fillMaxSize()) {
            //Lottie animation onNotFound City
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    outlineMasksAndMattes = true,
                    modifier = Modifier.size(400.dp),
                    iterations = LottieConstants.IterateForever,
                    enableMergePaths = true,
                    composition = onNotFoundLottie,
                )
                Text(text = "City was not found !",
                    fontFamily = fontFamily,
                    fontSize = 30.sp,
                    lineHeight = 50.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray,

                    )
            }
        }
    }
}




