package com.borjali.presentation.ui.home.base

import androidx.core.content.ContextCompat
import com.borjali.domain.Constants.Companion.TAB
import com.borjali.domain.preference.CleanAppPreferences
import com.borjali.presentation.R
import com.borjali.presentation.databinding.FragmentMainBinding
import com.borjali.presentation.ui.base.BaseFragment
import com.borjali.presentation.ui.home.MovieViewModel

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment :
    BaseFragment<FragmentMainBinding, MovieViewModel>(FragmentMainBinding::inflate) {

    @Inject
    lateinit var preferences: CleanAppPreferences
    override fun subscribeObservers() {
        initTabLayout()
    }

    private fun initTabLayout() {

        val titles = resources.getStringArray(R.array.title)
        binding.viewPager.adapter = activity?.let { ViewPagerFragmentAdapterQuestionnairesList(it) }
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
        //binding.viewPager.setPageTransformer(DepthTransformer())
        binding.viewPager.isSaveEnabled = true
        binding.viewPager.post {
            val position = preferences.getInt(TAB, 1)
            binding.viewPager.setCurrentItem(position, false)
            binding.tabLayout.getTabAt(position)?.let { tab -> setTabBackground(tab) }
            // binding.viewPager.setCurrentItem(0, true)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    preferences.setInt(TAB, it.position)
                    setTabBackground(it)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                tab?.view?.background =
                    ContextCompat.getDrawable(requireContext(), R.color.transparent)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setTabBackground(tab: TabLayout.Tab) {
        if (tab.position == 1) {
            tab.view.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.tab_background_selected_yellow
            )
            binding.tabLayout.setTabTextColors(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_4
                ),
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            )
        } else {
            binding.tabLayout.setTabTextColors(
                ContextCompat.getColor(requireContext(), R.color.gray_4),
                ContextCompat.getColor(requireContext(), R.color.white)
            )
            tab.view.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.tab_background_selected
            )
        }
    }
}