package com.example.exchangeratesapp.presentation.fragmentSplash

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.databinding.FragmentSplashBinding

class FragmentSplash : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = ""
        initialization()
    }

    private fun initialization() {
        requireActivity().actionBar?.hide()
        //определение ориентации
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mBinding.appName.animate().translationY(-1300f).setDuration(3000).startDelay = 0
            mBinding.lottie.animate().translationX(2000F).setDuration(3000).startDelay = 3000
        } else {
            mBinding.appName.animate().translationY(-700f).setDuration(2000).startDelay = 0
            mBinding.lottie.animate().translationX(2000F).setDuration(3000).startDelay = 2500
        }

        view?.postDelayed({
            findNavController().navigate(R.id.action_fragmentSplash_to_fragmentListCurrencies)
            requireActivity()
        }, 5000)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}