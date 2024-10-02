package com.laurentvrevin.doropomo.ui.theme

import androidx.compose.ui.unit.dp

/**
 * `Dimens` centralizes all dimensions used for padding, margin, and spacing
 * in the application to ensure consistency and simplify maintenance.
 */
object Dimens {

    // ---------- Global Padding and Margins ---------- //
    val globalPaddingTiny = 4.dp
    val globalPaddingSmall = 8.dp
    val globalPaddingMedium = 16.dp
    val globalPaddingLarge = 24.dp
    val globalPaddingExtraLarge = 32.dp

    val globalMarginSmall = 8.dp
    val globalMarginMedium = 16.dp
    val globalMarginLarge = 24.dp

    // ---------- Button Padding and Sizes ---------- //
    object Button {
        val paddingSmall = 8.dp // Small padding for compact buttons
        val paddingMedium = 16.dp // Standard button padding
        val paddingLarge = 24.dp // Larger padding for bigger buttons

        val height = 48.dp // Standard button height
        val widthSmall = 100.dp // Small button width
        val widthMedium = 200.dp // Medium button width
        val widthLarge = 300.dp // Large button width

        // Example usage: .padding(Dimens.Button.paddingMedium)
    }

    // ---------- Card Padding and Spacing ---------- //
    object Card {
        val paddingSmall = 12.dp // Padding inside a card
        val paddingMedium = 16.dp
        val paddingLarge = 24.dp

        val marginSmall = 8.dp // Margin around the card
        val marginMedium = 16.dp
        val marginLarge = 24.dp

        val cornerRadiusSmall = 4.dp // Small corner radius for card edges
        val cornerRadiusMedium = 8.dp
        val cornerRadiusLarge = 16.dp

        // Example usage: .padding(Dimens.Card.paddingMedium)
    }

    // ---------- Icon Sizes and Padding ---------- //
    object Icon {
        val sizeSmall = 16.dp // Small icon size
        val sizeMedium = 24.dp // Standard icon size
        val sizeLarge = 32.dp // Large icon size

        val paddingSmall = 4.dp // Small padding around icons
        val paddingMedium = 8.dp
        val paddingLarge = 12.dp

        // Example usage: .size(Dimens.Icon.sizeMedium).padding(Dimens.Icon.paddingSmall)
    }

    // ---------- Spacing Between Elements ---------- //
    object Spacing {
        val elementSpacingSmall = 8.dp // Small spacing between elements
        val elementSpacingMedium = 16.dp // Medium spacing between elements
        val elementSpacingLarge = 24.dp // Large spacing between elements
        val elementSpacingExtraLarge = 32.dp

        // Example usage: Arrangement.spacedBy(Dimens.Spacing.elementSpacingMedium)
    }

    // ---------- Toolbar Dimensions ---------- //
    object Toolbar {
        val height = 56.dp // Standard toolbar height
        val paddingHorizontal = 16.dp // Horizontal padding for the toolbar
        val paddingVertical = 8.dp // Vertical padding for the toolbar

        // Example usage: .height(Dimens.Toolbar.height)
    }

    // ---------- Divider Heights ---------- //
    object Divider {
        val heightSmall = 1.dp // Small divider height
        val heightMedium = 2.dp
        val heightLarge = 4.dp

        // Example usage: Divider(modifier = Modifier.height(Dimens.Divider.heightMedium))
    }

    // ---------- Screen Margins ---------- //
    object Screen {
        val marginHorizontal = 16.dp // Horizontal margin for screen content
        val marginVertical = 16.dp

        // Example usage: .padding(horizontal = Dimens.Screen.marginHorizontal)
    }
}
