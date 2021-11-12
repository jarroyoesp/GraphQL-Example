package com.jarroyo.graphqlexample.data.remote

import android.accounts.NetworkErrorException
import arrow.core.Either
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.jarroyo.CountryListQuery
import com.jarroyo.graphqlexample.domain.model.Country

interface NetworkDataSource {
    suspend fun getHomeData(): Either<Exception, List<Country>?>
}

class NetworkDataSourceImpl(private val apolloClient: ApolloClient,
    private val networkSystem: NetworkSystem
) : NetworkDataSource {

    companion object {
        private val TAG = NetworkDataSourceImpl::class.java.simpleName
    }

    override suspend fun getHomeData(): Either<Exception, List<Country>?> {
        return if (networkSystem.isNetworkAvailable()) {
            try {
                apolloClient.query(CountryListQuery()).toDeferred().await()
                Either.Right(listOf(Country("Name", "capital")))
            } catch (e: ApolloException) {
                Either.Left(e)
            }
        } else {
            Either.Left(NetworkErrorException())
        }
    }
}