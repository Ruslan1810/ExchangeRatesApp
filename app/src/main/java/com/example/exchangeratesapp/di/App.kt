package com.example.exchangeratesapp.di

import android.app.Application

class App: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}