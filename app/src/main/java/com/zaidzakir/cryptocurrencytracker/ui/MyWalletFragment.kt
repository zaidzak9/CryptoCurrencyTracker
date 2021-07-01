package com.zaidzakir.cryptocurrencytracker.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zaidzakir.cryptocurrencytracker.R
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class MyWalletFragment : Fragment(R.layout.fragment_my_wallet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "My Wallet"
    }
}