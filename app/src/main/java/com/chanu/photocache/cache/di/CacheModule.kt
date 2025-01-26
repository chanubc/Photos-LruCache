package com.chanu.photocache.cache.di

import android.content.Context
import com.chanu.photocache.cache.datasource.BitmapFetcher
import com.chanu.photocache.cache.datasource.DiskCache
import com.chanu.photocache.cache.datasource.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideMemoryCache(): MemoryCache = MemoryCache()

    @Provides
    @Singleton
    fun provideDiskCache(@ApplicationContext context: Context): DiskCache = DiskCache(context)

    @Provides
    @Singleton
    fun provideBitmapFetcher(): BitmapFetcher = BitmapFetcher()
}
