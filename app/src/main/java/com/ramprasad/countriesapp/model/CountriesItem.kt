package com.ramprasad.countriesapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 5/14/23.
 */
data class CountriesItem(
    @SerializedName("capital") var capital: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("currency") var currency: Currency? = Currency(),
    @SerializedName("flag") var flag: String? = null,
    @SerializedName("language") var language: Language? = Language(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("region") var region: String? = null

) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }
        other as CountriesItem
        if (capital != other.capital) {
            return false
        }
        if (code != other.code) {
            return false
        }
        if (currency != other.currency) {
            return false
        }
        if (flag != other.flag) {
            return false
        }
        if (language != other.language) {
            return false
        }
        if (name != other.name) {
            return false
        }
        if (region != other.region) {
            return false
        }
        return true
    }
}