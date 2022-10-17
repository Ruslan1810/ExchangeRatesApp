package com.example.exchangeratesapp.data.network

import com.example.exchangeratesapp.data.network.model.ResponseRatesJsonApi
import com.example.exchangeratesapp.data.network.model.ResponseSymbolsJson
import com.example.exchangeratesapp.presentation.fragmentListCurrencies.FragmentListCurrencies
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("symbols")
    suspend fun getCurrencySymbols(): ResponseSymbolsJson

    @GET("latest")
    suspend fun getCurrencyRates(
        @Query("symbols") listSymbols: List<String>? = null,
        @Query("base") base: String = FragmentListCurrencies.BASE_CURRENCY
    ): ResponseRatesJsonApi

    @GET("convert")
    suspend fun getConvertedRate(
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String
    )
}