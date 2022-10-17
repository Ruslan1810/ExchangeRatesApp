package com.example.exchangeratesapp.presentation.fragmentListCurrencies

import androidx.lifecycle.*
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.usecases.SortAlphabetDeskUseCase
import com.example.exchangeratesapp.domain.usecases.SortByValueAscUseCase
import com.example.exchangeratesapp.domain.usecases.SortByValueDeskUseCase
import com.example.exchangeratesapp.domain.usecases.api.GetCurrenciesRatesUseCase
import com.example.exchangeratesapp.domain.usecases.db.InsertFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentListCurrenciesVM @Inject constructor(
    private val getCurrenciesRatesUseCase: GetCurrenciesRatesUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val sortAlphabetDeskUseCase: SortAlphabetDeskUseCase,
    private val sortByValueAscUseCase: SortByValueAscUseCase,
    private val sortByValueDeskUseCase: SortByValueDeskUseCase
) : ViewModel() {

    private var listCurrenciesLiveData = MutableLiveData<List<Currency>>()
    private lateinit var listRates: List<Currency>
    fun getListCurrencies(typOfSorting: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(1000)
                listRates = getCurrenciesRatesUseCase.getCurrencyRates()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            when (typOfSorting) {
                "alphabetically-desc" -> {
                    listCurrenciesLiveData.postValue(
                        sortAlphabetDeskUseCase.sortAlphabeticallyDesk(listRates)
                    )
                }
                "ByValue-ask" -> {
                    listCurrenciesLiveData.postValue(
                        sortByValueAscUseCase.sortByValueAsk(listRates)
                    )
                }
                "ByValue-desk" -> {
                    listCurrenciesLiveData.postValue(
                        sortByValueDeskUseCase.sortByValueDesk(listRates)
                    )
                }
                else -> listCurrenciesLiveData.postValue(listRates)
            }
        }
    }

    fun saveCurrencyFavorite(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteUseCase.insertFavorite(currency)
        }
    }

    fun getListCurrenciesLiveData(): LiveData<List<Currency>> {
        return listCurrenciesLiveData
    }

    init {
        getListCurrencies()
    }
}