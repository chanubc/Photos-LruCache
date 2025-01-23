package com.chanu.photocache.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme

@Composable
fun HomeRoute(
    navigateToDetail: () -> Unit,
) {
    HomeScreen()
}

@Composable
private fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    PhotoCacheTheme {
        HomeScreen()
    }
}
