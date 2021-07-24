package com.loyaltyworks.wavinseapp.ui.enrollment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.CustomerDetailsResponseJson
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity


class EnrollmentActivity : BaseActivity() {


    var customerList: CustomerDetailsResponseJson? = null

    public var destinationId: Int = 0


    lateinit var AddDreamGift: MenuItem
    lateinit var mToolTip: MenuItem


    override fun callInitialServices() {

    }

    override fun callObservers() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enrollment)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val drawable = toolbar.navigationIcon
//        drawable!!.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)

        // getting the bundle back from the android
        val bundle = intent.extras

        val toolbarTitle: String = bundle!!.getString("Title").toString()

        if (toolbarTitle.equals("Verification", true)) {
            customerList = bundle.getSerializable("SELECT_MEMBER") as CustomerDetailsResponseJson
        }

        supportActionBar!!.title = toolbarTitle


        val navController = findNavController(R.id.nav_host_selected_retailer_detail_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            destinationId = destination.id

            when (destination.id) {
                R.id.nav_home -> {

                }

                R.id.selectedRetailerDetailFragment -> {

                    //  Add back press arrow to code on home fragment
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setDisplayShowHomeEnabled(true)

                }

                R.id.retailerOrderRequestFragment -> {
                    // make visible only is condition passes

                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.enrollment_menu, menu)

        AddDreamGift = menu.findItem(R.id.add_dreamgift)
        mToolTip = menu.findItem(R.id.ic_pop)

        AddDreamGift.isVisible = destinationId == R.id.reatilerDreamGiftFragment
        mToolTip.isVisible = destinationId == R.id.reatilerDreamGiftFragment

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_selected_retailer_detail_fragment)
        // Check before back press based on fragment
        if (navController.currentDestination?.id == R.id.selectedRetailerDetailFragment)
            onBackPressed()
        else return navController.navigateUp()

        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}