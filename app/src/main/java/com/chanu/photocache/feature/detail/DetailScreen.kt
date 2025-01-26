package com.chanu.photocache.feature.detail

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import com.chanu.photocache.core.designsystem.component.ThumbNailComponent
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme
import com.chanu.photocache.feature.detail.component.ZoomInOutBox
import com.chanu.photocache.feature.detail.model.DetailIntent
import com.chanu.photocache.feature.detail.model.DetailSideEffect
import com.chanu.photocache.feature.detail.model.DetailState
import com.chanu.photocache.feature.home.component.PhotoContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel(),
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val cachedBitmap by viewModel.bitmapState.collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is DetailSideEffect.ShowSnackBar -> onShowErrorSnackBar(sideEffect.message)
                }
            }
    }

    DetailScreen(
        state = state,
        imageBitmap = cachedBitmap,
        onClickGrayBtn = { viewModel.onIntent(DetailIntent.ClickGrayButton) },
        onClickBlurBtn = { viewModel.onIntent(DetailIntent.ClickBlurButton) },
        onClickDefaultBtn = { viewModel.onIntent(DetailIntent.ClickDefaultButton) },
    )
}

@Composable
private fun DetailScreen(
    state: DetailState,
    imageBitmap: Bitmap?,
    onClickGrayBtn: () -> Unit = {},
    onClickBlurBtn: () -> Unit = {},
    onClickDefaultBtn: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        ZoomInOutBox(
            maxScale = 5f,
            modifier = Modifier.align(Alignment.Center),
            content = { modifier ->
                PhotoContent(
                    bitmap = imageBitmap,
                    colorFilterType = state.colorFilterType,
                    modifier = modifier,
                    content = { ThumbNailComponent(state = state.loadState) },
                )
            },
        )

        Button(
            onClick = onClickDefaultBtn,
            modifier = Modifier.align(Alignment.BottomStart),
        ) {
            Text(text = "default")
        }

        Button(
            onClick = onClickGrayBtn,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Text(text = "gray")
        }
        Button(
            onClick = onClickBlurBtn,
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            Text(text = "blur")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    PhotoCacheTheme {
        DetailScreen(
            imageBitmap = null,
            state = DetailState(),
        )
    }
}
