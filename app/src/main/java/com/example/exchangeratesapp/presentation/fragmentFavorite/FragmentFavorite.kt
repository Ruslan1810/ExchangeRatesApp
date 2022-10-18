package com.example.exchangeratesapp.presentation.fragmentFavorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.databinding.FragmentFavoriteBinding
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.presentation.ViewModelFactory
import com.example.exchangeratesapp.presentation.adapter.CurrencyAdapter
import com.example.exchangeratesapp.di.App
import com.example.exchangeratesapp.presentation.fragmentListCurrencies.FragmentListCurrencies
import com.example.exchangeratesapp.util.BASE_CURRENCY_KEY
import com.example.exchangeratesapp.util.CURRENCY_NAME_KEY
import com.example.exchangeratesapp.util.CURRENCY_VALUE_KEY
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FragmentFavorite : Fragment() {

    private lateinit var viewModel: FragmentFavoriteVM
    private lateinit var currencyAdapter: CurrencyAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoriteBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[FragmentFavoriteVM::class.java]

        prepareFavoriteRecyclerView()

    }

    var position:Int = 0
    private fun itemTouchHandler() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                position = viewHolder.adapterPosition
                val currency = currencyAdapter.getCurrencyByPosition(position)
                deleteCurrencyFromFavorite(currency)
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvCurrencyFavorite)
    }

    private fun prepareFavoriteRecyclerView() {
        currencyAdapter = CurrencyAdapter(requireActivity())
        binding.rvCurrencyFavorite.adapter = currencyAdapter
        lifecycleScope.launchWhenStarted {
            viewModel.getFavoriteCurrenciesStateFlow()?.collect {
                currencyAdapter.submitList(it)
            }
        }
        onItemRVclick()
        itemTouchHandler()
        onFavoriteClick()
    }

    private fun onItemRVclick() {
        currencyAdapter.onItemClick = object : CurrencyAdapter.OnItemClick {
            override fun onItemClick(currency: Currency) {
                val bundle = bundleOf(
                    CURRENCY_NAME_KEY to currency.name,
                    CURRENCY_VALUE_KEY to currency.value,
                    BASE_CURRENCY_KEY to FragmentListCurrencies.BASE_CURRENCY
                )
                view?.let {
                    findNavController().navigate(
                        R.id.action_fragmentFavorite_to_fragmentConvertorCurrency,
                        bundle
                    )
                }
            }
        }
    }

    private fun onFavoriteClick() {

        currencyAdapter.onFavoriteClick = object : CurrencyAdapter.OnFavoriteClick {
            override fun onFavoriteClick(currency: Currency) {
                deleteCurrencyFromFavorite(currency)
            }
        }
    }
    private fun deleteCurrencyFromFavorite(currency: Currency){
        currency.let {
            viewModel.deleteCurrency(currency)

            Snackbar.make(requireView(), "Валюта была удалена", Snackbar.LENGTH_LONG).apply {
                setAction("Отменить") {
                    viewModel.saveCurrencyFavorite(currency)
                }.show()
            }
            Toast.makeText(activity, "Deleted ${currency.name}", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}