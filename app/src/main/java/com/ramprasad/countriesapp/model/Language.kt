package com.ramprasad.countriesapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 5/14/23.
 */
data class Language(
    @SerializedName("code") var code: String? = null,
    @SerializedName("iso639_2") var iso639_2: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("nativeName") var nativeName: String? = null,
)