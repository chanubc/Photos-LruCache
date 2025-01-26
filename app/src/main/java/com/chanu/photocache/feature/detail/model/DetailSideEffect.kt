package com.chanu.photocache.feature.detail.model

import com.chanu.photocache.core.common.base.SideEffect

sealed interface DetailSideEffect : SideEffect {
    data class ShowSnackBar(val message: Throwable) : DetailSideEffect
}
