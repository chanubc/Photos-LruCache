package com.chanu.photocache.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chanu.photocache.core.data.mapper.toPhotoModel
import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.network.datasource.HomeService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomePagingSource @Inject constructor(
    private val homeService: HomeService,
) : PagingSource<Int, PhotoModel>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoModel> {
        return try {
            val currentPage = params.key ?: INITIAL_KEY
            val photos = homeService.getPhotos(
                currentPage,
            ).map { it.toPhotoModel() }

            LoadResult.Page(
                data = photos,
                prevKey = if (currentPage == INITIAL_KEY) null else currentPage - 1,
                nextKey = if (photos.isEmpty()) null else currentPage + 1,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val INITIAL_KEY = 1
    }
}
