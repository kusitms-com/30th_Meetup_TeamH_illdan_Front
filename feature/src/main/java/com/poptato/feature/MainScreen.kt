package com.poptato.feature

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.poptato.navigation.NavRoutes
import com.poptato.navigation.loginNavGraph
import com.poptato.navigation.splashNavGraph

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()
    val slideDuration = 300

    Scaffold(
        bottomBar = { BottomNavBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .systemBarsPadding()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavRoutes.SplashGraph.route,
                exitTransition = { ExitTransition.None },
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        tween(slideDuration)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(slideDuration)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(slideDuration)
                    )
                }
            ) {
                splashNavGraph(navController = navController)
                loginNavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun BottomNavBar() {

}