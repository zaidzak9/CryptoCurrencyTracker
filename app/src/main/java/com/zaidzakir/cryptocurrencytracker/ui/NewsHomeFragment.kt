package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.adapters.NewsTabLayoutAdapter
import kotlinx.android.synthetic.main.news_home_fragment.*

/**
 *Created by Zaid Zakir
 */

val tabLayoutNames = arrayOf(
    "Crytpo News",
    "Saved News"
)

class NewsHomeFragment : Fragment(R.layout.news_home_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "News"

        val adapter = NewsTabLayoutAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabLayoutNames[position]
        }.attach()
    }
}