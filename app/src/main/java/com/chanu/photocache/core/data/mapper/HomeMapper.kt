package com.chanu.photocache.core.data.mapper

import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.network.dto.ResponsePhotoDto

private const val SIZE_200 = 200
private const val SIZE_1000 = 1000

fun ResponsePhotoDto.toPhotoModel(): PhotoModel =
    toPhotoModelWithSize(SIZE_200)

fun ResponsePhotoDto.toPhotoDetailModel(): PhotoModel =
    toPhotoModelWithSize(SIZE_1000)

private fun ResponsePhotoDto.toPhotoModelWithSize(size: Int): PhotoModel {
    val modifiedDownloadUrl = downloadUrl
        .substringBeforeLast("/")
        .substringBeforeLast("/") + "/$size"

    return PhotoModel(
        id = id,
        author = author,
        downloadUrl = modifiedDownloadUrl,
    )
}
