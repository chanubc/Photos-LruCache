package com.chanu.photocache.feature.home.component

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun PagingStateHandler(
    lazyPagingItems: LazyPagingItems<*>,
    loadingContent: @Composable () -> Unit,
    emptyContent: @Composable () -> Unit,
    errorContent: @Composable (Throwable) -> Unit,
    content: @Composable () -> Unit,
) {
    when {
        lazyPagingItems.loadState.refresh is LoadState.Loading -> loadingContent()

        lazyPagingItems.loadState.refresh is LoadState.Error -> {
            val error = (lazyPagingItems.loadState.refresh as LoadState.Error).error
            errorContent(error)
        }

        lazyPagingItems.itemCount == 0 &&
            lazyPagingItems.loadState.refresh is LoadState.NotLoading -> emptyContent()

        else -> content()
    }
}
