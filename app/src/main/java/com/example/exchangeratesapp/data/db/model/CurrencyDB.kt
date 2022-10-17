package com.example.exchangeratesapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class CurrencyDB (
    @PrimaryKey
    val name: String,
    val value: Double,
    val favorite: Boolean
)