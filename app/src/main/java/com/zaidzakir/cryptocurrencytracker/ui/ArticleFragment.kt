package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article.*

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {
    val args: ArticleFragmentArgs by navArgs()
    private val cryptoTrackerViewModel: CryptoTrackerViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        webViewArticle.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        btnArticleReturn.setOnClickListener {
            findNavController().navigate(
                R.id.action_articleFragment_to_newsHomeFragment
            )
        }

        saveNews.setOnClickListener {
            cryptoTrackerViewModel.saveNews(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }

    }
}