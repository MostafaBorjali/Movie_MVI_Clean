package com.borjali.presentation.ui.home.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.borjali.presentation.ui.home.information.InformationFragment

import com.borjali.presentation.ui.home.movieslist.MoviesFragment


class ViewPagerFragmentAdapterQuestionnairesList(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment()
            1 -> InformationFragment()
            else -> MoviesFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
