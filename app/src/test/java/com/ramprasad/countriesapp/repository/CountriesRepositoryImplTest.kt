package com.ramprasad.countriesapp.repository

import com.ramprasad.countriesapp.commons.StateOfResponse
import com.ramprasad.countriesapp.model.CountriesItem
import com.ramprasad.countriesapp.network.RetrofitClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mockito
import retrofit2.Response

/**
 * Created by Ramprasad on 5/14/23.
 */
@RunWith(BlockJUnit4ClassRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CountriesRepositoryImplTest {

    private lateinit var mockRetrofitClient: RetrofitClient


    private lateinit var repo: CountriesRepositoryImpl

    @Before
    fun before() {
        mockRetrofitClient = Mockito.mock(RetrofitClient::class.java)
        repo = CountriesRepositoryImpl(mockRetrofitClient)

    }


    @Test
    fun testEmptyListData() = runTest {
        Mockito.`when`(mockRetrofitClient.getAllListOfCountries()).thenAnswer {
            Response.success(emptyList<CountriesItem>())
        }
        val res: List<StateOfResponse> = repo.getAllCountries().toList()

        assert(true)
        Assert.assertEquals(0, (res[1] as StateOfResponse.SUCCESS).countries.count())
    }

    @Test
    fun testTwoItemsListData() = runTest {
        Mockito.`when`(mockRetrofitClient.getAllListOfCountries()).thenAnswer {
            Response.success(createCountry())
        }
        val res: List<StateOfResponse> = repo.getAllCountries().toList()

        assert(true)
        Assert.assertEquals(2, (res[1] as StateOfResponse.SUCCESS).countries.count())
    }

    @Test
    fun testErrorData() = runTest {
        Mockito.`when`(mockRetrofitClient.getAllListOfCountries()).thenAnswer {
            StateOfResponse.ERROR(Throwable())
        }
        val res: List<StateOfResponse> = repo.getAllCountries().toList()

        assert(true)
        assert((res[1] is StateOfResponse.ERROR))
    }

     private fun createCountry(): MutableList<CountriesItem> {
        return mutableListOf<CountriesItem>().apply {
            add(CountriesItem("El Aaiún", "EH", name = "Western Sahara"))
            add(CountriesItem("Mata-Utu", "WF", name = "Wallis and Futuna"))
        }

    }
}