package com.laurentvrevin.doropomo.presentation.components


import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ThemeSwitcher(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeSwitch: () -> Unit
) {
    // Switch between dark and light themes
    Switch(
        checked = isDarkTheme,
        onCheckedChange = { onThemeSwitch() }, // Call function to switch
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primary,
            uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
            checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            checkedBorderColor = MaterialTheme.colorScheme.primary,
            uncheckedBorderColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier.size(44.dp)
    )

}