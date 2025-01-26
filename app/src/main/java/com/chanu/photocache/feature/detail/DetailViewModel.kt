package com.chanu.photocache.feature.detail

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.chanu.photocache.core.common.base.BaseViewModel
import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
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
import timber.log.Timber
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

    override fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadInitialData -> loadInitialData(args.id)
            is DetailIntent.LoadImage -> loadImage(intent.url)
        }
    }

    private fun loadInitialData(id: String) {
        viewModelScope.launch {
            homeRepository.getDetailPhoto(id.toInt())
                .onSuccess {
                    intent { copy(url = it.downloadUrl) }
                    onIntent(DetailIntent.LoadImage(it.downloadUrl))
                }.onFailure {
                    postSideEffect(DetailSideEffect.ShowSnackBar(it))
                    Timber.e(it)
                }
        }
    }

    private fun loadImage(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imageLoaderRepository.loadImage(url)
                .onSuccess { bitmap ->
                    if (bitmap != null) {
                        _bitmapState.update { bitmap }
                    }
                }
                .onFailure {
                    postSideEffect(DetailSideEffect.ShowSnackBar(it))
                }
        }
    }
}
