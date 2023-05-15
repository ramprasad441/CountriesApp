package com.ramprasad.countriesapp.repository

import android.util.Log
import com.ramprasad.countriesapp.commons.EmptyResponseMessage
import com.ramprasad.countriesapp.commons.FailureResponse
import com.ramprasad.countriesapp.commons.StateOfResponse
import com.ramprasad.countriesapp.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ramprasad on 5/14/23.
 */

class CountriesRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient
) : CountriesRepository {


    override fun getAllCountries(): Flow<StateOfResponse> =
        flow {
            emit(StateOfResponse.LOADING())

            try {

                val response = retrofitClient.getAllListOfCountries()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(StateOfResponse.SUCCESS(it))
                        Log.d("Repository Response", it.toString())
                    } ?: throw EmptyResponseMessage()
                }
                else {
                    throw FailureResponse()
                }
            } catch (exception: Exception) {
                emit(StateOfResponse.ERROR(exception))
            }
        }
}


interface CountriesRepository {
    fun getAllCountries(): Flow<StateOfResponse>
}