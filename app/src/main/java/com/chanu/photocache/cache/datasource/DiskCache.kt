package com.chanu.photocache.cache.datasource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class DiskCache @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val cacheDir: File = context.cacheDir

    fun get(key: String): Bitmap? {
        val file = File(cacheDir, key.hashCode().toString())
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    fun put(key: String, bitmap: Bitmap) {
        val file = File(cacheDir, key.hashCode().toString())
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, out)
        }
    }

    companion object {
        private const val QUALITY = 80
    }
}
