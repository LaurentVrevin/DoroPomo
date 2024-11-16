package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.laurentvrevin.doropomo.ui.theme.ButtonStyles
import com.laurentvrevin.doropomo.ui.theme.LightOnPrimary
import com.laurentvrevin.doropomo.ui.theme.LightPrimary

@Composable
fun NormalButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        modifier = Modifier
            .padding(16.dp),
        colors = ButtonStyles.primaryButtonColors(),
        onClick = onClick )
    {
        Text(
            text = text,
            color = LightOnPrimary
        )

    }


}