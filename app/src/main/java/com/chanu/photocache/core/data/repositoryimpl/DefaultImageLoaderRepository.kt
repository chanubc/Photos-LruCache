package com.chanu.photocache.core.data.repositoryimpl

import android.graphics.Bitmap
import com.chanu.photocache.cache.datasource.BitmapFetcher
import com.chanu.photocache.cache.datasource.DiskCache
import com.chanu.photocache.cache.datasource.MemoryCache
import com.chanu.photocache.core.common.util.runSuspendCatching
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
import com.chanu.photocache.core.data.util.handleThrowable
import javax.inject.Inject

class DefaultImageLoaderRepository @Inject constructor(
    private val memoryCache: MemoryCache,
    private val diskCache: DiskCache,
    private val bitmapFetcher: BitmapFetcher,
) : ImageLoaderRepository {
    // 메모리 캐시, 디스크 캐시, 네트워크 순서로 이미지 조회
    override suspend fun loadImage(url: String): Result<Bitmap?> = runSuspendCatching {
        val memoryCachedBitmap = memoryCache.get(url)
        if (memoryCachedBitmap != null) {
            return@runSuspendCatching memoryCachedBitmap
        }

        val diskCachedBitmap = diskCache.get(url)
        if (diskCachedBitmap != null) {
            memoryCache.put(url, diskCachedBitmap)
            return@runSuspendCatching diskCachedBitmap
        }

        val bitmap = bitmapFetcher.fetchBitmapFromUrl(url)
        if (bitmap != null) {
            memoryCache.put(url, bitmap)
            diskCache.put(url, bitmap)
        }

        bitmap
    }.onFailure {
        return it.handleThrowable()
    }

    override suspend fun loadThumbNail(url: String): Result<Bitmap?> = runSuspendCatching {
        bitmapFetcher.fetchBitmapFromUrl(url)
    }.onFailure {
        return it.handleThrowable()
    }
}
