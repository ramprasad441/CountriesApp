package com.ramprasad.countriesapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramprasad.countriesapp.adapter.CountriesListAdapter
import com.ramprasad.countriesapp.commons.StateOfResponse
import com.ramprasad.countriesapp.databinding.FragmentCountryBinding
import com.ramprasad.countriesapp.network.ConnectivityStatus
import com.ramprasad.countriesapp.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ramprasad on 5/14/23.
 */
@AndroidEntryPoint
class CountriesFragment: Fragment() {

    private val binding by lazy {
        FragmentCountryBinding.inflate(layoutInflater)
    }

    private val countriesListAdapter by lazy {
        CountriesListAdapter()
    }

    private val countryViewModel: CountryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.countryRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = countriesListAdapter
        }
        fetchCountries()

        return binding.root
    }

    private fun fetchCountries() {
        countryViewModel.countries.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateOfResponse.LOADING -> {
                    binding.countryProgressBar.visibility = View.VISIBLE
                    binding.countryRecyclerView.visibility = View.GONE
                }

                is StateOfResponse.ERROR -> {
                    binding.countryProgressBar.visibility = View.GONE
                    binding.countryRecyclerView.visibility = View.GONE

                    state.error.localizedMessage?.let {
                        showErrorMessage(it) {
                            countryViewModel.getListOfAllCountries()
                        }
                    }
                }

                is StateOfResponse.SUCCESS -> {
                    binding.countryProgressBar.visibility = View.GONE
                    binding.countryRecyclerView.visibility = View.VISIBLE
                    countriesListAdapter.setNewCountries(state.countries)
                }

            }
        }

        countryViewModel.getListOfAllCountries()
    }

    private fun showErrorMessage(message: String = "Working on the issues", retry: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error Occurred While Fetching the Countries List")
            .setPositiveButton("retry") { dialog, _ ->
                dialog.dismiss()
                retry()
            }
            .setNegativeButton("dismiss") { dialog, _ ->
                dialog.dismiss()
            }
            .setMessage(message)
            .create()
            .show()
    }

}