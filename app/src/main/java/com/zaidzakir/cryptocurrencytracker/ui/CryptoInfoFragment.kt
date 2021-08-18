package com.zaidzakir.cryptocurrencytracker.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crypto_info.*
import kotlinx.coroutines.flow.collect
import java.text.NumberFormat
import java.text.SimpleDateFormat
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

        getCryptoTimeSeries()
    }

    private fun getCryptoTimeSeries() {
        cryptoTrackerViewModel.getCryptoTimeSeries(tvcryptoSymbol.text.toString())
        lifecycleScope.launchWhenResumed {
            cryptoTrackerViewModel.cryptoTimeSeriesFlow.collect { cryptoTimeSeries ->
                when (cryptoTimeSeries) {
                    is CryptoTrackerViewModel.Events.CryptoTimeSeriesSuccess -> {
                        progressBarCryptoInfo.isVisible = false
                        val entries = ArrayList<Entry>()
                        for (timeArray in cryptoTimeSeries.cryptoTimeSeriesResponse) {
                            for (time in timeArray.timeSeries) {
                                var price = "%.2f".format(time.close)
                                var time = getDateTime(time.time.toFloat())
                                println("Time_Series : Price - $price | Time - $time")
                                entries.add(Entry(time!!.toFloat(), price.toFloat()))
                            }
                        }

                        drawChart(entries)
                    }
                    is CryptoTrackerViewModel.Events.Failure -> {
                        progressBarCryptoInfo.isVisible = false
                        view?.let { view ->
                            Snackbar.make(
                                    view,
                                    "getCryptoTimeSeries api Failure",
                                    Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                    is CryptoTrackerViewModel.Events.Loading -> {
                        progressBarCryptoInfo.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun drawChart(entries: ArrayList<Entry>) {
        val lineDataSet = LineDataSet(entries, tvcryptoSymbol.text.toString())
        val data = LineData(lineDataSet)
        cryptoChart.data = data
        cryptoChart.setBackgroundColor(Color.WHITE)
        cryptoChart.animateY(500)
    }

    private fun getDateTime(s: Float): String? {
        return try {
            val sdf = SimpleDateFormat("dd.MM")
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}