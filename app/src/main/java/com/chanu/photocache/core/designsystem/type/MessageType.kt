package com.chanu.photocache.core.designsystem.type

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.chanu.photocache.R

enum class MessageType(
    @StringRes val title: Int,
    val color: Color = Color.White,
) {
    ERROR(
        title = R.string.error_message,
        color = Color.Red,
    ),
    EMPTY(
        title = R.string.empty_message,
        color = Color.DarkGray,
    ),
}
