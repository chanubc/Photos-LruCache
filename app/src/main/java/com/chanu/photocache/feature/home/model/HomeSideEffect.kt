package com.chanu.photocache.feature.home.model

import com.chanu.photocache.core.common.base.SideEffect
import com.chanu.photocache.core.model.PhotoModel

sealed interface HomeSideEffect : SideEffect {
    data class NavigateToDetail(val photoModel: PhotoModel) : HomeSideEffect

    data class ShowSnackBar(val message: Throwable) : HomeSideEffect
}
