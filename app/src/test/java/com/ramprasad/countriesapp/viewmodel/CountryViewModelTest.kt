package com.ramprasad.countriesapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.ramprasad.countriesapp.commons.StateOfResponse
import com.ramprasad.countriesapp.repository.CountriesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

/**
 * Created by Ramprasad on 5/14/23.
 */
@ExperimentalCoroutinesApi
class CountriesViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockRepository = mockk<CountriesRepository>(relaxed = true)

    private lateinit var targetTest: CountryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        targetTest = CountryViewModel(mockRepository, testDispatcher)
    }

    @After
    fun shutdown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `getting countries when the server retrieves a list of countries when it returns error`() {
        every { mockRepository.getAllCountries() } returns flowOf(
            StateOfResponse.ERROR(Throwable("Error"))
        )
        val stateList = mutableListOf<StateOfResponse>()
        targetTest.countries.observeForever {
            stateList.add(it)
        }

        targetTest.getListOfAllCountries()

        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(StateOfResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(StateOfResponse.ERROR::class.java)

        assertThat((stateList[1] as StateOfResponse.ERROR).error.localizedMessage).isEqualTo("Error")
    }

    @Test
    fun `getting the countries when the server retrieves a list of countries when it returns loading`() {
        every { mockRepository.getAllCountries() } returns flowOf(
            StateOfResponse.LOADING()
        )
        val stateList = mutableListOf<StateOfResponse>()
        targetTest.countries.observeForever {
            stateList.add(it)
        }

        targetTest.getListOfAllCountries()

        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(StateOfResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(StateOfResponse.LOADING::class.java)

        assertThat((stateList[1] as StateOfResponse.LOADING).loading).isTrue()
    }

    @Test
    fun `getting the countries when the server retrieves the list of countries when it returns success`() {
        every { mockRepository.getAllCountries() } returns flowOf(
            StateOfResponse.SUCCESS(listOf(mockk()))
        )
        val stateList = mutableListOf<StateOfResponse>()
        targetTest.countries.observeForever {
            stateList.add(it)
        }

        targetTest.getListOfAllCountries()

        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(StateOfResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(StateOfResponse.SUCCESS::class.java)

        assertThat((stateList[1] as StateOfResponse.SUCCESS).countries).hasSize(1)
    }
}