package com.chanu.photocache.feature.home

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chanu.photocache.core.common.base.BaseViewModel
import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
import com.chanu.photocache.feature.home.model.HomeIntent
import com.chanu.photocache.feature.home.model.HomeSideEffect
import com.chanu.photocache.feature.home.model.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val imageLoaderRepository: ImageLoaderRepository,
) : BaseViewModel<HomeIntent, HomeState, HomeSideEffect>(
        initialState = HomeState(),
    ) {
    private val _images = MutableStateFlow<Map<String, Bitmap>>(emptyMap())
    val images: StateFlow<Map<String, Bitmap>> get() = _images

    val newsPagingFlow = homeRepository.getPhotos()
        .cachedIn(viewModelScope)
        .catch {
            postSideEffect(HomeSideEffect.ShowSnackBar(it))
        }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadImage -> loadImage(intent.url)
            is HomeIntent.ItemClick -> onItemClick(intent.id)
            is HomeIntent.SetPagingError -> showPagingStateError(intent.error)
        }
    }

    private fun loadImage(url: String) {
        if (_images.value.containsKey(url)) return

        viewModelScope.launch(Dispatchers.IO) {
            imageLoaderRepository.loadImage(url)
                .onSuccess { bitmap -> handleLoadSuccess(bitmap, url) }
                .onFailure { handleLoadFail(it) }
        }
    }

    private fun handleLoadSuccess(bitmap: Bitmap?, url: String) {
        if (bitmap == null) return
        _images.update { currentMap ->
            currentMap.toMutableMap().apply { put(url, bitmap) }
        }
    }

    private fun handleLoadFail(error: Throwable) {
        if (error == currentState.lastError) {
            intent { copy(lastError = error) }
            postSideEffect(HomeSideEffect.ShowSnackBar(error))
        }
    }

    private fun onItemClick(id: String) {
        postSideEffect(HomeSideEffect.NavigateToDetail(id))
    }

    private fun showPagingStateError(throwable: Throwable) {
        postSideEffect(HomeSideEffect.ShowSnackBar(throwable))
    }
}
