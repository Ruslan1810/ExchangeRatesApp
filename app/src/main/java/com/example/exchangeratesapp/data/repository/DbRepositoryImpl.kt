package com.example.exchangeratesapp.data.repository

import com.example.exchangeratesapp.data.db.CurrencyDao
import com.example.exchangeratesapp.data.mapper.Mapper
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DbRepositoryImpl @Inject constructor(
    private val mapper: Mapper,
    private val currencyDao: CurrencyDao
) : DbRepository {

    override suspend fun insertFavorite(currency: Currency) {
        currencyDao.insertFavorite(mapper.currencyToCurrencyDB(currency))
    }

    override suspend fun updateFavorite(currency: Currency) {
        currencyDao.updateFavorite(mapper.currencyToCurrencyDB(currency))
    }

    override fun getAllSavedCurrencies(): Flow<List<Currency>?> {
        var currencyFlow = MutableStateFlow<List<Currency>?>(null)
        return currencyDao.getAllSavedCurrencies().map {
            val list = arrayListOf<Currency>()
            for (i in it.indices) {
                list.add(
                    Currency(
                        name = it[i].name,
                        value = it[i].value,
                        favorite = it[i].favorite
                    )
                )
            }
            currencyFlow.value = list
            list
        }
    }

    override suspend fun getCurrencyByName(name: String): Currency {
        return mapper.currencyDBToCurrency(currencyDao.getCurrencyByName(name))
    }

    override suspend fun deleteCurrencyByName(name: String) {
        currencyDao.deleteCurrencyByName(name)
    }

    override suspend fun deleteCurrency(currency: Currency) {
        currencyDao.deleteCurrency(mapper.currencyToCurrencyDB(currency))
    }


}