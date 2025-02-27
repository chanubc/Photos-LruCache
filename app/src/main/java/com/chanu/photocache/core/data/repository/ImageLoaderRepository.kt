package com.chanu.photocache.core.data.repository

import android.graphics.Bitmap

interface ImageLoaderRepository {
    suspend fun loadImage(url: String): Result<Bitmap>
}
