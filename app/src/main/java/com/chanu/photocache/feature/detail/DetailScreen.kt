package com.chanu.photocache.feature.detail

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.chanu.photocache.R
import com.chanu.photocache.core.designsystem.component.thumbnail.ThumbNailComponent
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme
import com.chanu.photocache.feature.detail.component.DetailButton
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
    onClickDefaultBtn: () -> Unit = {},
    onClickGrayBtn: () -> Unit = {},
    onClickBlurBtn: () -> Unit = {},
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

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = 16.dp,
                    vertical = 20.dp,
                ),
        ) {
            DetailButton(
                text = stringResource(id = R.string.btn_default),
                onClick = onClickDefaultBtn,
            )
            DetailButton(
                text = stringResource(id = R.string.btn_gray),
                onClick = onClickGrayBtn,
            )
            DetailButton(
                text = stringResource(id = R.string.btn_blur),
                onClick = onClickBlurBtn,
            )
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
