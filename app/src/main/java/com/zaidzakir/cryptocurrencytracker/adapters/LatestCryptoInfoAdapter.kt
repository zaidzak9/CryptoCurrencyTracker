package com.zaidzakir.cryptocurrencytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CrypoMarketMainResponse
import kotlinx.android.synthetic.main.news_article_info.view.*

/**
 *Created by Zaid Zakir
 */
class LatestCryptoInfoAdapter : RecyclerView.Adapter<LatestCryptoInfoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differentCallback = object : DiffUtil.ItemCallback<CrypoMarketMainResponse>(){
        override fun areItemsTheSame(oldItem: CrypoMarketMainResponse, newItem: CrypoMarketMainResponse): Boolean {
            return oldItem.data == newItem.data
        }

        override fun areContentsTheSame(oldItem: CrypoMarketMainResponse, newItem: CrypoMarketMainResponse): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,differentCallback)


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
            tvCryptoName.text = crypto.data[position].n
            tvCryptoPrice.text = crypto.data[position].p.toString()
            setOnClickListener {
                onItemClickListener?.let {
                    it(crypto)
                }
            }
        }
    }

    private var onItemClickListener:((CrypoMarketMainResponse) -> Unit)? = null

    fun setOnItemClickListener(listener:(CrypoMarketMainResponse) -> Unit){
        onItemClickListener = listener
    }
}