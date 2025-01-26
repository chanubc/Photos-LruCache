package com.chanu.photocache.core.designsystem.component.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.chanu.photocache.core.designsystem.component.indicator.PhotoCacheCircularIndicator
import com.chanu.photocache.core.designsystem.type.LoadType

@Composable
fun BoxScope.ThumbNailComponent(
    state: LoadType = LoadType.Loading,
    modifier: Modifier = Modifier,
) {
    if (state == LoadType.Loading) {
        Box(
            modifier = modifier
                .matchParentSize()
                .background(Color.LightGray.copy(alpha = 0.3f)),
        ) {
            PhotoCacheCircularIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
    }
}
