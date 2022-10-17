package com.example.exchangeratesapp.domain.usecases

import javax.inject.Inject

class CalculationUseCase @Inject constructor(){

    fun calculation(value: String, amount: Double): String{
        val currencyValue: Double = value.toDouble()
        val total = amount * currencyValue
        return String.format("%.1f", total)
    }
}