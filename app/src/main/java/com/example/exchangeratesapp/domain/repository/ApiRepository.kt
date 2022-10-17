package com.example.exchangeratesapp.domain.repository

import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.model.ResponseRatesJson

interface ApiRepository {

    suspend fun getCurrencyRates(): ResponseRatesJson

    suspend fun getRatesListFromJson(responseRatesJson: ResponseRatesJson): List<Currency>

}