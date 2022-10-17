package com.example.exchangeratesapp.presentation.fragmentConvetorCurrency

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.exchangeratesapp.data.mapper.Mapper
import com.example.exchangeratesapp.databinding.FragmentConvertorCurrencyBinding
import com.example.exchangeratesapp.presentation.ViewModelFactory
import com.example.exchangeratesapp.util.BASE_CURRENCY_KEY
import com.example.exchangeratesapp.util.CURRENCY_NAME_KEY
import com.example.exchangeratesapp.util.CURRENCY_VALUE_KEY
import javax.inject.Inject
import  com.example.exchangeratesapp.di.App

class FragmentConvertorCurrency : Fragment() {
    private var symbol: String? = null
    private var value: String? = null
    private var base_symbol: String? = null
    private lateinit var viewModel: FragmentConvertorCurrencyVM
    private var _binding: FragmentConvertorCurrencyBinding? = null
    private val binding: FragmentConvertorCurrencyBinding
        get() = _binding ?: throw RuntimeException("FragmentConvertorCurrencyBinding is null")

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            symbol = it.getString(CURRENCY_NAME_KEY)
            value = it.getDouble(CURRENCY_VALUE_KEY).toString()
            base_symbol = it.getString(BASE_CURRENCY_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConvertorCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[FragmentConvertorCurrencyVM::class.java]

        val nominal = 1
        var amount = 0.0

        "$nominal $base_symbol = $value $symbol".also { binding.currencyComparison.text = it }
        binding.codeCurrency.text = symbol
        binding.codeBase.text = base_symbol
        base_symbol?.let { Mapper().getIdResource(it,requireActivity()) }
            ?.let { binding.flagRus.setImageResource(it) }
        symbol?.let { Mapper().getIdResource(it,requireActivity()) }
            ?.let { binding.flagCountry.setImageResource(it) }

        lifecycleScope.launchWhenStarted {
            viewModel.result.collect {
                binding.outputForCurrency.text = it
            }
        }
        binding.inputForRub.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var strOfInput: String = binding.inputForRub.text.toString()

                try {
                    if (strOfInput.isEmpty()) strOfInput = "0"
                    amount = strOfInput.trim { it <= ' ' }.toDouble()

                } catch (e: NumberFormatException) {

                    if (strOfInput.isNotEmpty()) {
                        Toast.makeText(activity, "Введите число", Toast.LENGTH_SHORT).show()
                        binding.inputForRub.setText(
                            strOfInput.substring(0, binding.inputForRub.length() - 1)
                        )
                        //курсор в конец строки
                        binding.inputForRub.setSelection(binding.inputForRub.text.length)
                    }
                }
                value?.let { viewModel.calculation(it, amount) }
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
            }
        })
    }

}