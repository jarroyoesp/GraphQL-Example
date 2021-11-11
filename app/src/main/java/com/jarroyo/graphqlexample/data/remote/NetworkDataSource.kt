package com.jarroyo.graphqlexample.data.remote

import android.accounts.NetworkErrorException
import arrow.core.Either
import com.jarroyo.graphqlexample.domain.model.Country

interface NetworkDataSource {
    suspend fun getHomeData(): Either<Exception, List<Country>?>
}

class NetworkDataSourceImpl(/*private val apiService: ApiService,*/
    private val networkSystem: NetworkSystem
) : NetworkDataSource {

    companion object {
        private val TAG = NetworkDataSourceImpl::class.java.simpleName
    }

    override suspend fun getHomeData(): Either<Exception, List<Country>?> {
        return if (networkSystem.isNetworkAvailable()) {
            /*val response =
                apiService.getHomeData(bounds.p1Lat, bounds.p1Lon, bounds.p2Lat, bounds.p2Lon)
             if (response.isSuccessful) {
                return Either.Right(response.body()?.poiList?.toDomainModel())
            } else {*/
            Either.Left(NetworkErrorException())
            //}
        } else {
            Either.Left(NetworkErrorException())
        }
    }
}