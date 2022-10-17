package com.example.exchangeratesapp.domain.usecases.db

import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.repository.DbRepository
import javax.inject.Inject


class DeleteCurrencyUseCase @Inject constructor(private val repository: DbRepository) {
    suspend fun deleteCurrency(currency: Currency){
        return repository.deleteCurrency(currency)
    }
}