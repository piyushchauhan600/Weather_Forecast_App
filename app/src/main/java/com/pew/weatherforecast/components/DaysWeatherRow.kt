package com.pew.weatherforecast.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pew.weatherforecast.model.WeatherObject
import com.pew.weatherforecast.screens.mainscreens.WeatherStateImage
import com.pew.weatherforecast.utils.dateFormat
import com.pew.weatherforecast.utils.temperatureFormat


@SuppressLint("NewApi")
@Composable
fun DaysWeatherRow(data: WeatherObject,
                   onClick: () -> Unit = {},
) {
    val weatherCondition = "https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png"
    // Weather Row Composable
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight(0.088f)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFFF80)),
        elevation = CardDefaults.cardElevation(4.dp),
        ) {
        Row(modifier = Modifier.padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            ) {
            //Days
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                )) {
                     append(dateFormat(data.dt)?.split(",")?.get(0) ?:"!" )
                }
            })
            // Weather Image Icon
            WeatherStateImage(imageUrl = weatherCondition, isRowIcon = true)

            //Description
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                )) {
                    append(data.weather[0].description.toUpperCase())
                }
            })

            //High
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                    textDecoration = TextDecoration.Underline
                )) {
                    append(temperatureFormat(data.temp.min)+"°C")
                }
            })

            //Low
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                    textDecoration = TextDecoration.Underline
                )) {
                    append(temperatureFormat(data.temp.max)+"°C")
                }
            })

        }
    }




}