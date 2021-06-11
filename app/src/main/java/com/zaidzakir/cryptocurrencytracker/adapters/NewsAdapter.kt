package com.zaidzakir.cryptocurrencytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaidzakir.cryptocurrencytracker.R
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CoinData
import kotlinx.android.synthetic.main.latest_crypto_info.view.*

/**
 *Created by Zaid Zakir
 */
class NewsAdapter {
//    : RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>()
//
//    inner class NewsAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
//
//    private val differentCallback = object : DiffUtil.ItemCallback<NewsResponse>(){
//        override fun areItemsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this,differentCallback)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterViewHolder {
//        return NewsAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_article_info,parent,false))
//    }
//
//    override fun onBindViewHolder(holder: NewsAdapterViewHolder, position: Int) {
//        val newsResponse = differ.currentList[position]
//        holder.itemView.apply {
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }


}