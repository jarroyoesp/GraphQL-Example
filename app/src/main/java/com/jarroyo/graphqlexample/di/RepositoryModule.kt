package com.jarroyo.graphqlexample.di

import com.jarroyo.graphqlexample.data.remote.NetworkDataSource
import com.jarroyo.graphqlexample.data.repository.HomeRepositoryImpl
import com.jarroyo.graphqlexample.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideHomeRepository(
        networkDataSource: NetworkDataSource,
        ioDispatcher: CoroutineDispatcher
    ): HomeRepository {
        return HomeRepositoryImpl(networkDataSource, ioDispatcher)
    }
}