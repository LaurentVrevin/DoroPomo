package com.laurentvrevin.doropomo.ui.theme

import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable

object ButtonStyles {
    // Define button styles here
    @Composable
    fun primaryIconButtonColors() = IconButtonDefaults.filledIconButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
    // For Switch Buttons
    @Composable
    fun primarySwitchColors() = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colorScheme.primary,
        uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
        checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        checkedBorderColor = MaterialTheme.colorScheme.primary,
        uncheckedBorderColor = MaterialTheme.colorScheme.onSurface
    )
}