package com.ramprasad.countriesapp.di

import com.ramprasad.countriesapp.network.RetrofitClient
import com.ramprasad.countriesapp.repository.CountriesRepository
import com.ramprasad.countriesapp.repository.CountriesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Ramprasad on 5/14/23.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppNetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideNetworkService(okHttpClient: OkHttpClient): RetrofitClient =
        Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitClient::class.java)

    @Provides
    @Singleton
    fun providesToCountryRepository(retrofitClient: RetrofitClient): CountriesRepository =
        CountriesRepositoryImpl(retrofitClient)

    @Provides
    @Singleton
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}