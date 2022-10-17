package com.example.exchangeratesapp.presentation.fragmentFavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.usecases.db.DeleteCurrencyUseCase
import com.example.exchangeratesapp.domain.usecases.db.GetAllSavedCurrenciesUseCase
import com.example.exchangeratesapp.domain.usecases.db.InsertFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentFavoriteVM @Inject constructor(
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val getAllSavedCurrenciesUseCase: GetAllSavedCurrenciesUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase
) : ViewModel() {

    private var favoriteCurrenciesStateFlow: StateFlow<List<Currency>?>? = null

    private fun getFavoritesCurrencies() {

        favoriteCurrenciesStateFlow = getAllSavedCurrenciesUseCase.getAllSavedCurrencies()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = listOf()
            )
    }

    fun deleteCurrency(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCurrencyUseCase.deleteCurrency(currency)
        }
    }

    fun saveCurrencyFavorite(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteUseCase.insertFavorite(currency)
        }
    }

    fun getFavoriteCurrenciesStateFlow(): StateFlow<List<Currency>?>? {
        return favoriteCurrenciesStateFlow
    }

    init {
        getFavoritesCurrencies()
    }
}