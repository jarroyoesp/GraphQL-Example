package com.jarroyo.graphqlexample.domain.usecase

import arrow.core.Either
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import com.jarroyo.graphqlexample.domain.repository.HomeRepository
import javax.inject.Inject

class GetDataHomeUsecase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke(): Either<Exception, List<CharacterUIModel>?> {
        return repository.getData()
    }
}