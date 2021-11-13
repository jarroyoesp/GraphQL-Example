package com.jarroyo.graphqlexample.presentation.main.viewmodel

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import com.jarroyo.graphqlexample.domain.model.CharacterUIModel
import com.jarroyo.graphqlexample.domain.usecase.GetDataHomeUsecase
import com.jarroyo.graphqlexample.presentation.main.model.UIHomeState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @MockK
    private lateinit var getDataHomeUsecase: GetDataHomeUsecase

    @MockK
    private lateinit var homeObserver: Observer<UIHomeState>

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `When init & success THEN emit ShowSections event`() = runBlockingTest {
        // Given
        coEvery { getDataHomeUsecase.invoke() } returns Either.right(listOf(mockItem()))
        every { homeObserver.onChanged(any()) } answers {}
        viewModel = spyk(
            HomeViewModel(
                getDataHomeUsecase
            )
        )

        // When
        viewModel.homeState.observeForever(homeObserver)

        // Then
        coVerify { homeObserver.onChanged(UIHomeState.ShowData(listOf(mockItem()))) }
    }

    @Test
    fun `When init & error THEN emit Error event`() = runBlockingTest {
        // Given
        val exception = NetworkErrorException("")
        coEvery { getDataHomeUsecase.invoke() } returns Either.left(exception)
        every { homeObserver.onChanged(any()) } answers {}
        viewModel = spyk(
            HomeViewModel(
                getDataHomeUsecase
            )
        )

        // When
        viewModel.homeState.observeForever(homeObserver)

        // Then
        coVerify { homeObserver.onChanged(UIHomeState.Error(exception)) }
    }

    private fun mockItem(): CharacterUIModel {
        return CharacterUIModel(
            id = "1",
            name = "name",
            image = "image"
        )
    }

}