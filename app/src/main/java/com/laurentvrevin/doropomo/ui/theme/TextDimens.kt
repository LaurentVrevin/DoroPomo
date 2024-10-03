package com.laurentvrevin.doropomo.ui.theme

import androidx.compose.ui.unit.sp

/**
 * `TextDimens` centralizes all text sizes and line heights in the application.
 */
object TextDimens {

    // ---------- Text Sizes ---------- //
    val textSizeTiny = 10.sp // Used for labels or text icons
    val textSizeSmall = 12.sp // Used for descriptions or secondary text
    val textSizeMedium = 16.sp // Default size for button text and content text
    val textSizeLarge = 20.sp // Used for titles or level 3 headings
    val textSizeExtraLarge = 24.sp // Used for level 2 headings

    // ---------- Line Heights ---------- //
    val lineHeightSmall = 16.sp // Line height for tightly packed text
    val lineHeightMedium = 20.sp // Standard line height
    val lineHeightLarge = 24.sp // Line height for paragraphs

    // ---------- Letter Spacing ---------- //
    val letterSpacingSmall = 0.1.sp
    val letterSpacingMedium = 0.15.sp
    val letterSpacingLarge = 0.25.sp
}
