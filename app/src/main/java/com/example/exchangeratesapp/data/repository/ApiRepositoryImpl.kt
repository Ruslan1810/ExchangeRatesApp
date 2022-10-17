package com.example.exchangeratesapp.data.repository

import com.example.exchangeratesapp.data.network.ApiService
import com.example.exchangeratesapp.data.mapper.Mapper
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.model.ResponseRatesJson
import com.example.exchangeratesapp.domain.repository.ApiRepository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiservice: ApiService,
    private val mapper: Mapper
) : ApiRepository {

    override suspend fun getCurrencyRates(): ResponseRatesJson {
        return mapper.responseRatesJsonApiToResponseRatesJson(
            apiservice.getCurrencyRates()
        )
    }

    override suspend fun getRatesListFromJson(responseRatesJson: ResponseRatesJson): List<Currency> {
        return mapper.getRatesListFromJson(responseRatesJson)
    }
}