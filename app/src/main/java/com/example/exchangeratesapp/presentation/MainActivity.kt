package com.example.exchangeratesapp.presentation

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = binding.btmNav
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(bottomNavigation, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            TransitionManager.beginDelayedTransition(binding.btmNav, Fade())
            if (destination.id == R.id.fragmentSplash) {
                binding.btmNav.visibility = View.GONE
            } else {
                binding.btmNav.visibility = View.VISIBLE
            }
        }
    }
}

