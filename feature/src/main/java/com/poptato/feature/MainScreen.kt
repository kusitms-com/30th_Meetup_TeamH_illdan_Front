package com.poptato.feature

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.poptato.core.enums.BottomNavType
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray80
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.Primary60
import com.poptato.design_system.R
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.navigation.NavRoutes
import com.poptato.navigation.historyNavGraph
import com.poptato.navigation.loginNavGraph
import com.poptato.navigation.mainNavGraph
import com.poptato.navigation.splashNavGraph
import com.poptato.navigation.todayNavGraph
import com.poptato.ui.common.TodoBottomSheetContent
import com.poptato.ui.util.DismissKeyboardOnClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val sheetState = androidx.compose.material.rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val slideDuration = 300
    val showBottomSheet: (TodoItemModel) -> Unit = { item: TodoItemModel ->
        viewModel.onSelectedTodoItem(item)
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow
            .distinctUntilChanged()
            .collect { backStackEntry ->
                viewModel.setBottomNavType(backStackEntry.destination.route)
            }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is MainEvent.ShowTodoBottomSheet -> {
                    scope.launch {
                        sheetState.show()
                    }
                }
            }
        }
    }

    DismissKeyboardOnClick {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                TodoBottomSheetContent(
                    item = uiState.selectedItem
                )
            },
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            scrimColor = Color(0, 0, 0, 128)
        ) {
            Scaffold(
                bottomBar = {
                    if (uiState.bottomNavType != BottomNavType.DEFAULT) {
                        BottomNavBar(
                            type = uiState.bottomNavType,
                            onClick = { route: String ->
                                if (navController.currentDestination?.route != route) {
                                    navController.navigate(route)
                                }
                            },
                            modifier = Modifier.navigationBarsPadding()
                        )
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
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
                        mainNavGraph(navController = navController, showBottomSheet = showBottomSheet)
                        todayNavGraph(navController = navController)
                        historyNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    type: BottomNavType = BottomNavType.TODAY,
    onClick: (String) -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Gray100),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            iconId = if(type == BottomNavType.TODAY) R.drawable.ic_today_selected else R.drawable.ic_today_unselected,
            isSelected = type == BottomNavType.TODAY,
            type = BottomNavType.TODAY,
            onClick = onClick
        )
        BottomNavItem(
            iconId = if(type == BottomNavType.BACK_LOG) R.drawable.ic_list_selected else R.drawable.ic_list_unselected,
            isSelected = type == BottomNavType.BACK_LOG,
            type = BottomNavType.BACK_LOG,
            onClick = onClick
        )
        BottomNavItem(
            iconId = if(type == BottomNavType.HISTORY) R.drawable.ic_clock_selected else R.drawable.ic_clock_unselected,
            isSelected = type == BottomNavType.HISTORY,
            type = BottomNavType.HISTORY,
            onClick = onClick
        )
        BottomNavItem(
            iconId = if(type == BottomNavType.SETTINGS) R.drawable.ic_settings_selected else R.drawable.ic_settings_unselected,
            isSelected = type == BottomNavType.SETTINGS,
            type = BottomNavType.SETTINGS,
            onClick = onClick
        )
    }
}

@Composable
fun BottomNavItem(
    iconId: Int = -1,
    isSelected: Boolean = false,
    type: BottomNavType = BottomNavType.DEFAULT,
    onClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .size(width = 42.dp, height = 46.dp)
            .clickable {
                when (type) {
                    BottomNavType.TODAY -> {
                        onClick(NavRoutes.TodayScreen.route)
                    }

                    BottomNavType.BACK_LOG -> {
                        onClick(NavRoutes.BacklogScreen.route)
                    }

                    BottomNavType.HISTORY -> TODO()
                    BottomNavType.SETTINGS -> TODO()
                    BottomNavType.DEFAULT -> TODO()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = type.navName,
            style = PoptatoTypo.xsMedium,
            color = if (isSelected) Primary60 else Gray80
        )
    }
}
