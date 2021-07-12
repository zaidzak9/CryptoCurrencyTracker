package com.zaidzakir.cryptocurrencytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoCoinMetaData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.MetaData
import com.zaidzakir.cryptocurrencytracker.ui.CryptoTrackerViewModel
import com.zaidzakir.cryptocurrencytracker.util.Constants
import kotlinx.android.synthetic.main.latest_crypto_info.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *Created by Zaid Zakir
 */
class LatestCryptoInfoAdapter : RecyclerView.Adapter<LatestCryptoInfoAdapter.CryptoViewHolder>(), Filterable {

    inner class CryptoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    lateinit var coinFilterList: MutableList<CoinData>


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
        coinFilterList = differ.currentList
        holder.itemView.apply {
            Glide.with(this).load(Constants.cryptoMetaData[position].image).into(ivCryptoImage)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                println("filter : ${constraint.toString()}")
                val charSearch = constraint.toString()
                if (charSearch.isNotEmpty()) {
                    println("filter charSearch.isNotEmpty()")
                    val resultList = ArrayList<CoinData>()
                    for (row in coinFilterList) {
                        if (row.n?.toLowerCase()?.contains(charSearch.toLowerCase()) == true) {
                            println("filter : ${row.n}")
                            resultList.add(row)
                        }
                    }
                    coinFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = coinFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                println("filter publishResults: ${results?.values}")
                    coinFilterList = results?.values as MutableList<CoinData>
                    differ.submitList(coinFilterList)
            }

        }
    }
}