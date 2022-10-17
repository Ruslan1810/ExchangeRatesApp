package com.example.exchangeratesapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.exchangeratesapp.data.network.model.CurrencyApi
import com.example.exchangeratesapp.domain.model.Currency

object CurrencyDiffCallback: DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }
}