    package com.laurentvrevin.doropomo.presentation.screens

    import android.util.Log
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.ArrowBack
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.laurentvrevin.doropomo.domain.entity.PomodoroMode
    import com.laurentvrevin.doropomo.domain.entity.predefinedModes
    import com.laurentvrevin.doropomo.presentation.components.CycleSelector
    import com.laurentvrevin.doropomo.presentation.components.SelectableButton
    import com.laurentvrevin.doropomo.presentation.viewmodel.DoroPomoViewModel
    import com.laurentvrevin.doropomo.presentation.viewmodel.UserPreferencesViewModel
    import com.laurentvrevin.doropomo.ui.theme.Dimens

    @Composable
    fun SelectModeScreen(
        onBackClick: () -> Unit,
        onSaveClick: () -> Unit,
        doroPomoViewModel: DoroPomoViewModel,
        userPreferencesViewModel: UserPreferencesViewModel
    ) {
        val userPreferences by userPreferencesViewModel.userPreferences.collectAsState()
        val timerState by doroPomoViewModel.timerState.collectAsState()
        val numberOfCycles = timerState.cyclesBeforeLongBreak

        var longBreakDuration by remember { mutableIntStateOf(15) }
        var dontDisturbMode by remember { mutableStateOf(false) }

        val currentMode = remember {
            mutableStateOf(
                predefinedModes.find {
                    it.workDuration == userPreferences.workDuration &&
                            it.breakDuration == userPreferences.breakDuration
                } ?: predefinedModes.first()
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
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderSection(onBackClick)

                ModeSelector(
                    selectedMode = currentMode.value,
                    onModeSelected = { mode ->
                        currentMode.value = mode
                    }
                )

                CycleSelector(
                    numberOfCycles = numberOfCycles,
                    onIncrement = {
                    },
                    onDecrement = {
                    }
                )

                LongBreakTimeSelector(longBreakDuration) { longBreakDuration = it }

                DontDisturbModeCheckbox(dontDisturbMode) { dontDisturbMode = it }

                CustomizeTimerButton()

                SaveButton(){
                    userPreferencesViewModel.savePreferences(
                        userPreferences.copy(
                            workDuration = currentMode.value.workDuration,
                            breakDuration = currentMode.value.breakDuration
                        )
                    )
                    doroPomoViewModel.resetTimer()
                    onSaveClick()
                    Log.d("Verify", "SelectModeScreen, Preferences saved: ${userPreferences.copy(
                        workDuration = currentMode.value.workDuration,
                        breakDuration = currentMode.value.breakDuration
                    )}")
                }
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
    fun DontDisturbModeCheckbox(
        dontDisturbMode: Boolean,
        onCheckedChange: (Boolean) -> Unit
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don’t disturb mode", modifier = Modifier.weight(1f))
            Checkbox(
                checked = dontDisturbMode,
                onCheckedChange = { onCheckedChange(it) }
            )
        }
    }

    @Composable
    fun CustomizeTimerButton() {
        Button(
            onClick = { /* Logique pour personnaliser le timer */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(text = "Customize timer", color = Color.Red)
        }
    }

    @Composable
    fun SaveButton(onSaveClick: () -> Unit) {
        Button(
            onClick = onSaveClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Save", color = Color.White)
        }
    }