package com.chanu.photocache.core.designsystem.type

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix

enum class ColorFilterType {
    GRAYSCALE,
    BLUR,
    DEFAULT,
    ;

    fun toColorFilter(): ColorFilter? = when (this) {
        GRAYSCALE -> ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        else -> null
    }
}
