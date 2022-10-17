package com.example.exchangeratesapp.presentation.fragmentConvetorCurrency

import androidx.lifecycle.ViewModel
import com.example.exchangeratesapp.domain.usecases.CalculationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FragmentConvertorCurrencyVM @Inject constructor(
    private val calculationUseCase:CalculationUseCase
): ViewModel() {

    var result = MutableStateFlow<String?>(null)

    fun calculation(value: String, amount: Double) {
            result.value = calculationUseCase.calculation(value, amount)
    }
}
