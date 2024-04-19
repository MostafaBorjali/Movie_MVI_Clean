package com.borjali.presentation.ui.splash

import androidx.lifecycle.lifecycleScope
import com.borjali.domain.Constants.Companion.SPLASH_DELAY
import com.borjali.presentation.R
import com.borjali.presentation.databinding.FragmentSplashBinding
import com.borjali.presentation.extensions.navigate
import com.borjali.presentation.ui.base.BaseFragment
import com.borjali.presentation.ui.home.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, MovieViewModel>(FragmentSplashBinding::inflate) {
    override fun subscribeObservers() {
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            navigate(R.id.mainFragment)
        }
    }
}
