package com.zaidzakir.cryptocurrencytracker.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto_info.*
import kotlinx.android.synthetic.main.latest_crypto_info.view.*

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class CryptoInfoFragment : Fragment(R.layout.fragment_crypto_info) {
    private val cryptoInfoFragmentArgs: CryptoInfoFragmentArgs by navArgs()
    private val cryptoTrackerViewModel: CryptoTrackerViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coinData = cryptoInfoFragmentArgs.CoinData

        coinData?.let {
            tvCryptoName.text = it.n
            tvcryptoSymbol.text = it.s
            Glide.with(this).load(Constants.cryptoHashData[it.id]).into(ivCryptoImage)
            tvCryptoPrice.text = "USD ${it.p.toString()}"
            tvCryptoMarket.text = "Market Value : USD ${it.mc.toString()}"

            when {
                it.pch!! > 0.0 -> {
                    tvValueChange.setTextColor(Color.GREEN)
                    Glide.with(this).load(R.drawable.ic_arrow_up).into(ivCryptoTrend)

                }
                coinData.pch!! < 0.0 -> {
                    tvValueChange.setTextColor(Color.RED)
                    Glide.with(this).load(R.drawable.ic_arrow_down).into(ivCryptoTrend)
                }
                else -> {
                    tvValueChange.setTextColor(Color.YELLOW)
                    Glide.with(this).load(R.drawable.ic_dash).into(ivCryptoTrend)
                }
            }
            tvValueChange.text = it.pch.toString()
        }


    }
}