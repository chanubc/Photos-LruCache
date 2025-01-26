package com.chanu.photocache.feature.detail.model

import com.chanu.photocache.core.common.base.BaseState
import com.chanu.photocache.core.designsystem.type.ColorFilterType
import com.chanu.photocache.core.designsystem.type.LoadType

data class DetailState(
    val colorFilterType: ColorFilterType = ColorFilterType.DEFAULT,
    val loadState:LoadType = LoadType.Loading
) : BaseState
