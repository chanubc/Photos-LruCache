package com.chanu.photocache.feature.home.model

import com.chanu.photocache.core.common.base.SideEffect

sealed interface HomeSideEffect : SideEffect {
    data class NavigateToDetail(val id: String) : HomeSideEffect

    data class ShowSnackBar(val message: Throwable) : HomeSideEffect
}
