package com.borjali.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.borjali.domain.model.event.EventErrorInternetConnection
import com.borjali.domain.utils.EventOfCleanApp
import com.borjali.presentation.R
import com.borjali.presentation.databinding.ActivityMainBinding
import com.borjali.presentation.extensions.isOnline
import com.borjali.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        subscribeObservers()
        backHandle()
    }


    /**
     * Start listening to Data State Event
     */
    private fun subscribeObservers() {

        /**
         * when not found internet connection,
         * we post  EventErrorInternetConnection and listening here for manage this
         */
        EventOfCleanApp.registerEvent(EventErrorInternetConnection::class.java) {
            binding.internetErrorLayout.visibility = View.VISIBLE
        }

        binding.internetErrorLayout.setOnClickListener { }
        binding.buttonRetry.setOnClickListener {
            if (this@MainActivity.isOnline)
                binding.internetErrorLayout.visibility = View.GONE
        }
    }

    /**
     * this function new way for behavior back button
     */
    private fun backHandle() {
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (navController.currentDestination?.id == R.id.mainFragment)
                        finish()
                    navController.popBackStack()
                }
            }
        )
    }

    /**
     * this function setup navigation component and handle tool bar with back button view
     */
    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

    }

}
