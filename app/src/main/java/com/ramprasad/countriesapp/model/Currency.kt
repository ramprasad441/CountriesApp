package com.ramprasad.countriesapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 5/14/23.
 */
data class Currency(
    @SerializedName("code") var code: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null
)