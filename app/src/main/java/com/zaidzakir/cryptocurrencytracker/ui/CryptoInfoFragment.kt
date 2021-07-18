package com.zaidzakir.cryptocurrencytracker.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto_info.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

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
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")



        coinData?.let {
            tvCryptoName.text = it.n
            tvcryptoSymbol.text = it.s
            Glide.with(this).load(Constants.cryptoHashData[it.id]).into(ivCryptoImage)
            tvCryptoPrice.text = "${format.format(it.p)}"
            tvCryptoMarket.text = "Market : ${format.format(it.mc)}"
            println("Market : USD ${format.format(it.mc)}")

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

        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 0f))
        entries.add(Entry(2f, 1f))
        entries.add(Entry(3f, 2f))
        entries.add(Entry(4f, 3f))
        entries.add(Entry(5f, 4f))
        entries.add(Entry(6f, 5f))

        val lineDataSet = LineDataSet(entries, "test")
        val data = LineData(lineDataSet)
        cryptoChart.data = data
        cryptoChart.setBackgroundColor(Color.WHITE)
        cryptoChart.animateY(500)


    }
}