package com.laurentvrevin.doropomo.presentation.components


import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.laurentvrevin.doropomo.ui.theme.ButtonStyles.primarySwitchColors


@Composable
fun ThemeSwitchButton(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeSwitch: () -> Unit
) {
    // Switch between dark and light themes
    Switch(
        checked = isDarkTheme,
        onCheckedChange = { onThemeSwitch() }, // Call function to switch
        colors = primarySwitchColors()
        )

}