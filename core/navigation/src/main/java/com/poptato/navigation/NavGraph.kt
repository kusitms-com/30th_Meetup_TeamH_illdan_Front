package com.poptato.navigation

import androidx.compose.material.ModalBottomSheetState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.poptato.backlog.BacklogScreen
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.login.KaKaoLoginScreen
import com.poptato.splash.SplashScreen
import com.poptato.today.TodayScreen
import kotlinx.coroutines.flow.SharedFlow

fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {
    navigation(startDestination = NavRoutes.SplashScreen.route, route = NavRoutes.SplashGraph.route) {
        composable(NavRoutes.SplashScreen.route) {
            SplashScreen(
                goToKaKaoLogin = { navController.navigate(NavRoutes.KaKaoLoginGraph.route) }
            )
        }
    }
}

fun NavGraphBuilder.loginNavGraph(navController: NavHostController) {
    navigation(startDestination = NavRoutes.KaKaoLoginScreen.route, route = NavRoutes.KaKaoLoginGraph.route) {
        composable(NavRoutes.KaKaoLoginScreen.route) {
            KaKaoLoginScreen(
                goToBacklog = { navController.navigate(NavRoutes.BacklogScreen.route) }
            )
        }
    }
}

fun NavGraphBuilder.backlogNavGraph(
    navController: NavHostController,
    showBottomSheet: (TodoItemModel) -> Unit,
    todoBottomSheetClosedFlow: SharedFlow<Unit>,
    updateDeadlineFlow: SharedFlow<String>,
) {
    navigation(startDestination = NavRoutes.BacklogScreen.route, route = NavRoutes.BacklogGraph.route) {
        composable(NavRoutes.BacklogScreen.route) {
            BacklogScreen(
                showBottomSheet = showBottomSheet,
                todoBottomSheetClosedFlow = todoBottomSheetClosedFlow,
                updateDeadlineFlow = updateDeadlineFlow,
            )
        }
    }
}

fun NavGraphBuilder.todayNavGraph(navController: NavHostController) {
    navigation(startDestination = NavRoutes.TodayScreen.route, route = NavRoutes.TodayGraph.route) {
        composable(NavRoutes.TodayScreen.route) {
            TodayScreen(
                goToBacklog = { navController.navigate(NavRoutes.BacklogScreen.route) }
            )
        }
    }
}