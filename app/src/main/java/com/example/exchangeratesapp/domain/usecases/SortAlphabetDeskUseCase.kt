package com.example.exchangeratesapp.domain.usecases

import com.example.exchangeratesapp.domain.model.Currency
import java.util.*
import javax.inject.Inject

class SortAlphabetDeskUseCase @Inject constructor(){
    fun sortAlphabeticallyDesk(list: List<Currency>): List<Currency> {
        Collections.sort(list) { o1, o2 ->
            o2.toString().compareTo(o1.toString())
        }
        return list
    }
}