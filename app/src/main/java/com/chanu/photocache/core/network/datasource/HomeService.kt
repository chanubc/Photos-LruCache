package com.chanu.photocache.core.network.datasource

import com.chanu.photocache.core.network.dto.ResponsePhotoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 30,
    ): List<ResponsePhotoDto>

    @GET("id/{id}/info")
    suspend fun getPhotoInfo(
        @Path("id") id: Int,
    ): ResponsePhotoDto
}
