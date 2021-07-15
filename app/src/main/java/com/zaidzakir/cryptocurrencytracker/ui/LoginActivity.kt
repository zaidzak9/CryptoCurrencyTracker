package com.zaidzakir.cryptocurrencytracker.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.cryptocurrencytracker.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val cryptoViewModel: CryptoTrackerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //loginKeyButton.isEnabled = false
        loginKeyButton.setOnClickListener {
            val gotoMainActivity = Intent(this, MainActivity::class.java)
            startActivity(gotoMainActivity)
        }

        cryptoViewModel.getSavedCryptoMetaData().observe(this, {
            if (it.isEmpty()) {
                getCryptoMetaData()
            }
        })

    }

    private fun getCryptoMetaData() {
        cryptoViewModel.getCoinMetaData()
        lifecycleScope.launchWhenStarted {
            cryptoViewModel.cryptoMarketFlow.collect { cryptoMetaResponse ->
                when (cryptoMetaResponse) {
                    is CryptoTrackerViewModel.Events.CryptoMetaSuccess -> {
                        loginKeyButton.isEnabled = true
                        cryptoMetaResponse?.let {
                            println("getCryptoMetaData was success")
                            cryptoViewModel.saveCryptoMetaData(cryptoMetaResponse.cryptoResponse)
                        }
                    }
                    is CryptoTrackerViewModel.Events.Failure -> {
                        loginKeyButton.isEnabled = false
                        println("getCryptoMetaData was failure")
                    }
                    else -> Unit
                }
            }

        }
    }
}