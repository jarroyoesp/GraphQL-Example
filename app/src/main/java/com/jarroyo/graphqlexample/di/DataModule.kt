package com.jarroyo.graphqlexample.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.jarroyo.graphqlexample.data.remote.*
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
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()

    @Provides
    fun provideApolloAPI(apolloClient: ApolloClient): ApolloAPI = ApolloAPIImpl(apolloClient)

    @Provides
    fun provideRemoteNetworkDataSource(apolloAPI: ApolloAPI, networkSystem: NetworkSystem): NetworkDataSource {
        return NetworkDataSourceImpl(apolloAPI,  networkSystem)
    }

}