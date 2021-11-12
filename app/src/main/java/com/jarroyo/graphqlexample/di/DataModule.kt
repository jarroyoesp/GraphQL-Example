package com.jarroyo.graphqlexample.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.jarroyo.graphqlexample.data.remote.NetworkDataSource
import com.jarroyo.graphqlexample.data.remote.NetworkDataSourceImpl
import com.jarroyo.graphqlexample.data.remote.NetworkSystem
import com.jarroyo.graphqlexample.data.remote.NetworkSystemImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideNetworkSystem(@ApplicationContext context: Context) =
        NetworkSystemImpl(context) as NetworkSystem

    @Provides
    fun provideApolloClient(): ApolloClient = ApolloClient.builder()
        .serverUrl("https://countries-274616.ew.r.appspot.com/")
        .build()

    @Provides
    fun provideRemoteNetworkDataSource(apolloClient: ApolloClient, networkSystem: NetworkSystem): NetworkDataSource {
        return NetworkDataSourceImpl(apolloClient,  networkSystem)
    }

}