package com.ramprasad.countriesapp.network

import kotlinx.coroutines.flow.Flow

/**
 * Created by Ramprasad on 5/14/23.
 */
interface ConnectivityStatus {

    fun observe(): Flow<Status>

    enum class Status {
        Connected, NoConnection, Loosing, Lost
    }
}