package com.zaidzakir.cryptocurrencytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import kotlinx.android.synthetic.main.latest_crypto_info.view.*

/**
 *Created by Zaid Zakir
 */
class LatestCryptoPagingAdapter :
    PagingDataAdapter<CoinData, LatestCryptoPagingAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.latest_crypto_info, parent, false)
        )
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<CoinData>() {
            override fun areItemsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
                return oldItem.p == newItem.p
            }

            override fun areContentsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crypto = getItem(position)
        holder.itemView.apply {
            tvCryptoName.text = crypto?.n
            tvCryptoPrice.text = crypto?.p.toString()
        }
    }


}