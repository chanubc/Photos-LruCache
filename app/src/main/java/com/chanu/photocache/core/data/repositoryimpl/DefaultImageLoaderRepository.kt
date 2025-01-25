package com.chanu.photocache.core.data.repositoryimpl

import android.graphics.Bitmap
import com.chanu.photocache.cache.datasource.BitmapFetcher
import com.chanu.photocache.cache.datasource.MemoryCache
import com.chanu.photocache.cache.ver2.CoroutineLRUDiskCache
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
import javax.inject.Inject

class DefaultImageLoaderRepository @Inject constructor(
    private val memoryCache: MemoryCache,
    private val diskCache: CoroutineLRUDiskCache,
    private val bitmapFetcher: BitmapFetcher,
) : ImageLoaderRepository {
    override suspend fun loadImage(url: String): Result<Bitmap?> = runCatching {
        // 1. 메모리 캐시에서 검색
        val memoryCachedBitmap = memoryCache.get(url)
        if (memoryCachedBitmap != null) {
            return@runCatching memoryCachedBitmap
        }

        // 2. 디스크 캐시에서 검색
        val diskCachedBitmap = diskCache.get(url)
        if (diskCachedBitmap != null) {
            memoryCache.put(url, diskCachedBitmap) // 메모리 캐시에 저장
            return@runCatching diskCachedBitmap
        }

        // 3. 네트워크 요청으로 다운로드
        val bitmap = bitmapFetcher.fetchBitmapFromUrl(url)
        if (bitmap != null) {
            memoryCache.put(url, bitmap) // 메모리 캐시에 저장
            diskCache.put(url, bitmap) // 디스크 캐시에 저장
        }

        bitmap
    }
}
