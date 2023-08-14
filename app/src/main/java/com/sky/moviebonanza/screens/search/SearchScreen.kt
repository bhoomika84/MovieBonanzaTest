package com.sky.moviebonanza.screens.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.sky.moviebonanza.R
import com.sky.moviebonanza.components.MovieAppBar
import com.sky.moviebonanza.screens.home.HomeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController){
    Scaffold(topBar = {
        MovieAppBar(title = stringResource(id = R.string.app_name),
            backIcon = Icons.Default.ArrowBack ,
            navController = navController){
            navController.popBackStack()
        }
    })
    {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            SearchContent(navController, it)
        }
    }
}

@Composable
fun SearchContent(navController: NavController, it: PaddingValues) {

}
