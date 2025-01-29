package com.chanu.photocache.feature.home.model

import com.chanu.photocache.core.common.base.BaseIntent
import com.chanu.photocache.core.model.PhotoModel

sealed interface HomeIntent : BaseIntent {
    data class ItemClick(val photoModel: PhotoModel) : HomeIntent

    data class SetPagingError(val error: Throwable) : HomeIntent

    data class LoadImage(val url: String) : HomeIntent
}
