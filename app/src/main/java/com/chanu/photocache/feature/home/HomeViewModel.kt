package com.chanu.photocache.feature.home

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chanu.photocache.core.common.base.BaseViewModel
import com.chanu.photocache.core.common.base.EmptyState
import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
import com.chanu.photocache.core.model.CustomError
import com.chanu.photocache.feature.home.model.HomeIntent
import com.chanu.photocache.feature.home.model.HomeSideEffect
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
) : BaseViewModel<HomeIntent, EmptyState, HomeSideEffect>(
        initialState = EmptyState,
    ) {
    val newsPagingFlow = homeRepository.getPhotos()
        .cachedIn(viewModelScope)
        .catch {
            postSideEffect(HomeSideEffect.ShowSnackBar(it))
        }

    private val _images = MutableStateFlow<Map<String, Bitmap>>(emptyMap())
    val images: StateFlow<Map<String, Bitmap>> get() = _images

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ItemClick -> onItemClick(intent.id)
        }
    }

    fun loadImage(url: String) {
        if (_images.value.containsKey(url)) return

        viewModelScope.launch(Dispatchers.IO) {
            imageLoaderRepository.loadImage(url)
                .onSuccess { bitmap ->
                    if (bitmap == null) return@onSuccess
                    _images.update { currentMap ->
                        currentMap.toMutableMap().apply { put(url, bitmap) }
                    }
                }
                .onFailure {
                    postSideEffect(HomeSideEffect.ShowSnackBar(CustomError.ApiError("이미지 로드 실패")))
                }
        }
    }

    private fun onItemClick(id: String) {
        postSideEffect(HomeSideEffect.NavigateToDetail(id))
    }
}
