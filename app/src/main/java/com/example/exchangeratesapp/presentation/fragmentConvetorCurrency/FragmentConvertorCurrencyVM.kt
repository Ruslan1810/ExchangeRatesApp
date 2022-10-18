package com.example.exchangeratesapp.presentation.fragmentConvetorCurrency

import androidx.lifecycle.ViewModel
import com.example.exchangeratesapp.domain.usecases.CalculationUseCase
import com.example.exchangeratesapp.presentation.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FragmentConvertorCurrencyVM @Inject constructor(
    private val calculationUseCase: CalculationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State?>(value = null)
    val state: StateFlow<State?>
        get() = _state

    fun calculation(value: String, amount: Double) {
        try {
            val resultCalc = calculationUseCase.calculation(value, amount)
            _state.value = State(result = resultCalc)

        } catch (e: Exception) {
            _state.value = State(isError = true)
        }
    }
}
