package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CircleProgressIndicator(
    progression: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp
){
    CircularProgressIndicator(
        progress = { progression },
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth,
    )
}