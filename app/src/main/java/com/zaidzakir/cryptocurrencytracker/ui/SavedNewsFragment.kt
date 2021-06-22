package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.flow.collect

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}