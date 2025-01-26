package com.chanu.photocache.core.designsystem.component.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chanu.photocache.core.designsystem.component.indicator.PhotoCacheCircularIndicator

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        PhotoCacheCircularIndicator()
    }
}
