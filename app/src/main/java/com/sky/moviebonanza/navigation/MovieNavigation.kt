package com.sky.moviebonanza.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sky.moviebonanza.screens.home.HomeScreen
import com.sky.moviebonanza.screens.home.HomeViewModel
import com.sky.moviebonanza.screens.splash.SplashScreen

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MovieBonanzaScreens.SplashScreen.name
    ) {
        composable(MovieBonanzaScreens.SplashScreen.name) {
            SplashScreen(navController)
        }

        composable(MovieBonanzaScreens.HomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController,homeViewModel)
        }
    }

}