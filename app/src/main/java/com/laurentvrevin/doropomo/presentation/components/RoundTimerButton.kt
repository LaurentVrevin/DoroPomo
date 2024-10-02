package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundTimerButton(
    mainText: String,          // ex: 25:00
    actionText: String,        // ex: play or pause
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    mainTextColor: Color = Color.White,
    actionTextColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(250.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = mainText,
                style = MaterialTheme.typography.displayLarge,
                color = mainTextColor
            )

            Text(
                text = actionText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = actionTextColor,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RoundButtonPreview() {
    RoundTimerButton(
        mainText = "25:00",
        actionText = "Play",
        onClick = { }
    )
}