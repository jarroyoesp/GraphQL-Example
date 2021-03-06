package com.jarroyo.graphqlexample.data.remote

import android.accounts.NetworkErrorException
import android.util.Log
import arrow.core.Either
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.jarroyo.GetCharactersQuery
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import com.jarroyo.graphqlexample.domain.model.toUIModel

interface NetworkDataSource {
    suspend fun getHomeData(): Either<Exception, List<CharacterUIModel>?>
}

class NetworkDataSourceImpl(private val apolloAPI: ApolloAPI,
    private val networkSystem: NetworkSystem
) : NetworkDataSource {

    companion object {
        private val TAG = NetworkDataSourceImpl::class.java.simpleName
    }

    override suspend fun getHomeData(): Either<Exception, List<CharacterUIModel>?> {
        return if (networkSystem.isNetworkAvailable()) {
            apolloAPI.getHomeData()
        } else {
            Either.Left(NetworkErrorException())
        }
    }
}