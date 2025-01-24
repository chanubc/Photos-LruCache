package com.chanu.photocache.feature.home

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme
import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.feature.home.component.NetworkImage2
import com.chanu.photocache.feature.home.model.HomeSideEffect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lazyPagingItems = viewModel.newsPagingFlow.collectAsLazyPagingItems()
    val images by viewModel.images.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is HomeSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.id)
                    is HomeSideEffect.ShowSnackBar -> onShowErrorSnackBar(sideEffect.message)
                }
            }
    }

    /*  when {
          isLoading -> item { LoadingScreen() }
          isEmpty -> item { NewsNoticeEmptyScreen(emptyTxt = R.string.tv_news_info_empty) }
          else -> HomeScreen()
      }*/
    HomeScreen(
        lazyPagingItems = lazyPagingItems,
        navigateToDetail = navigateToDetail,
        onImageLoad = { url -> viewModel.loadImage(url) },
        images = images,
    )
}

@Composable
private fun HomeScreen(
    lazyPagingItems: LazyPagingItems<PhotoModel>,
    navigateToDetail: (String) -> Unit,
    images: Map<String, Bitmap>,
    onImageLoad: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id },
        ) { index ->
            val item = lazyPagingItems[index]
            Timber.tag("HomeScreen").d("item: $item")
            if (item != null) {
                NetworkImage2(
                    images[item.downloadUrl],
                    onLoad = { onImageLoad(item.downloadUrl) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    PhotoCacheTheme {
        HomeScreen(
            navigateToDetail = {},
            lazyPagingItems = flowOf(PagingData.from(emptyList<PhotoModel>())).collectAsLazyPagingItems(),
            images = emptyMap(),
            onImageLoad = { },
        )
    }
}
