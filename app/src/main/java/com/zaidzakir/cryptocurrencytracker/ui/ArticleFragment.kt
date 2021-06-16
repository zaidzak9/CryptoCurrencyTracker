package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import kotlinx.android.synthetic.main.fragment_article.*

/**
 *Created by Zaid Zakir
 */
class ArticleFragment : Fragment(R.layout.fragment_article) {
    val args: ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        webViewArticle.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        btnArticleReturn.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}