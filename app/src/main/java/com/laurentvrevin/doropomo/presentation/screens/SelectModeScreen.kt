    package com.laurentvrevin.doropomo.presentation.screens

    import android.content.Context
    import android.content.Intent
    import android.provider.Settings
    import androidx.compose.foundation.layout.*
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.ArrowBack
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.laurentvrevin.doropomo.domain.model.PomodoroMode
    import com.laurentvrevin.doropomo.domain.model.predefinedModes
    import com.laurentvrevin.doropomo.presentation.components.CycleSelector
    import com.laurentvrevin.doropomo.presentation.components.NormalButtonWithBorder
    import com.laurentvrevin.doropomo.presentation.components.NormalButtonWithoutBorder
    import com.laurentvrevin.doropomo.presentation.components.SelectableButton
    import com.laurentvrevin.doropomo.presentation.viewmodel.TimerStateViewModel
    import com.laurentvrevin.doropomo.presentation.viewmodel.UserPreferencesViewModel
    import com.laurentvrevin.doropomo.ui.theme.Dimens

    @Composable
    fun SelectModeScreen(
        onBackClick: () -> Unit,
        onSaveClick: () -> Unit,
        timerStateViewModel: TimerStateViewModel,
        userPreferencesViewModel: UserPreferencesViewModel
    ) {
        val userPreferences by userPreferencesViewModel.userPreferences.collectAsState()

        var numberOfCycles by remember { mutableIntStateOf(userPreferences.cyclesBeforeLongBreak) }
        var longBreakDuration by remember { mutableIntStateOf(15) }

        val context = LocalContext.current

        val currentMode = remember {
            mutableStateOf(
                predefinedModes.find {
                    it.workDuration == userPreferences.workDuration &&
                            it.breakDuration == userPreferences.breakDuration
                } ?: predefinedModes.first()
            )
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
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderSection(onBackClick)

                Spacer(modifier = Modifier.padding(16.dp))

                ModeSelector(
                    selectedMode = currentMode.value,
                    onModeSelected = { mode ->
                        currentMode.value = mode
                    }
                )

                CycleSelector(
                    numberOfCycles = numberOfCycles,
                    onIncrement = {
                        numberOfCycles++
                    },
                    onDecrement = {
                        numberOfCycles--
                    }
                )

                LongBreakTimeSelector(longBreakDuration) { longBreakDuration = it }

                DontDisturbModeButton(
                    onDndPermissionRequested = {
                        requestDndPermission(context)
                    }
                )

                Text("Focus Mode", style = MaterialTheme.typography.displayLarge)


                CustomizeTimerButton()

                SaveButton {
                    userPreferencesViewModel.savePreferences(
                        userPreferences.copy(
                            workDuration = currentMode.value.workDuration,
                            breakDuration = currentMode.value.breakDuration,
                            cyclesBeforeLongBreak = numberOfCycles,

                        )
                    )

                    timerStateViewModel.resetCountDown()
                    onSaveClick()
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }

    @Composable
    fun HeaderSection(onBackClick: () -> Unit) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Select Mode",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    @Composable
    fun ModeSelector(
        selectedMode: PomodoroMode?,
        onModeSelected: (PomodoroMode) -> Unit
    ) {
        val modes = predefinedModes

        Text(
            modifier = Modifier.padding(8.dp),
            text = "Choose the pomodoro mode that suits you in minutes :",
            fontSize = 12.sp)
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Work duration / Break duration",
            fontSize = 12.sp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            modes.forEach { mode ->
                SelectableButton(
                    text = mode.label,
                    isSelected = selectedMode == mode,
                    onClick = { onModeSelected(mode) }
                )
            }
        }
    }

    @Composable
    fun LongBreakTimeSelector(
        selectedDuration: Int,
        onDurationSelected: (Int) -> Unit
    ) {
        val durations = listOf(15, 30, 45)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            durations.forEach { duration ->
                SelectableButton(
                    text = "${duration}:00",
                    isSelected = selectedDuration == duration,
                    onClick = { onDurationSelected(duration) }
                )
            }
        }
    }

    @Composable
    fun DontDisturbModeButton(
        onDndPermissionRequested: () -> Unit
    ) {
        var showDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Activer le Focus Mode",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Configurer le mode Ne Pas Déranger")
            }
        }

        if (showDialog) {
            ShowDndPermissionDialog(
                onDismiss = { showDialog = false },
                onOpenSettings = {
                    onDndPermissionRequested()
                    showDialog = false
                }
            )
        }
    }

    @Composable
    fun ShowDndPermissionDialog(
        onDismiss: () -> Unit,
        onOpenSettings: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Activer le Focus Mode") },
            text = {
                Text(
                    "Pour activer le Focus Mode, veuillez accorder l'accès au mode \"Ne Pas Déranger\" à Doropomo dans les paramètres. " +
                            "Cela permettra de bloquer les notifications pendant vos sessions de concentration."
                )
            },
            confirmButton = {
                Button(onClick = { onOpenSettings() }) {
                    Text("Ouvrir les Paramètres")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Annuler")
                }
            }
        )
    }
    fun requestDndPermission(context: Context) {
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        context.startActivity(intent)
    }


    @Composable
    fun CustomizeTimerButton() {
        NormalButtonWithBorder(
            text = "Customize timer",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    @Composable
    fun SaveButton(onSaveClick: () -> Unit){
        NormalButtonWithoutBorder(
            text = "Save",
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
