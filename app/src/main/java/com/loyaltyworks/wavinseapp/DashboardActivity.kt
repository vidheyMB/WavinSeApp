package com.loyaltyworks.wavinseapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import com.loyaltyworks.wavinseapp.ui.notification.HistoryNotificationActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper

class DashboardActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    public var destinationId: Int = 0

    lateinit var notification: MenuItem
    lateinit var filter: MenuItem

    lateinit var menu_name_txt: TextView
    lateinit var menu_loyaty_id: TextView
    lateinit var menu_mem_mail: TextView
    lateinit var version_num: TextView

    val productViewModel: ProductViewModel by viewModels {
        ViewModelFactory((this.application as ApplicationClass).repository)
    }


    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // drawer menu textview
        val headerView: View = navView.getHeaderView(0)
        menu_name_txt = headerView.findViewById<TextView>(R.id.menu_name_txt) as TextView
        menu_loyaty_id = headerView.findViewById<TextView>(R.id.menu_loyaty_id) as TextView
        menu_mem_mail = headerView.findViewById<TextView>(R.id.menu_mem_mail) as TextView
        version_num = headerView.findViewById<TextView>(R.id.version_num) as TextView

        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            Log.d(javaClass.name, "onCreate: VERSION CODE" + packageInfo.versionCode)
            version_num.setText("v 1.0." + packageInfo.versionCode)
        } catch (e: Exception) {
        }


        menu_name_txt.text =
            PreferenceHelper.getLoginDetails(this)?.UserList!![0].UserName!!.toString()
        menu_loyaty_id.text =
            PreferenceHelper.getLoginDetails(this)?.UserList!![0].Name!!.toString()
        menu_mem_mail.text = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Email?.toString()


        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            destinationId = destination.id


            when (destination.id) {
                R.id.nav_home -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    // make visible only is condition passes
                    if (this::notification.isInitialized) {
                        notification.isVisible = true // make visible only is condition passes
                        filter.isVisible = false
                    }
                }

                R.id.retailerOrderRequestFragment -> {
                    notification.isVisible = false
                    filter.isVisible = true
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

                }

                R.id.redemptionStatusFragment -> {
                    notification.isVisible = false
                    filter.isVisible = false
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    // make visible only is condition passes
                }

                R.id.UserMappinActivity -> {
                    notification.isVisible = false
                    filter.isVisible = false
                }
                R.id.plumberRetailerQueryFragment -> {
                    notification.isVisible = false
                    filter.isVisible = false
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.logout -> {

                }


            }


        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        notification = menu.findItem(R.id.notification)
        filter = menu.findItem(R.id.filter)

        // make visible only is condition passes
        notification.isVisible = destinationId == R.id.nav_home
        filter.isVisible = destinationId == R.id.retailerOrderRequestFragment

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notification -> {
                val intent = Intent(this, HistoryNotificationActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
                return true

            }

        }
        return super.onOptionsItemSelected(item)

    }


}