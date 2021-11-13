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

class NetworkDataSourceImpl(private val apolloClient: ApolloClient,
    private val networkSystem: NetworkSystem
) : NetworkDataSource {

    companion object {
        private val TAG = NetworkDataSourceImpl::class.java.simpleName
    }

    override suspend fun getHomeData(): Either<Exception, List<CharacterUIModel>?> {
        return if (networkSystem.isNetworkAvailable()) {
            try {
                val response = apolloClient.query(GetCharactersQuery()).toDeferred().await()
                Log.d(TAG, "$response")
                Either.Right(response.data?.characters?.toUIModel())
            } catch (e: ApolloException) {
                Either.Left(e)
            }
        } else {
            Either.Left(NetworkErrorException())
        }
    }
}