package com.laurentvrevin.doropomo.presentation.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.laurentvrevin.doropomo.ui.theme.ButtonStyles


@Composable
fun SettingsButton(
    modifier: Modifier,
    onClick: () -> Unit
){
    FilledIconButton(
        onClick = onClick,
        colors = ButtonStyles.primaryIconButtonColors()

    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = MaterialTheme.colorScheme.onPrimary // Icon color
        )
    }
}