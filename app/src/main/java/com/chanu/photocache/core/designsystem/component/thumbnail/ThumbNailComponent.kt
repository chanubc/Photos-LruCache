package com.chanu.photocache.core.designsystem.component.thumbnail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chanu.photocache.core.designsystem.component.indicator.PhotoCacheCircularIndicator
import com.chanu.photocache.core.designsystem.type.LoadType

@Composable
fun BoxScope.ThumbNailComponent(
    modifier: Modifier = Modifier,
    state: LoadType = LoadType.Loading,
) {
    if (state == LoadType.Loading) {
        Box(
            modifier = modifier
                .matchParentSize(),
        ) {
            PhotoCacheCircularIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
    }
}
