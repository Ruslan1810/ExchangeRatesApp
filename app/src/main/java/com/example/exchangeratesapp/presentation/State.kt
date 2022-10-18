package com.example.exchangeratesapp.presentation

import com.example.exchangeratesapp.domain.model.Currency

class State(
    val isError: Boolean = false,
    val isInProgress: Boolean = false,
    val result: String = "",
    var listRates: List<Currency> = listOf()
)