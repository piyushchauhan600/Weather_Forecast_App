package com.pew.weatherforecast.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pew.weatherforecast.screens.aboutscreen.AboutScreen
import com.pew.weatherforecast.screens.favouritscreen.FavouriteScreen
import com.pew.weatherforecast.screens.mainscreens.MainScreen
import com.pew.weatherforecast.screens.mainscreens.WeatherViewModel
import com.pew.weatherforecast.screens.settingscreen.SettingScreen
import com.pew.weatherforecast.searchpackage.SearchScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = (ScreenRoutes.Mainscreen.name+"/delhi")) {
        //Navigation MainScreen
        if (navController.currentBackStackEntry?.id.isNullOrEmpty()) {
        }
        composable(ScreenRoutes.Mainscreen.name+"/{city}",
            arguments = listOf(
                navArgument("city") {
                    type = NavType.StringType }),
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }

        ) {navBack ->
           val city =  navBack.arguments?.getString("city")
            val viewModel: WeatherViewModel = hiltViewModel()
            MainScreen(viewModel,navController,city)
        }
        //Navigation SearchScreen
        composable(ScreenRoutes.SearchScreen.name,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }) {
           SearchScreen(navController)
        }
        //Navigation AboutScreen
        composable(ScreenRoutes.AboutScreen.name,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }) {
            AboutScreen(navController)
        }
        //Navigation Favourite Screen
        composable(ScreenRoutes.FavoriteScreen.name,

            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }
        ) {
            FavouriteScreen(navController)
        }
        //Navigation Setting Screen
        composable(ScreenRoutes.Settingscreen.name,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            }) {
            SettingScreen(navController)
        }

    }
}

