package com.zaidzakir.cryptocurrencytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import kotlinx.android.synthetic.main.latest_crypto_info.view.*

/**
 *Created by Zaid Zakir
 */
class LatestCryptoInfoAdapter : RecyclerView.Adapter<LatestCryptoInfoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differentCallback = object : DiffUtil.ItemCallback<CoinData>(){
        override fun areItemsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differentCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.latest_crypto_info,parent,false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = differ.currentList[position]
        holder.itemView.apply {
            //Glide.with(this).load(crypto.data.get(position)).into(ivCryptoImage)
            tvCryptoName.text = crypto.n
            tvCryptoPrice.text = crypto.p.toString()
            setOnClickListener {
                onItemClickListener?.let {
                    it(crypto)
                }
            }
        }
    }

    private var onItemClickListener:((CoinData) -> Unit)? = null

    fun setOnItemClickListener(listener:(CoinData) -> Unit){
        onItemClickListener = listener
    }
}