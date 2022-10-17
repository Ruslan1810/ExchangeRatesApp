package com.example.exchangeratesapp.di

import android.app.Application
import com.example.exchangeratesapp.presentation.ViewModelFactory
import com.example.exchangeratesapp.presentation.fragmentConvetorCurrency.FragmentConvertorCurrency
import com.example.exchangeratesapp.presentation.fragmentFavorite.FragmentFavorite
import com.example.exchangeratesapp.presentation.fragmentFavorite.FragmentFavoriteVM
import com.example.exchangeratesapp.presentation.fragmentListCurrencies.FragmentListCurrencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: FragmentListCurrencies)
    fun inject(fragment: FragmentFavorite)
    fun inject(fragment: FragmentConvertorCurrency)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}