package com.laurentvrevin.doropomo.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laurentvrevin.doropomo.ui.theme.Dimens
import com.laurentvrevin.doropomo.ui.theme.Dimens.Button.heightMedium
import com.laurentvrevin.doropomo.ui.theme.Dimens.Button.widthSmall
import com.laurentvrevin.doropomo.ui.theme.LightOnPrimary
import com.laurentvrevin.doropomo.ui.theme.LightPrimary

@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (isSelected) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = LightPrimary,
                contentColor = LightOnPrimary
            ),
            modifier = modifier.size(widthSmall, heightMedium)
        ) {
            Text(text = text, fontSize = 16.sp)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            shape = CircleShape,
            border = BorderStroke(2.dp, LightPrimary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = LightPrimary
            ),
            modifier = modifier.size(widthSmall, heightMedium)
        ) {
            Text(text = text, fontSize = 16.sp)
        }
    }
}