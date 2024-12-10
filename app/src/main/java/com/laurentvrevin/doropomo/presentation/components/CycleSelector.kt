package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laurentvrevin.doropomo.ui.theme.LightPrimary

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
        OutlinedButton(
            onClick = onDecrement,
            modifier = Modifier
                .size(width = 60.dp, height = 60.dp),
            border = BorderStroke(2.dp, LightPrimary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = LightPrimary),
            shape = MaterialTheme.shapes.small
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease cycles",

            )
        }
        Text(
            text = numberOfCycles.toString(),
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp),
            color = LightPrimary)

        OutlinedButton(
            onClick = onIncrement,
            modifier = Modifier
                .size(width = 60.dp, height = 60.dp),
            border = BorderStroke(2.dp, LightPrimary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = LightPrimary),
            shape = MaterialTheme.shapes.small
        ){
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase cycles"
            )
        }
    }
}