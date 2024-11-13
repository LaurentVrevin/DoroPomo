package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BreakPopup(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Commencer la pause")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Ignorer")
            }
        },
        title = { Text("Pause") },
        text = { Text("Votre temps de travail est écoulé. Voulez-vous commencer la pause ?") }
    )
}