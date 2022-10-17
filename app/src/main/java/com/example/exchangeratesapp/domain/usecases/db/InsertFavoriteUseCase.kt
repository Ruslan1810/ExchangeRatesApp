package com.example.exchangeratesapp.domain.usecases.db

import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.repository.DbRepository
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(private val repository: DbRepository) {
    suspend fun insertFavorite(currency: Currency) {
        return repository.insertFavorite(currency)
    }
}