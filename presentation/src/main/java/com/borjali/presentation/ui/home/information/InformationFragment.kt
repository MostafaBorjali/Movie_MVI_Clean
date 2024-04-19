package com.borjali.presentation.ui.home.information

import com.borjali.presentation.databinding.FragmentInformationBinding
import com.borjali.presentation.ui.base.BaseFragment
import com.borjali.presentation.ui.home.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment :
    BaseFragment<FragmentInformationBinding, MovieViewModel>(FragmentInformationBinding::inflate) {

    override fun subscribeObservers() {

    }
}
