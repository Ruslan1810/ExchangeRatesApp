package com.example.exchangeratesapp.domain.repository

import com.example.exchangeratesapp.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    suspend fun insertFavorite(currency: Currency)

    suspend fun updateFavorite(currency: Currency)

    fun getAllSavedCurrencies(): Flow<List<Currency>?>

    suspend fun getCurrencyByName(id:String): Currency

    suspend fun deleteCurrencyByName(id:String)

    suspend fun deleteCurrency(currency: Currency)
}