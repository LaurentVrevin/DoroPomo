package com.laurentvrevin.doropomo.presentation.screens

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.laurentvrevin.doropomo.presentation.components.BreakPopup
import com.laurentvrevin.doropomo.presentation.components.CircleProgressIndicator
import com.laurentvrevin.doropomo.presentation.components.CustomTextButton
import com.laurentvrevin.doropomo.presentation.components.SettingsButton
import com.laurentvrevin.doropomo.presentation.components.StartPauseTimerButton
import com.laurentvrevin.doropomo.presentation.components.ThemeSwitchButton
import com.laurentvrevin.doropomo.presentation.viewmodel.DoroPomoViewModel
import com.laurentvrevin.doropomo.ui.theme.Dimens
import com.laurentvrevin.doropomo.utils.PreferencesManager


@Composable
fun TimerScreen(
    viewModel: DoroPomoViewModel = hiltViewModel(),
    onSelectModeClick: () -> Unit,
    onModeSelected: Boolean = false
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val workDuration by remember { mutableLongStateOf(preferencesManager.getSavedPomodoroMode().workDuration) }
    val breakDuration by remember { mutableLongStateOf(preferencesManager.getSavedPomodoroMode().breakDuration) }

    val timerState by viewModel.timerState
    val isDarkTheme by viewModel.isDarkTheme

    // Charger les préférences au démarrage de TimerScreen
    LaunchedEffect(Unit) {
        viewModel.applySavedPreferences()
    }

    // Affiche BreakPopup lorsque le temps de travail est écoulé
    if (!timerState.isRunning && timerState.remainingTime <= 0) {
        BreakPopup(
            onDismiss = { viewModel.stopAlarm() },
            onConfirm = {
                viewModel.stopAlarm()
                // Logique pour lancer la pause ici, par exemple, réinitialiser pour le temps de pause
                viewModel.startBreak()
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
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
                onThemeSwitch = { viewModel.toggleTheme() },
                onSettingButtonClick = { onSelectModeClick() }
            )

            TimerBody(
                progression = timerState.remainingTime / workDuration.toFloat(),
                remainingTime = timerState.remainingTime,
                onStartPauseClick = {
                    if (timerState.isRunning) {
                        viewModel.pauseTimer()
                    } else {
                        viewModel.startTimer()
                    }
                },
                isRunning = timerState.isRunning
            )

            TimerFooter(
                onStopClick = { viewModel.resetTimer() }
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

// Body with CircleProgressIndicator and StartPauseTimerButton
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

// FormatTime
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
