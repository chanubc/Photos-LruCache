package com.chanu.photocache.feature.home.model

import com.chanu.photocache.core.common.base.BaseIntent

sealed interface HomeIntent : BaseIntent {
    data class ItemClick(val id: String) : HomeIntent
}
