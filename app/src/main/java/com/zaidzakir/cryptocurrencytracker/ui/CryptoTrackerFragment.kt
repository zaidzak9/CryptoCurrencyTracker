package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
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
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.MetaData
import com.zaidzakir.cryptocurrencytracker.util.Constants.cryptoMetaData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.fragment_cryptotracker.*
import kotlinx.coroutines.flow.collect

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class CryptoTrackerFragment : Fragment(R.layout.fragment_cryptotracker) {
    lateinit var cryptoInfoAdapter: LatestCryptoInfoAdapter
    lateinit var cryptoPagingInfoAdapter: LatestCryptoPagingAdapter
    private val cryptoViewModel:CryptoTrackerViewModel by viewModels()
    private var searchLength =1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.title = "Crypto Market"

        recyclerView()

        getCryptoDataFromStateFlow()

        cryptoViewModel.getSavedCryptoMetaData().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                cryptoMetaData = it
            }
        })

        //this uses paging 3 to manage response
        // recyclerViewPaging()
//        lifecycleScope.launchWhenStarted {
//            cryptoViewModel.cryptoResponseFromPaging.observe(viewLifecycleOwner) {
//                cryptoPagingInfoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
//            }
//        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_crypto_fragment, menu)

        val searchCrypto = menu.findItem(R.id.cryptoSearch)
        val searchView = searchCrypto.actionView as SearchView

        // Define the listener
        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                cryptoViewModel.getCryptoMarket()
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }
        }
        val actionMenuItem = menu?.findItem(R.id.cryptoSearch)
        actionMenuItem?.setOnActionExpandListener(expandListener)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                cryptoInfoAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cryptoInfoAdapter.filter.filter(newText)
                return true
            }

        })
    }

    //using stateflow for filtering function
    private fun getCryptoDataFromStateFlow() {
        cryptoViewModel.getCryptoMarket()
        lifecycleScope.launchWhenStarted {
            cryptoViewModel.cryptoMarketFlow.collect { cryptoResponse ->
                when (cryptoResponse) {
                    is CryptoTrackerViewModel.Events.Success -> {
                        progressBar.isVisible = false
                        cryptoResponse.let {
                            cryptoInfoAdapter.differ.submitList(it.cryptoResponse)
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
        cryptoInfoAdapter = LatestCryptoInfoAdapter()

        rvCryptoInfo.apply {
            adapter = cryptoInfoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun recyclerViewPaging(){
        cryptoPagingInfoAdapter = LatestCryptoPagingAdapter()

        rvCryptoInfo.apply {
            adapter = cryptoPagingInfoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}