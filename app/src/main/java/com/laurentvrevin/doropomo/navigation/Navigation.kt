package com.laurentvrevin.doropomo.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.laurentvrevin.doropomo.presentation.screens.SelectModeScreen
import com.laurentvrevin.doropomo.presentation.screens.TimerScreen
import com.laurentvrevin.doropomo.presentation.viewmodel.TimerStateViewModel
import com.laurentvrevin.doropomo.presentation.viewmodel.UserPreferencesViewModel

sealed class Screen(val route: String) {
    object TimerScreen : Screen("timer_screen")
    object SelectModeScreen : Screen("select_mode_screen")
}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    timerStateViewModel: TimerStateViewModel,
    userPreferencesViewModel: UserPreferencesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TimerScreen.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.TimerScreen.route,
            exitTransition = {
                slideOutHorizontally (
                    targetOffsetX = { -it },
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideInHorizontally (
                    initialOffsetX = { -it },
                    animationSpec = tween(500)
                )
            }
        ) {
            TimerScreen(
                timerStateViewModel = timerStateViewModel,
                userPreferencesViewModel = userPreferencesViewModel,
                onSelectModeClick = { navController.navigate(Screen.SelectModeScreen.route) }
            )
        }

        composable(
            route = Screen.SelectModeScreen.route,
            enterTransition = {
                slideInHorizontally  (
                    initialOffsetX = { it },
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(500)
                )
            }
        ) {
            SelectModeScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.popBackStack() },
                timerStateViewModel = timerStateViewModel,
                userPreferencesViewModel = userPreferencesViewModel
            )
        }
    }
}
