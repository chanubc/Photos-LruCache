package com.chanu.photocache.feature.detail

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme
import com.chanu.photocache.feature.detail.model.DetailIntent
import com.chanu.photocache.feature.detail.model.DetailSideEffect
import com.chanu.photocache.feature.home.component.PhotoItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel(),
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val cachedBitmap by viewModel.bitmapState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is DetailSideEffect.ShowSnackBar -> onShowErrorSnackBar(sideEffect.message)
                }
            }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(DetailIntent.LoadInitialData)
    }

    DetailScreen(
        imageBitmap = cachedBitmap,
    )
}

@Composable
private fun DetailScreen(
    imageBitmap: Bitmap?,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        PhotoItem(
            bitmap = imageBitmap,
            onLoad = {},
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    PhotoCacheTheme {
        DetailScreen(
            imageBitmap = null,
        )
    }
}
