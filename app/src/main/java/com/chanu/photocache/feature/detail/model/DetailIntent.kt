package com.chanu.photocache.feature.detail.model

import com.chanu.photocache.core.common.base.BaseIntent

sealed interface DetailIntent : BaseIntent {
    data object LoadInitialData : DetailIntent

    data class LoadImage(val url: String) : DetailIntent
}
