package com.example.exchangeratesapp.presentation.fragmentListCurrencies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.databinding.FragmentListCurrenciesBinding
import com.example.exchangeratesapp.domain.model.Currency
import com.example.exchangeratesapp.presentation.ViewModelFactory
import com.example.exchangeratesapp.presentation.adapter.CurrencyAdapter
import com.example.exchangeratesapp.di.App
import com.example.exchangeratesapp.util.BASE_CURRENCY
import com.example.exchangeratesapp.util.BASE_CURRENCY_KEY
import com.example.exchangeratesapp.util.CURRENCY_NAME_KEY
import com.example.exchangeratesapp.util.CURRENCY_VALUE_KEY
import kotlinx.coroutines.delay
import javax.inject.Inject

class FragmentListCurrencies : Fragment() {
    private lateinit var countries: List<String>
    private lateinit var viewModel: FragmentListCurrenciesVM
    private lateinit var currencyAdapter: CurrencyAdapter
    private var _binding: FragmentListCurrenciesBinding? = null
    private val binding: FragmentListCurrenciesBinding
        get() = _binding ?: throw RuntimeException("FragmentListCurrenciesBinding is null")

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
        _binding = FragmentListCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[FragmentListCurrenciesVM::class.java]
        currencyAdapter = CurrencyAdapter(requireActivity())

        showLoading()
        prepareRadioGroup()
        prepareCategoryRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        prepareTextInputLayout()

    }

    private fun prepareRadioGroup() {
        val radioGroup = binding.radioGroup
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            getListCurrenciesByRadio(checkedId)
        }

    }

    private fun prepareTextInputLayout() {
        countries = resources.getStringArray(R.array.country_symbols).toList()
        val arrayAdapter = ArrayAdapter(
            requireActivity(), R.layout.drop_down_item, countries
        )
        val autoCompleteTV = binding.autoCompleteTv
        autoCompleteTV.apply {
            setText(context.getString(R.string.autoCompleteSetText))
            setAdapter(arrayAdapter)
            setOnItemClickListener { _, _, it, _ ->
                val fullNameCurrency = autoCompleteTV.text.toString()
                stopLoading()
                BASE_CURRENCY = fullNameCurrency.substring(0, 3)
                getListCurrenciesByRadio(binding.radioGroup.checkedRadioButtonId)
                showLoading()
                Toast.makeText(requireActivity(), BASE_CURRENCY, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getListCurrenciesByRadio(id: Int) {
        currencyAdapter.submitList(listOf())
        when (id) {
            R.id.radio2 -> {
                viewModel.getListCurrencies("alphabetically-desc")
            }
            R.id.radio3 -> {
                viewModel.getListCurrencies("ByValue-ask")
            }
            R.id.radio4 -> {
                viewModel.getListCurrencies("ByValue-desk")
            }
            else -> {
                viewModel.getListCurrencies()
            }
        }
    }

    private fun prepareCategoryRecyclerView() {
        binding.rvCurrency.adapter = currencyAdapter
        viewModel.getListCurrenciesLiveData().observe(viewLifecycleOwner) {
            currencyAdapter.submitList(it)
            stopLoading()
        }

        onItemRVclick()
        onFavoriteClick()
    }

    private fun onItemRVclick() {
        currencyAdapter.onItemClick = object : CurrencyAdapter.OnItemClick {
            override fun onItemClick(currency: Currency) {
                val bundle = bundleOf(
                    CURRENCY_NAME_KEY to currency.name,
                    CURRENCY_VALUE_KEY to currency.value,
                    BASE_CURRENCY_KEY to BASE_CURRENCY
                )
                view?.let {
                    findNavController().navigate(
                        R.id.action_fragmentListCurrencies_to_fragmentConvertorCurrency,
                        bundle
                    )
                }
            }
        }
    }

    private fun onFavoriteClick() {
        currencyAdapter.onFavoriteClick = object : CurrencyAdapter.OnFavoriteClick {
            override fun onFavoriteClick(currency: Currency) {
                currency.let {
                    viewModel.saveCurrencyFavorite(it)
                    it.favorite = true
                    Toast.makeText(activity, "Saved ${currency.name}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun showLoading() {
        binding.rvCurrency.visibility = View.INVISIBLE
        val progressBar = binding.progressBar
        if (progressBar != null) {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun stopLoading() {
        binding.rvCurrency.visibility = View.VISIBLE
        val progressBar = binding.progressBar
        if (progressBar != null) {
            progressBar.visibility = View.INVISIBLE
        }
    }
}

