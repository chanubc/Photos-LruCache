package com.chanu.photocache.core.network.di

import com.chanu.photocache.core.network.datasource.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun providePostingService(
        retrofit: Retrofit,
    ): HomeService = retrofit.create(HomeService::class.java)
}
