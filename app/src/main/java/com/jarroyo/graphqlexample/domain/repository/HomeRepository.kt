package com.jarroyo.graphqlexample.domain.repository

import arrow.core.Either
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getData(): Either<Exception, List<CharacterUIModel>?>
    fun getDataFlow(): Flow<Either<Exception, List<CharacterUIModel>?>>
}