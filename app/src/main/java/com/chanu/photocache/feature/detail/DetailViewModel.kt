package com.chanu.photocache.feature.detail

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.chanu.photocache.core.common.base.BaseViewModel
import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
import com.chanu.photocache.core.designsystem.type.ColorFilterType
import com.chanu.photocache.core.designsystem.type.LoadType
import com.chanu.photocache.core.navigation.Route
import com.chanu.photocache.feature.detail.model.DetailIntent
import com.chanu.photocache.feature.detail.model.DetailSideEffect
import com.chanu.photocache.feature.detail.model.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val imageLoaderRepository: ImageLoaderRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<DetailIntent, DetailState, DetailSideEffect>(
        initialState = DetailState(),
    ) {
    private val args = savedStateHandle.toRoute<Route.Detail>()
    private val _bitmapState = MutableStateFlow<Bitmap?>(null)
    val bitmapState: StateFlow<Bitmap?> get() = _bitmapState

    init {
        onIntent(DetailIntent.LoadInitialData)
        onIntent(DetailIntent.LoadThumbNail)
    }

    override fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadInitialData -> getInitialData(args.id)
            is DetailIntent.ClickBlurButton -> intent { copy(colorFilterType = ColorFilterType.BLUR) }
            is DetailIntent.ClickGrayButton -> intent { copy(colorFilterType = ColorFilterType.GRAYSCALE) }
            is DetailIntent.ClickDefaultButton -> intent { copy(colorFilterType = ColorFilterType.DEFAULT) }
            is DetailIntent.LoadThumbNail -> getThumbNailData(args.id)
        }
    }

    private fun getInitialData(id: String) {
        viewModelScope.launch {
            homeRepository.getDetailPhoto(id.toInt())
                .onSuccess {
                    loadImage(it)
                }.onFailure {
                    postSideEffect(DetailSideEffect.ShowSnackBar(it))
                }
        }
    }

    private fun loadImage(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            intent { copy(loadState = LoadType.Loading) }
            imageLoaderRepository.loadImage(url)
                .onSuccess { bitmap ->
                    if (bitmap != null) {
                        intent { copy(loadState = LoadType.Success) }
                        _bitmapState.update { bitmap }
                    }
                }
                .onFailure {
                    intent { copy(loadState = LoadType.Error) }
                    postSideEffect(DetailSideEffect.ShowSnackBar(it))
                }
        }
    }

    private fun getThumbNailData(id: String) {
        viewModelScope.launch {
            homeRepository.getThumbNailPhoto(id.toInt())
                .onSuccess {
                    loadThumbNailImage(it)
                }.onFailure {
                    postSideEffect(DetailSideEffect.ShowSnackBar(it))
                }
        }
    }

    private fun loadThumbNailImage(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imageLoaderRepository.loadThumbNail(url)
                .onSuccess { bitmap ->
                    if (_bitmapState.value == null) {
                        _bitmapState.update { bitmap }
                    }
                }
                .onFailure {
                    postSideEffect(DetailSideEffect.ShowSnackBar(it))
                }
        }
    }
}
