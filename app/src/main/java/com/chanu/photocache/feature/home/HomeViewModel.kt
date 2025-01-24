package com.chanu.photocache.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chanu.photocache.core.common.base.BaseViewModel
import com.chanu.photocache.core.common.base.EmptyState
import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
import com.chanu.photocache.feature.home.model.HomeIntent
import com.chanu.photocache.feature.home.model.HomeSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
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

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ItemClick -> onItemClick(intent.id)
        }
    }

    private fun onItemClick(id: String) {
        postSideEffect(HomeSideEffect.NavigateToDetail(id))
    }
}
