package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.adapters.LatestCryptoInfoAdapter
import com.zaidzakir.cryptocurrencytracker.adapters.LatestCryptoPagingAdapter
import com.zaidzakir.cryptocurrencytracker.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.fragment_cryptotracker.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.flow.collect

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var newsAdapter: NewsAdapter
    private val cryptoViewModel:CryptoTrackerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_articleFragment,
                bundle
            )
        }
        getCryptoDataFromStateFlow()


    }

    private fun getCryptoDataFromStateFlow(){
        cryptoViewModel.getCryptoNews()
        lifecycleScope.launchWhenStarted {
            cryptoViewModel.cryptoNewsFlow.collect{cryptoNewsResponse ->
                when(cryptoNewsResponse){
                    is CryptoTrackerViewModel.Events.NewsSuccess -> {
                        progressBar.isVisible = false
                        cryptoNewsResponse.let {
                            newsAdapter.differ.submitList(it.cryptoResponse.data?.articles)
                        }
                    }
                    is CryptoTrackerViewModel.Events.Failure -> {
                        progressBar.isVisible = false
                        view?.let {
                            Snackbar.make(
                                it,
                                "Crypto api Failure",
                                Snackbar.LENGTH_LONG).show()
                        }
                    }
                    is CryptoTrackerViewModel.Events.Loading -> {
                        progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun recyclerView(){
        newsAdapter = NewsAdapter()

        rvCryptoNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}