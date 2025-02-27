package com.chanu.photocache.core.designsystem.extension.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.chanu.photocache.core.designsystem.type.ColorFilterType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit = {},
): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        onClick()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Modifier.noRippleDebounceClickable(
    onClick: () -> Unit,
): Modifier = composed {
    var clickable by remember { mutableStateOf(true) }

    pointerInteropFilter {
        if (clickable) {
            onClick()
            clickable = false
            CoroutineScope(Dispatchers.Main).launch {
                delay(500) // 500밀리초 딜레이
                clickable = true
            }
        }
        true
    }
}

fun Modifier.applyBlurStyle(
    style: ColorFilterType,
): Modifier = composed {
    when (style) {
        ColorFilterType.BLUR -> this.blur(
            radiusX = 10.dp,
            radiusY = 10.dp,
        )

        else -> this
    }
}
