package com.laurentvrevin.doropomo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.laurentvrevin.doropomo.presentation.screens.SelectModeScreen
import com.laurentvrevin.doropomo.presentation.screens.TimerScreen
import com.laurentvrevin.doropomo.presentation.viewmodel.DoroPomoViewModel

sealed class Screen(val route: String) {
    data object TimerScreen : Screen("timer_screen")
    data object SelectModeScreen : Screen("select_mode_screen")
}

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: DoroPomoViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.TimerScreen.route,
        modifier = modifier
    ) {
        composable(Screen.TimerScreen.route) {
            TimerScreen(
                viewModel = viewModel,
                onSelectModeClick = { navController.navigate(Screen.SelectModeScreen.route) }
            )
        }
        composable(Screen.SelectModeScreen.route) {
            SelectModeScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.popBackStack() }
            )
        }
    }
}
