package com.chanu.photocache.core.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chanu.photocache.core.common.util.runSuspendCatching
import com.chanu.photocache.core.data.mapper.toThumbNailModel
import com.chanu.photocache.core.data.paging.HomePagingSource
import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.data.util.handleThrowable
import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.network.datasource.HomeService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultHomeRepository @Inject constructor(
    private val homePagingSource: HomePagingSource,
    private val homeService: HomeService,
) : HomeRepository {
    override fun getPhotos(): Flow<PagingData<PhotoModel>> {
        return Pager(PagingConfig(pageSize = 30, prefetchDistance = 2)) {
            homePagingSource
        }.flow
    }

    override suspend fun getDetailPhoto(id: Int): Result<String> = runSuspendCatching {
        homeService.getPhotoInfo(id).downloadUrl
    }.onFailure {
        return it.handleThrowable()
    }

    override suspend fun getThumbNailPhoto(id: Int): Result<PhotoModel> = runSuspendCatching {
        homeService.getPhotoInfo(id).toThumbNailModel()
    }.onFailure {
        return it.handleThrowable()
    }
}
