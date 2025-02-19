package com.chanu.photocache.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PhotoModel(
    val id: String,
    val downloadUrl: String,
) : Parcelable
