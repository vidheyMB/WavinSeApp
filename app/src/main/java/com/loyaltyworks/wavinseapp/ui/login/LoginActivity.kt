package com.loyaltyworks.wavinseapp.ui.login

import android.os.Bundle
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity

class LoginActivity : BaseActivity() {
    override fun callInitialServices() {

    }

    override fun callObservers() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}