package com.chanu.photocache.core.data.repositoryimpl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.chanu.photocache.cache.datasource.MemoryCache
import com.chanu.photocache.cache.ver2.CoroutineLRUDiskCache
import com.chanu.photocache.core.common.util.runSuspendCatching
import com.chanu.photocache.core.data.repository.ImageLoaderRepository
import com.chanu.photocache.core.data.util.toCustomError
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class DefaultImageLoaderRepository @Inject constructor(
    private val memoryCache: MemoryCache,
    private val diskCache: CoroutineLRUDiskCache,
) : ImageLoaderRepository {
    override suspend fun loadImage(url: String): Result<Bitmap?> = runSuspendCatching {
        // 1. 메모리 캐시에서 검색
        val memoryCachedBitmap = memoryCache.get(url)
        if (memoryCachedBitmap != null) {
            return@runSuspendCatching memoryCachedBitmap
        }

        // 2. 디스크 캐시에서 검색
        val diskCachedBitmap = diskCache.get(url)
        if (diskCachedBitmap != null) {
            memoryCache.put(url, diskCachedBitmap) // 메모리 캐시에 저장
            return@runSuspendCatching diskCachedBitmap
        }

        // 3. 네트워크 요청으로 다운로드
        val bitmap = fetchBitmapFromUrl(url)
        if (bitmap != null) {
            memoryCache.put(url, bitmap) // 메모리 캐시에 저장
            diskCache.put(url, bitmap) // 디스크 캐시에 저장
        }

        return@runSuspendCatching bitmap // 결과 반환
    }

    private fun fetchBitmapFromUrl(urlString: String): Bitmap? {
        return try {
            // URL 객체 생성
            val url = URL(urlString)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            // InputStream을 use로 처리
            connection.inputStream.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            throw e.toCustomError()
        }
    }
}
