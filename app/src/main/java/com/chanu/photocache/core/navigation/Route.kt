package com.chanu.photocache.core.navigation

import com.chanu.photocache.core.model.PhotoModel
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data class Detail(val photoModel: PhotoModel) : Route
}
