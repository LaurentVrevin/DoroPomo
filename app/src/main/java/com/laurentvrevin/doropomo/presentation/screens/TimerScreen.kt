package com.laurentvrevin.doropomo.presentation.screens

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.laurentvrevin.doropomo.R
import com.laurentvrevin.doropomo.presentation.components.RoundTimerButton
import com.laurentvrevin.doropomo.presentation.components.SettingsButton
import com.laurentvrevin.doropomo.presentation.components.ThemeSwitcher


@Composable
fun TimerScreen(
    isDarkTheme: Boolean,
    onThemeSwitch: () -> Unit,
    progression: Float,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color.Green)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ThemeSwitcher(
                        isDarkTheme = isDarkTheme,
                        onThemeSwitch = onThemeSwitch,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    SettingsButton(
                        onClick = onClick,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundTimerButton(
                mainText = "25:00",
                actionText = stringResource(id = R.string.round_timer_button_play),
                onClick = { }
            )
        }
    }


