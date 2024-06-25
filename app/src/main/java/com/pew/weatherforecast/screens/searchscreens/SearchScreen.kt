package com.pew.weatherforecast.searchpackage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pew.weatherforecast.R
import com.pew.weatherforecast.navigation.ScreenRoutes
import com.pew.weatherforecast.widgets.WeatherAppBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavHostController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(id = R.string.search),
            icon = Icons.Default.ArrowBack,
            elevation = 90.dp,
            isMainScreen = false,
            navController = navController,
            onButtonClicked = {
                navController.popBackStack()
            },
        )
    }) {
        Column(modifier = Modifier.padding(it)) {
            Surface() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {mCity ->
                        navController.navigate(ScreenRoutes.Mainscreen.name+"/$mCity")
                        }
                    }
                }
            }
        }
    }

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
) {

    val searchQueryState = rememberSaveable { mutableStateOf("") }
    var validOrNot = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    Column {
        commonTextField(
            valueState = searchQueryState,
            isError = validOrNot,
            placeholder = "Enter city",
            onAction = KeyboardActions {
                if (!valid) {
                    validOrNot.value = true
                    return@KeyboardActions
                } else {
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
                }
            })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun commonTextField(
    valueState: MutableState<String>,
    isError: MutableState<Boolean>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeActions: ImeAction = ImeAction.Go,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            isError.value = false
        },
        label = {
            if (isError.value) Text(text = "Enter City !")
            else Text(text = placeholder)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "SearchIcon"
            )
        },
        maxLines = 1,
        singleLine = false,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeActions
        ),
        keyboardActions = onAction,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.DarkGray,
            errorBorderColor = Color.Red,
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black,
        ),
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        modifier = Modifier
            .width(500.dp)
            .padding(start = 10.dp, end = 10.dp),
        isError = isError.value

    )

}