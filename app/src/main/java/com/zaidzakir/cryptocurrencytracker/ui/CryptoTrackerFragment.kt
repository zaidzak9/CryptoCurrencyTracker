package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.adapters.LatestCryptoInfoAdapter
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CoinData
import com.zaidzakir.cryptocurrencytracker.util.Resource
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
    private val cryptoViewModel:CryptoTrackerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()

//        cryptoViewModel.getCryptoMarket

        cryptoViewModel.getCryptoMarket.observe(viewLifecycleOwner, Observer {cryptoResponse->
            when(cryptoResponse){
                is Resource.Success->{
                    progressBar.isVisible = false
                        cryptoResponse.let {
                        cryptoInfoAdapter.differ.submitList(cryptoResponse.data)
                    }
                }
                is Resource.Error->{
                    progressBar.isVisible = false
                        Snackbar.make(
                                view,
                                "Crypto api Failure",
                                Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading->{
                    cryptoInfoAdapter.differ.submitList(cryptoResponse.data)
                    progressBar.isVisible = true
                }
            }
        })

//        lifecycleScope.launchWhenStarted {
//            cryptoViewModel.cryptoMarketFlow.collect{cryptoResponse ->
//                when(cryptoResponse){
//                    is CryptoTrackerViewModel.Events.Success -> {
//                        progressBar.isVisible = false
//                        cryptoResponse.let {
//                           cryptoInfoAdapter.differ.submitList(it.cryptoResponse)
//                        }
//                    }
//                    is CryptoTrackerViewModel.Events.Failure -> {
//                        progressBar.isVisible = false
//                        Snackbar.make(
//                                view,
//                                "Crypto api Failure",
//                                Snackbar.LENGTH_LONG).show()
//                    }
//                    is CryptoTrackerViewModel.Events.Loading -> {
//                        progressBar.isVisible = true
//                    }
//                    else -> Unit
//                }
//            }
//        }
    }

    private fun recyclerView(){
        cryptoInfoAdapter = LatestCryptoInfoAdapter()

        rvCryptoInfo.apply {
            adapter = cryptoInfoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}