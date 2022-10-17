package com.example.exchangeratesapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.data.mapper.Mapper
import com.example.exchangeratesapp.databinding.ItemRvBinding
import com.example.exchangeratesapp.domain.model.Currency


class CurrencyAdapter(private val context: Context):
    ListAdapter<Currency, CurrencyAdapter.CurrencyHolder>(CurrencyDiffCallback) {

    lateinit var onItemClick: OnItemClick
    lateinit var onFavoriteClick: OnFavoriteClick
    private val mapper = Mapper()

    class CurrencyHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        return CurrencyHolder(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.binding.currencyName.text = getItem(position).name
        holder.binding.currencyValue.text = String.format("%.2f", getItem(position).value)
        holder.binding.flagCountry.setImageResource(
            mapper.getIdResource(getItem(position).name, context)
        )

        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(getItem(position))
        }

        holder.binding.btnFavorite.setOnClickListener {
            onFavoriteClick.onFavoriteClick(getItem(position))
        }
    }
    fun getCurrencyByPosition(position: Int):Currency{
        return getItem(position)
    }

    interface OnItemClick {
        fun onItemClick(currency: Currency)
    }

    interface OnFavoriteClick{
        fun onFavoriteClick(currency: Currency)
    }
}

