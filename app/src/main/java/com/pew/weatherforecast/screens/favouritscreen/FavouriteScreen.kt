package com.pew.weatherforecast.screens.favouritscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pew.weatherforecast.model.Favourite
import com.pew.weatherforecast.navigation.ScreenRoutes
import com.pew.weatherforecast.widgets.WeatherAppBar

@Composable
fun FavouriteScreen(navHostController: NavHostController, favouriteViewModel: FavouriteViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Like cities",
            icon = Icons.Sharp.ArrowBack,
            isMainScreen = false,
            favouriteViewModel = hiltViewModel(),
            navController = navHostController,
            elevation = 90.dp,
        ) {
            navHostController.popBackStack()
        }
    })
    {paddingvalues ->
         Column(modifier = Modifier.padding(paddingvalues)) {
             val listOfFavouritesCities = favouriteViewModel.favList.collectAsState().value
             LazyColumn {
                 items(listOfFavouritesCities) {
                     favouriteItems(citiesCountry = it,favouriteViewModel ,navHostController)
                 }
             }
         }
    }
}

@Composable
fun favouriteItems(citiesCountry: Favourite,favouriteViewModel: FavouriteViewModel ,navHostController: NavHostController) {


    Surface(modifier = Modifier
        .padding(start = 10.dp, end = 10.dp, bottom = 2.dp, top = 4.dp)
        .fillMaxWidth()
        .fillMaxHeight()
        .clickable { navHostController.navigate(ScreenRoutes.Mainscreen.name+"/${citiesCountry.city}") {
            popUpTo(ScreenRoutes.FavoriteScreen.name) {
                inclusive = true
            }
            launchSingleTop = true
        } },
        tonalElevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        border = BorderStroke(0.5f.dp,Color.LightGray),
        color = Color(0xffFFFF80)
    ) {
        Row(modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //Cities Name
            Text(text = citiesCountry.city,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth(0.6f),
                textAlign = TextAlign.Left
            )
            // Country Name
            Text(text = citiesCountry.country,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.3f)
            )
            Column(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.End
                ) {
                Icon(imageVector = Icons.Sharp.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red.copy(0.5f),
                    modifier = Modifier.clickable {
                        favouriteViewModel.deleteFavourite(citiesCountry)
                    }
                )
            }
        }
    }
}
