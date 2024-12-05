package com.laurentvrevin.doropomo.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.laurentvrevin.doropomo.presentation.components.CircleProgressIndicator
import com.laurentvrevin.doropomo.presentation.components.CustomTextButton
import com.laurentvrevin.doropomo.presentation.components.SettingsButton
import com.laurentvrevin.doropomo.presentation.components.StartPauseTimerButton
import com.laurentvrevin.doropomo.presentation.components.ThemeSwitchButton
import com.laurentvrevin.doropomo.presentation.viewmodel.TimerStateViewModel
import com.laurentvrevin.doropomo.presentation.viewmodel.UserPreferencesViewModel
import com.laurentvrevin.doropomo.ui.theme.Dimens


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TimerScreen(
    timerStateViewModel: TimerStateViewModel,
    userPreferencesViewModel: UserPreferencesViewModel,
    onSelectModeClick: () -> Unit,
) {

    val timerState by timerStateViewModel.timerState.collectAsState()
    val isRunning by timerStateViewModel.isRunning.collectAsState()

    // Récupération des valeurs depuis le timerState
    val workDuration = timerState.workDuration
    val remainingTime = timerState.remainingTime
    val cyclesBeforeLongBreak = timerState.cyclesBeforeLongBreak

    val isDarkTheme by userPreferencesViewModel.isDarkTheme
    val userPreferences by userPreferencesViewModel.userPreferences.collectAsState()

    LaunchedEffect(userPreferences) {
        timerStateViewModel.observePreferences(userPreferencesViewModel.userPreferences)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),

        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.globalPaddingExtraLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TimerHeader(
                isDarkTheme = isDarkTheme,
                onThemeSwitch = { userPreferencesViewModel.toggleTheme() },
                onSettingButtonClick = { onSelectModeClick() }
            )
            // Ajout de logs pour vérifier les données
            Log.d("VERIFY", "Remaining time: $remainingTime, Work duration: $workDuration")

            TimerBody(
                progression = if (workDuration > 0) remainingTime / workDuration.toFloat() else 0f,
                remainingTime = remainingTime,
                onStartPauseClick = {
                    if (isRunning) timerStateViewModel.pauseCountdown()
                    else timerStateViewModel.startCountdown()
                },
                isRunning = isRunning
            )

            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "Cycles avant pause longue : $cyclesBeforeLongBreak",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            TimerFooter(
                onStopClick = { timerStateViewModel.resetCountDown() }
            )
        }
    }
}
// Header with ThemeSwitchButton and SettingsButton
@Composable
fun TimerHeader(
    isDarkTheme: Boolean,
    onThemeSwitch: () -> Unit,
    onSettingButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
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
            onClick = onSettingButtonClick,
            modifier = Modifier.padding(Dimens.Button.paddingMedium)
        )
    }
}

@Composable
fun TimerBody(
    progression: Float,
    remainingTime: Long,
    onStartPauseClick: () -> Unit,
    isRunning: Boolean
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
                mainText = formatTime(remainingTime),
                actionText = if (isRunning) "Pause" else "Start",
                onClick = onStartPauseClick,
                verticalArrangement = Arrangement.Center,
            )

        }
    }
}

@Composable
fun formatTime(timeInMillis: Long): String {
    val minutes = (timeInMillis / 1000) / 60
    val seconds = (timeInMillis / 1000) % 60
    return "%02d:%02d".format(minutes, seconds)
}



// Footer with StopButton
@Composable
fun TimerFooter(
    onStopClick: () -> Unit,

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