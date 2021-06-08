package com.zaidzakir.cryptocurrencytracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CoinData
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
): ViewModel() {

    sealed class Events{
        class Success(val cryptoResponse: List<CoinData>):Events()
        object Failure : Events()
        object Loading : Events()
        object Empty : Events()
    }

    private val _cryptoMarketFlow = MutableStateFlow<Events>(Events.Empty)
    val cryptoMarketFlow:StateFlow<Events> =_cryptoMarketFlow

    fun getCryptoMarket() = viewModelScope.launch (Dispatchers.IO){
        _cryptoMarketFlow.value = Events.Loading
        when(val cryptoResponse = defaultRepository.getCoinsMarket()){
            is Resource.Error -> _cryptoMarketFlow.value = Events.Failure
            is Resource.Success -> {
                val cryptoResponse = cryptoResponse.data!!.data
                if (cryptoResponse == null){
                   _cryptoMarketFlow.value = Events.Failure
                }else{
                    _cryptoMarketFlow.value = Events.Success(cryptoResponse)
                }
            }
        }
    }

}