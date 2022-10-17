package com.example.exchangeratesapp.presentation.fragmentListCurrencies

import androidx.lifecycle.*
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.usecases.SortAlphabetDeskUseCase
import com.example.exchangeratesapp.domain.usecases.SortByValueAscUseCase
import com.example.exchangeratesapp.domain.usecases.SortByValueDeskUseCase
import com.example.exchangeratesapp.domain.usecases.api.GetCurrenciesRatesUseCase
import com.example.exchangeratesapp.domain.usecases.db.InsertFavoriteUseCase
import kotlinx.coroutines.Dispatchers
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

    private var listCurrenciesStateFlow = MutableStateFlow<List<Currency>>(listOf())
    private lateinit var listRates: List<Currency>
    fun getListCurrencies(typOfSorting: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                listRates = getCurrenciesRatesUseCase.getCurrencyRates()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            when (typOfSorting) {
                "alphabetically-desc" -> {
                    listCurrenciesStateFlow.value =
                        sortAlphabetDeskUseCase.sortAlphabeticallyDesk(listRates)
                }
                "ByValue-ask" -> {
                    listCurrenciesStateFlow.value =
                        sortByValueAscUseCase.sortByValueAsk(listRates)
                }
                "ByValue-desk" -> {
                    listCurrenciesStateFlow.value =
                        sortByValueDeskUseCase.sortByValueDesk(listRates)
                }
                else -> listCurrenciesStateFlow.value = listRates
            }
        }
    }

    fun saveCurrencyFavorite(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteUseCase.insertFavorite(currency)
        }
    }

    fun getListCurrenciesStateFlow(): StateFlow<List<Currency>> {
        return listCurrenciesStateFlow
    }

    init {
        getListCurrencies()
    }
}
