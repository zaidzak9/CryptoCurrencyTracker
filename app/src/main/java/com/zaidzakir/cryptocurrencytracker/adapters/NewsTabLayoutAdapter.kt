package com.zaidzakir.cryptocurrencytracker.adapters

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zaidzakir.cryptocurrencytracker.ui.NewsFragment
import com.zaidzakir.cryptocurrencytracker.ui.NewsHomeFragment
import com.zaidzakir.cryptocurrencytracker.ui.SavedNewsFragment
import com.zaidzakir.cryptocurrencytracker.util.Constants.NUM_TABS

/**
 *Created by Zaid Zakir
 */
class NewsTabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS;
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return NewsFragment()
            1 -> return SavedNewsFragment()
        }
        return NewsHomeFragment()
    }
}