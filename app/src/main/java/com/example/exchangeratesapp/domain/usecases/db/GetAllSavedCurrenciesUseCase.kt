package com.example.exchangeratesapp.domain.usecases.db

import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSavedCurrenciesUseCase @Inject constructor(private val repository: DbRepository) {
    fun getAllSavedCurrencies(): Flow<List<Currency>?> {
        return repository.getAllSavedCurrencies()
    }
}