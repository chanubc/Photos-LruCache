package com.chanu.photocache.cache.datasource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import javax.inject.Inject

class DiskCache @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val maxSize: Int = 100
    private val cacheDir: File = File(context.cacheDir, "image_cache")
    private val mutex = Mutex()

    private val lruMap = LinkedHashMap<String, String>(0, 0.75f, true)

    init {
        if (!cacheDir.exists()) cacheDir.mkdirs()
    }

    suspend fun get(key: String): Bitmap? =
        mutex.withLock {
            val cacheFile = File(cacheDir, key.hashCode().toString())
            if (cacheFile.exists()) {
                lruMap[key] = cacheFile.absolutePath
                cacheFile.setLastModified(System.currentTimeMillis())
                BitmapFactory.decodeFile(cacheFile.absolutePath)
            } else {
                null
            }
        }

    suspend fun put(key: String, bitmap: Bitmap) =
        mutex.withLock {
            if (lruMap.size >= maxSize) {
                val oldestKey = lruMap.keys.firstOrNull()
                if (oldestKey != null) {
                    val oldestFile = File(lruMap[oldestKey]!!)
                    oldestFile.delete()
                    lruMap.remove(oldestKey)
                }
            }

            val cacheFile = File(cacheDir, key.hashCode().toString())
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, cacheFile.outputStream())
            lruMap[key] = cacheFile.absolutePath
        }
}
