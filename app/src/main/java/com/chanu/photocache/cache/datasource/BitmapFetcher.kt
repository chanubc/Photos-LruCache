package com.chanu.photocache.cache.datasource

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.chanu.photocache.core.data.util.toCustomError
import java.net.HttpURLConnection
import java.net.URL

class BitmapFetcher {
    private val urlConnectionTimeout: Int = 5000
    private val urlReadTimeout: Int = 5000

    fun fetchBitmapFromUrl(urlString: String): Bitmap? {
        return try {
            val url = URL(urlString)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

            connection.connectTimeout = urlConnectionTimeout
            connection.readTimeout = urlReadTimeout
            connection.doInput = true
            connection.connect()

            connection.inputStream.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            throw e.toCustomError()
        }
    }
}
