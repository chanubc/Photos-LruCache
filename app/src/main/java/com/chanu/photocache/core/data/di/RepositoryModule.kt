package com.chanu.photocache.core.data.di

import com.chanu.photocache.core.data.repository.HomeRepository
import com.chanu.photocache.core.data.repositoryimpl.DefaultHomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsHomeRepository(
        repositoryImpl: DefaultHomeRepository,
    ): HomeRepository
}
