package com.example.exchangeratesapp.di

import android.app.Application
import com.example.exchangeratesapp.data.db.CurrencyDao
import com.example.exchangeratesapp.data.db.CurrencyDatabase
import com.example.exchangeratesapp.data.network.ApiFactory
import com.example.exchangeratesapp.data.network.ApiService
import com.example.exchangeratesapp.data.repository.ApiRepositoryImpl
import com.example.exchangeratesapp.data.repository.DbRepositoryImpl
import com.example.exchangeratesapp.domain.repository.ApiRepository
import com.example.exchangeratesapp.domain.repository.DbRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    fun binDbRepository(repository: DbRepositoryImpl): DbRepository

    @Binds
    fun binApiRepository(repository: ApiRepositoryImpl): ApiRepository

    companion object {
        @Singleton
        @Provides
        fun provideMealDao(application: Application): CurrencyDao {
            return CurrencyDatabase.getInstance(application).dao()
        }
        @Singleton
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}