package com.laurentvrevin.doropomo.presentation.components


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomTextButton(
    onClick: () -> Unit,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier,
    style: TextStyle,
    fontWeight: FontWeight,
    text: String
){
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = MaterialTheme.shapes.small
        ){
            Text(
                text = text,
                modifier = modifier,
                style = style,
                fontWeight = fontWeight?: FontWeight.Normal
            )
    }
}