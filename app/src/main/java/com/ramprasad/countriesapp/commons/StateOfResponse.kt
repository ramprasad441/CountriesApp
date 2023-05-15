package com.ramprasad.countriesapp.commons

import com.ramprasad.countriesapp.model.CountriesItem

/**
 * Created by Ramprasad on 5/14/23.
 */
sealed interface StateOfResponse {
    class ERROR(val error: Throwable): StateOfResponse
    class LOADING(val loading: Boolean = true): StateOfResponse
    class SUCCESS(val countries: List<CountriesItem>): StateOfResponse
}