package com.chanu.photocache.core.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chanu.photocache.core.data.paging.HomePagingSource
import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultHomeRepository @Inject constructor(
    private val homePagingSource: HomePagingSource,
) : HomeRepository {
    override fun getPhotos(): Flow<PagingData<PhotoModel>> {
        return Pager(PagingConfig(pageSize = 30)) {
            homePagingSource
        }.flow
    }
}
