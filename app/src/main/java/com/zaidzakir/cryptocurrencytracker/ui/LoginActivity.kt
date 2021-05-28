package com.zaidzakir.cryptocurrencytracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaidzakir.cryptocurrencytracker.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginKeyButton.setOnClickListener {
            val gotoMainActivity = Intent(this,MainActivity::class.java)
            startActivity(gotoMainActivity)
        }
    }
}