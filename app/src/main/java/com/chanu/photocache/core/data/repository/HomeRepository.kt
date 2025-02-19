package com.chanu.photocache.core.data.repository

import androidx.paging.PagingData
import com.chanu.photocache.core.model.PhotoModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getPhotos(): Flow<PagingData<PhotoModel>>

    suspend fun getDetailPhoto(id: Int): Result<String>
}
