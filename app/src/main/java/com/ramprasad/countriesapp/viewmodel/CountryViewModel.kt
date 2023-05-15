package com.ramprasad.countriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramprasad.countriesapp.commons.StateOfResponse
import com.ramprasad.countriesapp.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ramprasad on 5/14/23.
 */
@HiltViewModel
class CountryViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _countries: MutableLiveData<StateOfResponse> =
        MutableLiveData(StateOfResponse.LOADING())
    val countries: LiveData<StateOfResponse> get() = _countries

    fun getListOfAllCountries() {
        viewModelSafeScope.launch(ioDispatcher) {
            countriesRepository.getAllCountries().collect {
                _countries.postValue(it)

            }
        }
    }

}