package com.laurentvrevin.doropomo.presentation.screens

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
import com.laurentvrevin.doropomo.R
import com.laurentvrevin.doropomo.presentation.components.SettingsButton
import com.laurentvrevin.doropomo.presentation.components.StartPauseTimerButton
import com.laurentvrevin.doropomo.presentation.components.ThemeSwitchButton
import com.laurentvrevin.doropomo.ui.theme.Dimens


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
                .padding(Dimens.globalPaddingMedium)
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
                    ThemeSwitchButton(
                        isDarkTheme = isDarkTheme,
                        onThemeSwitch = onThemeSwitch,
                        modifier = Modifier
                            .padding(Dimens.Button.paddingMedium)
                    )
                    SettingsButton(
                        onClick = onClick,
                        modifier = Modifier
                            .padding(Dimens.Button.paddingMedium)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.globalPaddingMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StartPauseTimerButton(
                mainText = "25:00",
                actionText = stringResource(id = R.string.round_timer_button_play),
                onClick = { }
            )
        }
    }


