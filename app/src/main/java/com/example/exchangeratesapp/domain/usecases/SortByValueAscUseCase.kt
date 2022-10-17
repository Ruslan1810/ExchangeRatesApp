package com.example.exchangeratesapp.domain.usecases

import com.example.exchangeratesapp.domain.model.Currency
import java.util.*
import javax.inject.Inject

class SortByValueAscUseCase @Inject constructor(){
    fun sortByValueAsk(list: List<Currency>): List<Currency> {
        Collections.sort(list) { p0, p1 ->
            if (p0.value > p1.value) return@sort 1
            else return@sort -1
        }
        return list
    }
}