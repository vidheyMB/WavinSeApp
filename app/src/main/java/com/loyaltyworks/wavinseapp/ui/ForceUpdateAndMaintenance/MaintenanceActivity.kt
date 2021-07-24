package com.loyaltyworks.wavinseapp.ui.ForceUpdateAndMaintenance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.loyaltyworks.wavinseapp.R
import kotlinx.android.synthetic.main.activity_maintenance.*

class MaintenanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        exit.setOnClickListener { v: View? ->
            onBackPressed()
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}