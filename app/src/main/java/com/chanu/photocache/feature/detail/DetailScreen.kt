package com.chanu.photocache.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme

@Composable
fun DetailRoute(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    DetailScreen()
}

@Composable
private fun DetailScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    PhotoCacheTheme {
        DetailScreen()
    }
}
