package com.chanu.photocache.feature.detail.component

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ZoomInOutBox(
    modifier: Modifier = Modifier,
    contentAspectRatio: Float = 1f,
    maxScale: Float = 5f,
    minScale: Float = 1f,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    val offsetSaver = Saver<Offset, List<Float>>(
        save = { listOf(it.x, it.y) },
        restore = { Offset(it[0], it[1]) },
    )

    var scale by rememberSaveable { mutableStateOf(1f) }
    var offset by rememberSaveable(stateSaver = offsetSaver) { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(
        modifier = modifier.aspectRatio(contentAspectRatio),
    ) {
        val state = rememberTransformableState { zoomChange, panChange, _ ->
            scale = (scale * zoomChange).coerceIn(minScale, maxScale)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
        }

        content(
            Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state),
        )
    }
}
