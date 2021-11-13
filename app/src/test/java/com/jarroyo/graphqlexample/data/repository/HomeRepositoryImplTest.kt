package com.jarroyo.graphqlexample.data.repository

import android.accounts.NetworkErrorException
import arrow.core.Either
import com.jarroyo.graphqlexample.data.remote.NetworkDataSource
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeRepositoryImplTest {

    @MockK
    private lateinit var remoteNetworkDataSource: NetworkDataSource

    private lateinit var homeRepositoryImpl: HomeRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        homeRepositoryImpl = HomeRepositoryImpl(
            remoteNetworkDataSource,
            Dispatchers.Unconfined
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN location success && remote error WHEN call getHomeData THEN returns EitherLeft`() = runBlocking {
        // Given
        coEvery { remoteNetworkDataSource.getHomeData() } returns Either.left(
            NetworkErrorException()
        )

        // When
        val response = homeRepositoryImpl.getData()

        // Then
        assert(response is Either.Left)
    }

    @Test
    fun `GIVEN location success && remote success WHEN call getHomeData THEN returns EitherRight`() = runBlocking {
        // Given
        coEvery { remoteNetworkDataSource.getHomeData() } returns Either.Right(emptyList())

        // When
        val response = homeRepositoryImpl.getData()

        // Then
        assert(response is Either.Right)
    }
}