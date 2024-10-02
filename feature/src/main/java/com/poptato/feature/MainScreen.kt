package com.poptato.feature

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.poptato.navigation.NavRoutes
import com.poptato.navigation.splashNavGraph

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar() }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.SplashGraph.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            splashNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavBar() {

}