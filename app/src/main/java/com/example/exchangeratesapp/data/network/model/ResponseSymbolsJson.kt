package com.example.exchangeratesapp.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseSymbolsJson {
    @SerializedName("symbols")
    @Expose
    val symbolsJsonObject: JsonObject? = null
}