package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LongBreakOption(
    duration: Long,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Button(
        onClick = onSelect,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) Color.Red else Color.LightGray),
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp, 50.dp)
    ) {
        Text(
            text = "${duration / 1000 / 60} min",
            fontSize = 16.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}