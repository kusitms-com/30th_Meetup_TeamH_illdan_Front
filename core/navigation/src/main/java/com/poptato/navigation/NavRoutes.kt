package com.poptato.navigation

sealed class NavRoutes(val route: String) {
    // Splash Graph
    data object SplashGraph: NavRoutes("splash_graph")
    data object SplashScreen: NavRoutes("splash")
}