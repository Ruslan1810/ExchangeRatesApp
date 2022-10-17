package com.example.exchangeratesapp.data.mapper

import android.content.Context
import com.example.exchangeratesapp.data.db.model.CurrencyDB
import com.example.exchangeratesapp.data.network.model.ResponseRatesJsonApi
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.domain.model.ResponseRatesJson
import javax.inject.Inject

class Mapper @Inject constructor(){

    fun responseRatesJsonApiToResponseRatesJson(responseRatesJsonApi: ResponseRatesJsonApi)
            : ResponseRatesJson {
        val responseRatesJson = ResponseRatesJson()
        responseRatesJson.ratesJsonObject = responseRatesJsonApi.ratesJsonObject
        return responseRatesJson
    }

    fun currencyToCurrencyDB(currency: Currency): CurrencyDB {
        return CurrencyDB(
            name = currency.name,
            value = currency.value,
            favorite = currency.favorite
        )
    }

    fun currencyDBToCurrency(currency: CurrencyDB): Currency {
        return Currency(
            name = currency.name,
            value = currency.value,
            favorite = currency.favorite
        )
    }


    fun getRatesListFromJson(
        responseRatesJson: ResponseRatesJson
    ): List<Currency> {
        val listCurrencies = mutableListOf<Currency>()

        val jsonObject = responseRatesJson.ratesJsonObject
        val ratesKeySet = jsonObject?.keySet()

        if (ratesKeySet != null) {
            for (ratesKey in ratesKeySet) {
                val ratesValue = jsonObject.get(ratesKey).asDouble
                val currency = Currency(ratesKey, ratesValue, false)
                listCurrencies.add(currency)
            }
        }
        return listCurrencies
    }

    fun getIdResource(charCode: String, context: Context): Int {
        var nameResource = charCode.lowercase()
        if (charCode == "TRY") nameResource = "tur"

        return context.resources.getIdentifier(
            nameResource, "drawable",
            context.packageName
        )
    }

}