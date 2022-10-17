package com.example.exchangeratesapp.data.db

import androidx.room.*
import com.example.exchangeratesapp.data.db.model.CurrencyDB
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(currency: CurrencyDB)

    @Update
    suspend fun updateFavorite(currency: CurrencyDB)

    @Query("SELECT * FROM currency_table")
    fun getAllSavedCurrencies(): Flow<List<CurrencyDB>>

    @Query("SELECT * FROM currency_table WHERE  name =:name")
    fun getCurrencyByName(name:String): CurrencyDB

    @Query("DELETE FROM currency_table WHERE name =:name")
    fun deleteCurrencyByName(name:String)

    @Delete
    fun deleteCurrency(currency: CurrencyDB)

}