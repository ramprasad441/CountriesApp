package com.ramprasad.countriesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ramprasad.countriesapp.databinding.CountriesListItemBinding
import com.ramprasad.countriesapp.model.CountriesItem

/**
 * Created by Ramprasad on 5/14/23.
 */

class CountriesListAdapter(private val countriesListData: MutableList<CountriesItem> = mutableListOf()) :
    RecyclerView.Adapter<CountriesListAdapter.CountriesViewHolder>() {


    fun setNewCountries(newDataSet: List<CountriesItem>) {
        val list = countriesListData
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(CountriesDiffCallBack(list, newDataSet))
        countriesListData.addAll(newDataSet)
        diffResult.dispatchUpdatesTo(this)

    }

    class CountriesDiffCallBack(
        private var oldCountryList: List<CountriesItem>,
        private var newCountryList: List<CountriesItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldCountryList.size
        }

        override fun getNewListSize(): Int {
            return newCountryList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCountryList[oldItemPosition].capital === newCountryList[newItemPosition].capital

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCountryList[oldItemPosition] == newCountryList[newItemPosition]
        }

    }


    class CountriesViewHolder(
        private val countriesListItemBinding: CountriesListItemBinding
    ) : RecyclerView.ViewHolder(countriesListItemBinding.root) {
        fun bind(countries: CountriesItem) {
            countriesListItemBinding.countryCode.text = countries.code
            countriesListItemBinding.countryName.text = String.format(countries.name + ",")
            countriesListItemBinding.region.text = countries.region
            countriesListItemBinding.countryCapital.text = countries.capital
            countriesListItemBinding.countryCardView.setOnClickListener {
                Toast.makeText(
                    countriesListItemBinding.root.context,
                    "Clicked on the Country: " + " " + countries.code.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder =
        CountriesViewHolder(
            CountriesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(countriesViewHolder: CountriesViewHolder, position: Int) {
        countriesViewHolder.bind(countriesListData[position])
    }

    override fun getItemCount(): Int = countriesListData.size


}