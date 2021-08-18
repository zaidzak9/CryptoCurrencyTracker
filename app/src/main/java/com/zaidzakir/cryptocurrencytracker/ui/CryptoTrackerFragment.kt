package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.adapters.LatestCryptoInfoAdapter
import com.zaidzakir.cryptocurrencytracker.adapters.LatestCryptoPagingAdapter
import com.zaidzakir.cryptocurrencytracker.util.Constants.cryptoHashData
import com.zaidzakir.cryptocurrencytracker.util.Constants.cryptoMetaData
import com.zaidzakir.cryptocurrencytracker.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.fragment_cryptotracker.*
import kotlinx.coroutines.flow.collect
import java.util.*
import kotlin.collections.isNotEmpty
import kotlin.collections.set


/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class CryptoTrackerFragment : Fragment(R.layout.fragment_cryptotracker) {
    lateinit var cryptoInfoAdapter: LatestCryptoInfoAdapter
    lateinit var cryptoPagingInfoAdapter: LatestCryptoPagingAdapter
    private val cryptoViewModel: CryptoTrackerViewModel by viewModels()
    private var searchLength = 1
    private var sort = "p"
    private var order = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.title = "Crypto Market"
        spinnerInitialization()
        cryptoViewModel.getSavedCryptoMetaData().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                cryptoMetaData = it
                mapImageWithID()
            }
        })

        recyclerView()
        getCryptoDataFromStateFlow("acr", true)
        //getCryptoLiveDataUsingNetworkBoundResource("p", true)
        //this uses paging 3 to manage response
        //getCryptoDataUsingPaging3()
        cryptoInfoAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("CoinData", it)
            }
            findNavController().navigate(
                    R.id.action_cryptoTrackerFragment_to_cryptoInfoFragment,
                    bundle
            )
        }
    }

    private fun mapImageWithID() {
        for (crypto in cryptoMetaData) {
            if (crypto.id != null && crypto.image != null) {
                cryptoHashData[crypto.id] = crypto.image
            }
        }
        println("size_of_crypto_meta_hash ${cryptoHashData.size}")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_crypto_fragment, menu)

        val searchCrypto = menu.findItem(R.id.cryptoSearch)
        val searchView = searchCrypto.actionView as SearchView

        // Define the listener
        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                cryptoViewModel.getCryptoMarketNBR("p", true)
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

    private fun spinnerInitialization() {
        ArrayAdapter.createFromResource(
                context!!,
                R.array.spinner_time_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerTime.adapter = adapter
        }

        ArrayAdapter.createFromResource(
                context!!,
                R.array.spinner_other_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerOther.adapter = adapter
        }

        spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (parent?.getItemAtPosition(position) as String) {
                    "Low to High" -> order == null
                    "High to Low" -> order = true
                }
                if (parent?.getItemAtPosition(position) != "Sort Order") {
                    getCryptoDataFromStateFlow(sort, order)
                    cryptoInfoAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            super.onItemRangeInserted(positionStart, itemCount)
                            rvCryptoInfo.smoothScrollToPosition(0)
                        }
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerOther.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sort = parent?.getItemAtPosition(position) as String
                when (sort) {
                    "Price" -> sort = "p"
                    "Percent Change (24 Hours)" -> sort = "pc"
                    "Percent Change (1 Hours)" -> sort = "pch"
                    "Rank" -> sort = "acr"
                    "Market Cap" -> sort = "mc"
                }
                if (!sort.contentEquals("Sort by Value")) {
                    getCryptoDataFromStateFlow(sort, order)
                    cryptoInfoAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            super.onItemRangeInserted(positionStart, itemCount)
                            rvCryptoInfo.smoothScrollToPosition(0)
                        }
                    })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun getCryptoDataUsingPaging3() {
        recyclerViewPaging()
        lifecycleScope.launchWhenStarted {
            cryptoViewModel.cryptoResponseFromPaging.observe(viewLifecycleOwner) {
                cryptoPagingInfoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    //using stateflow for filtering function
    private fun getCryptoDataFromStateFlow(sort: String, order: Boolean) {
        cryptoViewModel.getCryptoMarket(sort, order)
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
                                    Snackbar.LENGTH_LONG
                            ).show()
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

    private fun getCryptoLiveDataUsingNetworkBoundResource(sort: String, order: Boolean) {
        cryptoViewModel.getCryptoMarketNBR(sort, order).observe(viewLifecycleOwner, { cryptoResponse ->
            when (cryptoResponse) {
                is Resource.Success -> {
                    progressBar.isVisible = false
                    cryptoResponse.let {
                        cryptoInfoAdapter.differ.submitList(cryptoResponse.data)
                    }
                }
                is Resource.Error -> {
                    progressBar.isVisible = false
                    view?.let {
                        Snackbar.make(
                                it,
                                "Crypto api Failure",
                                Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    if (cryptoResponse.data?.size == 0) {
                        cryptoViewModel.getCryptoMarketNBR(sort, order)
                    } else {
                        cryptoInfoAdapter.differ.submitList(cryptoResponse.data)
                    }

                    //progressBar.isVisible = true
                }
            }
        }
        )
    }

    private fun recyclerView() {
        cryptoInfoAdapter = LatestCryptoInfoAdapter()

        rvCryptoInfo.apply {
            adapter = cryptoInfoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun recyclerViewPaging() {
        cryptoPagingInfoAdapter = LatestCryptoPagingAdapter()

        rvCryptoInfo.apply {
            adapter = cryptoPagingInfoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}