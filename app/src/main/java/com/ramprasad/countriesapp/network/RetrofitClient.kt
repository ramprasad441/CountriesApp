package com.ramprasad.countriesapp.network


import com.ramprasad.countriesapp.model.CountriesItem
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Ramprasad on 5/14/23.
 */

interface RetrofitClient {

    @GET(COUNTRIES_LIST)
    suspend fun getAllListOfCountries(): Response<List<CountriesItem>>


    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/"
        private const val COUNTRIES_LIST = "countries.json"
    }

}