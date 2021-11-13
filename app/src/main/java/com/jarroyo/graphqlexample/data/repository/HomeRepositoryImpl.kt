package com.jarroyo.graphqlexample.data.repository

import android.util.Log
import arrow.core.Either
import com.jarroyo.graphqlexample.data.remote.NetworkDataSource
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import com.jarroyo.graphqlexample.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {

    companion object {
        private val TAG = HomeRepositoryImpl::class.java.simpleName
        private const val DELAY = 10000L
    }

    override suspend fun getData(): Either<Exception, List<CharacterUIModel>?> {
        return withContext(ioDispatcher) {
            networkDataSource.getHomeData()
        }
    }

    override fun getDataFlow(): Flow<Either<Exception, List<CharacterUIModel>?>> = flow {
        while (true) {
            Log.d(TAG, "getDataFlow")
            emit(getData())
            delay(DELAY)
        }
    }
}