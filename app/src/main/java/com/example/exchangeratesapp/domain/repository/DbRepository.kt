package com.example.exchangeratesapp.domain.repository

import com.example.exchangeratesapp.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    suspend fun insertFavorite(meal: Currency)

    suspend fun updateFavorite(meal: Currency)

    fun getAllSavedCurrencies(): Flow<List<Currency>?>

    suspend fun getCurrencyByName(id:String): Currency

    suspend fun deleteCurrencyByName(id:String)

    suspend fun deleteCurrency(meal: Currency)
}