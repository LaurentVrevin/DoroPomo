package com.laurentvrevin.doropomo.presentation.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.laurentvrevin.doropomo.presentation.components.CycleSelector
import com.laurentvrevin.doropomo.presentation.components.SelectableButton
import com.laurentvrevin.doropomo.presentation.viewmodel.DoroPomoViewModel
import com.laurentvrevin.doropomo.ui.theme.Dimens

@Composable
fun SelectModeScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: DoroPomoViewModel = hiltViewModel()  // Utilisation du ViewModel
) {
    // Les états pour chaque section de l'écran
    var selectedMode by remember { mutableStateOf("25/5") }
    var numberOfCycles by remember { mutableIntStateOf(4) }
    var longBreakDuration by remember { mutableIntStateOf(15) }
    var dontDisturbMode by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),  // Surface pour englober tout l'écran
        color = MaterialTheme.colorScheme.background // Utilisation de la couleur de fond du thème
    ){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.globalPaddingExtraLarge),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // En-tête de l'écran
        HeaderSection(onBackClick)

        // Sélecteur des modes de travail / pause prédéfinis
        ModeSelector(selectedMode, onModeSelected = { selectedMode = it })

        // Sélecteur du nombre de cycles avant la longue pause
        CycleSelector(
            numberOfCycles = numberOfCycles,
            onIncrement = { numberOfCycles++ },
            onDecrement = { if (numberOfCycles > 1) numberOfCycles-- }
        )

        // Sélecteur de la durée de la longue pause
        LongBreakTimeSelector(longBreakDuration) { longBreakDuration = it }

        // Mode "Don't Disturb"
        DontDisturbModeCheckbox(dontDisturbMode) { dontDisturbMode = it }

        // Bouton pour personnaliser le timer
        CustomizeTimerButton()

        // Bouton pour sauvegarder les paramètres
        SaveButton(onSaveClick)
    }
}
}

// En-tête de l'écran Settings
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

// Work / Pause mode selector
@Composable
fun ModeSelector(
    selectedMode: String,
    onModeSelected: (String) -> Unit
) {
    val modes = listOf("25/5", "50/10", "90/20")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        modes.forEach { mode ->
            SelectableButton(
                text = mode,
                isSelected = selectedMode == mode,
                onClick = { onModeSelected(mode) }
            )
        }
    }
}

// Long Break Selector
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

// Checkbox for "Don't Disturb" mode
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

// Timer Button
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

// Save Button
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
