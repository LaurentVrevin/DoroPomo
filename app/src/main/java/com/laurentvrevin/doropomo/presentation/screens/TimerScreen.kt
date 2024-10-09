package com.laurentvrevin.doropomo.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.laurentvrevin.doropomo.R
import com.laurentvrevin.doropomo.presentation.components.CircleProgressIndicator
import com.laurentvrevin.doropomo.presentation.components.CustomTextButton
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.globalPaddingExtraLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header
                TimerHeader(
                    isDarkTheme = isDarkTheme,
                    onThemeSwitch = onThemeSwitch,
                    onClick = onClick
                )

                // Body
                TimerBody(progression)

                // Footer
                TimerFooter(
                    onStopClick = onClick
                )
            }
        }
    }
}

//Header with theme switch and settings button
@Composable
fun TimerHeader(
    isDarkTheme: Boolean,
    onThemeSwitch: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Green),
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
            modifier = Modifier.padding(Dimens.Button.paddingMedium)
        )
    }
}

// Body with circle progress indicator and start/pause button
@Composable
fun TimerBody(
    progression: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(Dimens.globalPaddingLarge)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            CircleProgressIndicator(
                progression = progression,
                modifier = Modifier
                    .fillMaxSize(),
                strokeWidth = Dimens.globalPaddingMedium
            )

            StartPauseTimerButton(
                modifier = Modifier
                    .padding(Dimens.globalPaddingLarge),
                mainText = "25:00",
                actionText = stringResource(id = R.string.round_timer_button_play),
                onClick = { /* Logique de démarrage ou de pause */ },
                verticalArrangement = Arrangement.Center,
            )
        }
    }
}

//Footer with stop button
@Composable
fun TimerFooter(
    onStopClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextButton(
            modifier = Modifier
                .padding(Dimens.Button.paddingMedium),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            text = "Stop",
            onClick = onStopClick
        )

        Spacer(modifier = Modifier.height(Dimens.globalPaddingExtraLarge))
    }
}


