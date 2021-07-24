package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustomerJsons
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.utils.Count.Companion.setCounting
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel

class ProductActivity : BaseActivity() {


    private val viewModel: ProductViewModel by viewModels {
        ViewModelFactory((application as ApplicationClass).repository)
    }

    lateinit var notification: MenuItem

    lateinit var navController: NavController

    private var destinationId: Int = 0

    var customerList: ArrayList<LstCustomerJsons>? = null


    override fun callInitialServices() {

    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)

        // getting the bundle back from the android
        val bundle = intent.extras
        customerList = bundle!!.getSerializable("SELECT_MEMBER") as ArrayList<LstCustomerJsons>


        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            destinationId = destination.id
            when (destination.id) {
                R.id.productFragment -> {

                    if (this::notification.isInitialized) {
                        notification.isVisible = true
                    }
                    //  Add back press arrow to code on home fragment
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setDisplayShowHomeEnabled(true)
                }
                R.id.productDetailsFragment -> {
                    notification.isVisible = true


                }
                R.id.myCartFragment -> {
                    notification.isVisible = false


                }
                else -> {
                    notification.isVisible = false

                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)


        // Check before back press based on fragment
        if (navController.currentDestination?.id == R.id.productFragment)
            onBackPressed()
        else return navController.navigateUp()

        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.product_menu_cart, menu)
        notification = menu.findItem(R.id.cart)
        notification.isEnabled = true

        viewModel.allProducts.observe(this, Observer {
            setBadgeCount(it.size.toString())
        })


        if (destinationId == R.id.myCartFragment)
            notification.isVisible = false


//        notification.isVisible = true

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.cart -> {
                navController.navigate(R.id.myCartFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun setBadgeCount(count: String?) {
        val icon = notification.icon as LayerDrawable
        setCounting(this, icon, count!!)
    }


}