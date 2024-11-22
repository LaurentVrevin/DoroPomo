package com.laurentvrevin.doropomo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.laurentvrevin.doropomo.navigation.Navigation
import com.laurentvrevin.doropomo.presentation.viewmodel.DoroPomoViewModel
import com.laurentvrevin.doropomo.ui.theme.DoropomoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val viewModel: DoroPomoViewModel = hiltViewModel()
            val isDarkTheme by viewModel.isDarkTheme

            DoropomoTheme(darkTheme = isDarkTheme) {
                val navController: NavHostController = rememberNavController()
                Navigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}
