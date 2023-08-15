package com.sky.moviebonanza.navigation

enum class MovieBonanzaScreens {
    SplashScreen,
    HomeScreen,
    DetailsScreen;

    companion object {
        fun fromRoute(route: String): MovieBonanzaScreens = when (route.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            DetailsScreen.name -> DetailsScreen
            else -> throw java.lang.IllegalArgumentException("Route $route is not recognized")
        }
    }
}