package com.pew.weatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pew.weatherforecast.model.Favourite
import com.pew.weatherforecast.navigation.ScreenRoutes
import com.pew.weatherforecast.screens.favouritscreen.FavouriteViewModel
import com.pew.weatherforecast.ui.theme.fontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "TITLE",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 1.dp,
    navController: NavHostController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {
    // ShowDrop State
    val showDropDownMenu = remember { mutableStateOf(false) }
    //Toast State
    var isShowOrNot = remember { mutableStateOf(false) }
    // Context
    val context = LocalContext.current

    if (showDropDownMenu.value) {
        ShowingDropDownMenu(menuState = showDropDownMenu, navController = navController)
    }

    TopAppBar(
        // Title App Bar
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
        },
        //Color App Bar
        colors = TopAppBarDefaults.topAppBarColors
            (containerColor = MaterialTheme.colorScheme.inverseSurface),

        // Modifier App Bar
          modifier = Modifier.fillMaxWidth(),

        //Action Icons
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onButtonClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                IconButton(onClick = { showDropDownMenu.value = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "MoreVertical",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            } else Box {}
        },
        //Navigation Icon
        navigationIcon = {
            if (!isMainScreen)
                IconButton(
                    onClick = { onButtonClicked.invoke() },
                ) {
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Arrow Back",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            if (isMainScreen) {
                val titleList = title.split(",")
                val isAlreadyFavourite = favouriteViewModel
                    .favList.collectAsState().value.filter {
                        it.city == title.split(",")[0]
                    }
                if (isAlreadyFavourite.isNullOrEmpty()) {
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourite",
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .scale(0.9f)
                            .clickable {
                                favouriteViewModel
                                    .insertFavourite(
                                        Favourite(
                                            city = titleList[0],
                                            country = titleList[1],
                                        )
                                    )
                                    .run {
                                        isShowOrNot.value = true
                                    }
                            }
                            .padding(top = 13.dp, start = 10.dp),
                        tint = Color.Red
                    )

                } else {
                    isShowOrNot.value = false
                    Box {}
                }
                showToast(context, isShowOrNot)
            }
        },
    )
}


fun showToast(context: Context, showOrNot: MutableState<Boolean>) {
    if (showOrNot.value) {
        Toast.makeText(context, " Added to favourites ", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowingDropDownMenu(
    menuState: MutableState<Boolean>,
    navController: NavHostController
) {
    val items = listOf("About", "Favourite", "Setting")
    var expanded by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { menuState.value = false },
            modifier = Modifier
                .padding(0.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, items ->
                DropdownMenuItem(text = {
                    Text(text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 18.sp,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                            )
                        ) {
                            append(items)
                        }
                    })
                }, onClick = {
                    expanded = false
                    menuState.value = false
                    when (items) {
                        "About" -> navController.navigate(ScreenRoutes.AboutScreen.name)

                        "Favourite" -> navController.navigate(ScreenRoutes.FavoriteScreen.name)

                        "Setting" -> navController.navigate(ScreenRoutes.Settingscreen.name)
                    }
                },
                    modifier = Modifier.padding(1.dp),
                    {
                        Icon(
                            imageVector = when (items) {
                                "About" -> Icons.Default.Info
                                "Favourite" -> Icons.Default.Favorite
                                else -> Icons.Default.Settings

                            }, contentDescription = "DropDownMenu"
                        )
                    }
                )
            }
        }

    }

}
