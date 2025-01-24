package com.chanu.photocache.core.data.mapper

import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.network.dto.ResponsePhotoDto

private const val URL_SIZE = 200

fun ResponsePhotoDto.toPhotoModel(): PhotoModel {
    val modifiedDownloadUrl = downloadUrl
        .substringBeforeLast("/")
        .substringBeforeLast("/") + "/$URL_SIZE"
    return PhotoModel(
        id = id,
        author = author,
        downloadUrl = modifiedDownloadUrl,
    )
}
