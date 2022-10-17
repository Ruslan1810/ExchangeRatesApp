package com.example.exchangeratesapp.di

import androidx.lifecycle.ViewModel
import com.example.exchangeratesapp.presentation.fragmentConvetorCurrency.FragmentConvertorCurrencyVM
import com.example.exchangeratesapp.presentation.fragmentFavorite.FragmentFavoriteVM
import com.example.exchangeratesapp.presentation.fragmentListCurrencies.FragmentListCurrenciesVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FragmentConvertorCurrencyVM::class)
    fun bindFragmentConvertorCurrencyVM(viewModel: FragmentConvertorCurrencyVM): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(FragmentFavoriteVM::class)
    fun bindFragmentFavoriteVM(viewModel: FragmentFavoriteVM): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(FragmentListCurrenciesVM::class)
    fun bindFragmentListCurrenciesVM(viewModel: FragmentListCurrenciesVM): ViewModel
}