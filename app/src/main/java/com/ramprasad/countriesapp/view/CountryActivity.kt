package com.ramprasad.countriesapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramprasad.countriesapp.databinding.ActivityCountryBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ramprasad on 5/14/23.
 */
@AndroidEntryPoint
class CountryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCountryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }


}