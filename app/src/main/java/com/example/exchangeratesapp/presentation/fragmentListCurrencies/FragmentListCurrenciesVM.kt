package com.example.exchangeratesapp.presentation.fragmentListCurrencies

import androidx.lifecycle.*
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.usecases.SortAlphabetDeskUseCase
import com.example.exchangeratesapp.domain.usecases.SortByValueAscUseCase
import com.example.exchangeratesapp.domain.usecases.SortByValueDeskUseCase
import com.example.exchangeratesapp.domain.usecases.api.GetCurrenciesRatesUseCase
import com.example.exchangeratesapp.domain.usecases.db.InsertFavoriteUseCase
import com.example.exchangeratesapp.presentation.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentListCurrenciesVM @Inject constructor(
    private val getCurrenciesRatesUseCase: GetCurrenciesRatesUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val sortAlphabetDeskUseCase: SortAlphabetDeskUseCase,
    private val sortByValueAscUseCase: SortByValueAscUseCase,
    private val sortByValueDeskUseCase: SortByValueDeskUseCase
) : ViewModel() {

    private var listRates: List<Currency> = listOf()
    private val _state = MutableStateFlow<State?>(value = null)
    val state: StateFlow<State?>
        get() = _state

    fun getListCurrencies(typeOfSorting: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = State(isInProgress = true)
            delay(500)
            try {
                listRates = getCurrenciesRatesUseCase.getCurrencyRates()
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = State(isError = true)
            }
            when (typeOfSorting) {
                "alphabetically-desc" -> {
                    _state.value = State(
                        listRates =
                        sortAlphabetDeskUseCase.sortAlphabeticallyDesk(listRates)
                    )
                }
                "ByValue-ask" -> {
                    _state.value = State(
                        listRates =
                        sortByValueAscUseCase.sortByValueAsk(listRates)
                    )
                }
                "ByValue-desk" -> {
                    _state.value = State(
                        listRates =
                        sortByValueDeskUseCase.sortByValueDesk(listRates)
                    )
                }
                else -> _state.value = State(listRates = listRates)
            }
        }
    }

    fun saveCurrencyFavorite(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteUseCase.insertFavorite(currency)
        }
    }

    init {
        getListCurrencies()
    }
}
