package com.example.exchangeratesapp.domain.usecases.api

import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.model.ResponseRatesJson
import com.example.exchangeratesapp.domain.repository.ApiRepository
import javax.inject.Inject

class GetCurrenciesRatesUseCase @Inject constructor(private val repository: ApiRepository) {
    suspend fun getCurrencyRates(): List<Currency> {
        return repository.getRatesListFromJson(repository.getCurrencyRates())
    }
}