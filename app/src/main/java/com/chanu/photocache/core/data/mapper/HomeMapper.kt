package com.chanu.photocache.core.data.mapper

import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.network.dto.ResponsePhotoDto

private const val SIZE_200 = 200
private const val FILE_EXTENSION = ".webp"

fun ResponsePhotoDto.toPhotoModel(): PhotoModel = PhotoModel(
    id = id,
    downloadUrl = downloadUrl.toResizedUrl(),
)

fun String.toResizedUrl(): String = this
    .substringBeforeLast("/")
    .substringBeforeLast("/") + "/$SIZE_200$FILE_EXTENSION"
