package com.example.exchangeratesapp.presentation.fragmentFavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.usecases.db.DeleteMealUseCase
import com.example.exchangeratesapp.domain.usecases.db.GetAllSavedMealsUseCase
import com.example.exchangeratesapp.domain.usecases.db.InsertFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentFavoriteVM @Inject constructor(
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val getAllSavedMealsUseCase: GetAllSavedMealsUseCase,
    private val deleteMealUseCase: DeleteMealUseCase
) : ViewModel() {

    private var favoriteCurrenciesStateFlow: StateFlow<List<Currency>?>? = null

    private fun getFavoritesCurrencies() {

        favoriteCurrenciesStateFlow = getAllSavedMealsUseCase.getAllSavedCurrencies().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf()
        )
    }

    fun deleteMeal(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMealUseCase.deleteMeal(currency)
        }
    }

    fun saveMealFavorite(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteUseCase.insertFavorite(currency)
        }
    }

    fun getFavoritesMealsLiveData(): StateFlow<List<Currency>?>? {
        return favoriteCurrenciesStateFlow
    }

    init {
        getFavoritesCurrencies()
    }
}