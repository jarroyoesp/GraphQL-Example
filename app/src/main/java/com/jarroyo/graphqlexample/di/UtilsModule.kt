package com.jarroyo.graphqlexample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(ApplicationComponent::class)
@Module
object UtilsModule {

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}