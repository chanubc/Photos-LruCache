package com.chanu.photocache.feature.home.model

import com.chanu.photocache.core.common.base.BaseState

data class HomeState(
    val lastError: Throwable? = null,
) : BaseState
