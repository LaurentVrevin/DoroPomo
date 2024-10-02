package com.laurentvrevin.doropomo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.laurentvrevin.doropomo.presentation.screens.TimerScreen
import com.laurentvrevin.doropomo.ui.theme.DoropomoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Setup state of theme here
            var isDarkTheme by remember { mutableStateOf(false) }

            DoropomoTheme(darkTheme = isDarkTheme) {
                TimerScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeSwitch = { isDarkTheme = !isDarkTheme },
                    progression = 0.7f,
                    onClick = { }
                )
            }
        }
    }
}
