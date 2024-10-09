package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CycleSelector(
    numberOfCycles: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onDecrement) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease cycles"
            )
        }
        Text(text = numberOfCycles.toString(), fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        IconButton(onClick = onIncrement) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase cycles"
            )
        }
    }
}