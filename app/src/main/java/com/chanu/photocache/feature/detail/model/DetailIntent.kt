package com.chanu.photocache.feature.detail.model

import com.chanu.photocache.core.common.base.BaseIntent

sealed interface DetailIntent : BaseIntent {
    data class LoadInitialData(val isMainImage: Boolean) : DetailIntent

    data object ClickBlurButton : DetailIntent

    data object ClickGrayButton : DetailIntent

    data object ClickDefaultButton : DetailIntent

    data class LoadThumbNail(val isMainImage: Boolean) : DetailIntent
}
