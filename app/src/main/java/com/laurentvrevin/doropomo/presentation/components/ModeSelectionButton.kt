package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laurentvrevin.doropomo.domain.entity.PomodoroMode

@Composable
fun ModeSelectionButton(
    mode: PomodoroMode,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Button(
        onClick = onSelect,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) Color.Red else Color.LightGray),
        modifier = Modifier
            .padding(8.dp)
            .size(70.dp)
    ) {
        Text(text = mode.label, fontSize = 16.sp, color = if (isSelected) Color.White else Color.Black)
    }
}