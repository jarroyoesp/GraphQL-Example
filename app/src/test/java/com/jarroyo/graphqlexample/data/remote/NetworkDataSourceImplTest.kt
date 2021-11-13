package com.jarroyo.graphqlexample.data.remote

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class NetworkDataSourceImplTest {
    @MockK
    private lateinit var apolloAPI: ApolloAPI

    @MockK
    private lateinit var networkSystem: NetworkSystem

    private lateinit var networkDataSource: NetworkDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        networkDataSource = NetworkDataSourceImpl(
            apolloAPI,
            networkSystem
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN remote error WHEN call getHomeData THEN returns EitherLeft`() = runBlocking {
        // Given
        coEvery { apolloAPI.getHomeData()} returns Either.left(Exception(""))
        every { networkSystem.isNetworkAvailable() } returns true

        // When
        val response = networkDataSource.getHomeData()

        // Then
        assert(response is Either.Left)
    }

  @Test
    fun `GIVEN remote success WHEN call getHomeData THEN returns EitherRight`() = runBlocking {
        // Given
        coEvery { apolloAPI.getHomeData() } returns Either.right(emptyList())
        every { networkSystem.isNetworkAvailable() } returns true

        // When
        val response = networkDataSource.getHomeData()

        // Then
        assert(response is Either.Right)
    }

    @Test
    fun `GIVEN No Internet WHEN call getHomeData THEN returns EitherLeft`() = runBlocking {
        // Given
        every { networkSystem.isNetworkAvailable() } returns false

        // When
        val response = networkDataSource.getHomeData()

        // Then
        assert(response is Either.Left)
    }
}