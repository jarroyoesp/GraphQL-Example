package com.jarroyo.graphqlexample.domain.usecase

import android.accounts.NetworkErrorException
import arrow.core.Either
import com.jarroyo.graphqlexample.domain.repository.HomeRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class GetDataHomeUsecaseTest {
    private lateinit var getDataHomeUsecase: GetDataHomeUsecase

    @MockK
    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getDataHomeUsecase = GetDataHomeUsecase(
            homeRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN remote error WHEN call getHomeData THEN returns EitherLeft`() = runBlocking {
        // Given
        coEvery { homeRepository.getData() } returns Either.left(NetworkErrorException())

        // When
        val response = getDataHomeUsecase.invoke()

        // Then
        assert(response is Either.Left)
    }


    @Test
    fun `GIVEN remote success WHEN call getHomeData THEN returns EitherRight`() = runBlocking {
        // Given
        coEvery { homeRepository.getData() } returns Either.Right(emptyList())

        // When
        val response = getDataHomeUsecase.invoke()

        // Then
        assert(response is Either.Right)
    }
}