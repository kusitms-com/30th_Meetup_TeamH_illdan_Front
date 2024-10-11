package com.poptato.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.poptato.backlog.BacklogScreen
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.login.KaKaoLoginScreen
import com.poptato.splash.SplashScreen
import com.poptato.yesterdaylist.YesterdayListScreen
import com.poptato.today.TodayScreen
import com.poptato.yesterdaylist.allcheck.AllCheckScreen

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

fun NavGraphBuilder.mainNavGraph(navController: NavHostController, showBottomSheet: (TodoItemModel) -> Unit) {
    navigation(startDestination = NavRoutes.BacklogScreen.route, route = NavRoutes.BacklogGraph.route) {
        composable(NavRoutes.BacklogScreen.route) {
            BacklogScreen(
                goToYesterdayList = { navController.navigate(NavRoutes.YesterdayListScreen.route) },
                showBottomSheet = showBottomSheet

            )
        }
    }
}

fun NavGraphBuilder.yesterdayListNavGraph(navController: NavHostController) {
    navigation(startDestination = NavRoutes.YesterdayListScreen.route, route = NavRoutes.YesterdayListGraph.route) {
        composable(NavRoutes.YesterdayListScreen.route) {
            YesterdayListScreen(
                goBackToBacklog = { navController.popBackStack() },
                showAllCheckPage = { navController.navigate(NavRoutes.YesterdayAllCheckScreen.route) }
            )
        }

        composable(NavRoutes.YesterdayAllCheckScreen.route) {
            AllCheckScreen(
                goBackToBacklog = { navController.navigate(NavRoutes.BacklogScreen.route) {
                    popUpTo(NavRoutes.BacklogScreen.route) { inclusive = true }
                } }
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