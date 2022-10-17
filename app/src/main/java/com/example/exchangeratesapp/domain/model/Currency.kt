package com.example.exchangeratesapp.domain.model

data class Currency(
    val name: String,
    val value: Double,
    var favorite: Boolean
)