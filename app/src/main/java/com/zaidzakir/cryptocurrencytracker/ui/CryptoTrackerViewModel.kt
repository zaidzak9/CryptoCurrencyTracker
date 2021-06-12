package com.zaidzakir.cryptocurrencytracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.NewsResponse
import com.zaidzakir.cryptocurrencytracker.repositories.remote.DefaultRepository
import com.zaidzakir.cryptocurrencytracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
@HiltViewModel
class CryptoTrackerViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository
) : ViewModel() {

    sealed class Events{
        class Success(val cryptoResponse: List<CoinData>):Events()
        class NewsSuccess(val cryptoResponse: Resource<NewsResponse>):Events()
        object Failure : Events()
        object Loading : Events()
        object Empty : Events()
    }

    private val _cryptoMarketFlow = MutableStateFlow<Events>(Events.Empty)
    val cryptoMarketFlow: StateFlow<Events> = _cryptoMarketFlow

    private val _cryptoNewsFlow = MutableStateFlow<Events>(Events.Empty)
    val cryptoNewsFlow: StateFlow<Events> = _cryptoNewsFlow

    fun getCryptoMarket() = viewModelScope.launch(Dispatchers.IO) {
        _cryptoMarketFlow.value = Events.Loading
        when (val cryptoResponse = defaultRepository.getCoinsMarket()) {
            is Resource.Error -> _cryptoMarketFlow.value = Events.Failure
            is Resource.Success -> {
                val cryptoResponse = cryptoResponse.data!!.data
                if (cryptoResponse == null) {
                    _cryptoMarketFlow.value = Events.Failure
                } else {
                    _cryptoMarketFlow.value = Events.Success(cryptoResponse)
                }
            }
        }
    }

    fun getCryptoNews() = viewModelScope.launch(Dispatchers.IO) {
        _cryptoNewsFlow.value = Events.Loading
        when (val cryptoNewsResponse = defaultRepository.getNewsApi()) {
            is Resource.Error -> _cryptoNewsFlow.value = Events.Failure
            is Resource.Success -> {
                val cryptoResponse = cryptoNewsResponse.data
                if (cryptoResponse == null) {
                    _cryptoNewsFlow.value = Events.Failure
                } else {
                    _cryptoNewsFlow.value = Events.NewsSuccess(cryptoNewsResponse)
                }
            }
        }
    }

    val cryptoResponseFromPaging = defaultRepository.getCoinsMarketPaging().cachedIn(viewModelScope)

}