package com.loyaltyworks.wavinseapp.ui.usermapping

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustParentChildMapping
import com.loyaltyworks.wavinseapp.model.UserMappedParentRequest
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity
import com.loyaltyworks.wavinseapp.ui.usermapping.adapter.UserMappingAdapter
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.DatePickerBox
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.internet.Internet
import kotlinx.android.synthetic.main.activity_user_mapping.*
import kotlinx.android.synthetic.main.filter_layout.*
import java.util.*

class UserMappingActivity : BaseActivity(), UserMappingAdapter.OnItemClickListener,
    View.OnClickListener {


    private lateinit var viewModel: UserMappingViewModel

    var UserDetails = 1
    var Enrollment = 2


    private var name = ""
    private var mobile = ""

    private var fromDate = ""
    private var toDate = ""

    private var selectedActionType = 1

    var enrollmentDisplayed = false


    var summeryReportss = ArrayList<LstCustParentChildMapping>()


    override fun callInitialServices() {
        getData(
            UserDetails,
            PreferenceHelper.getLoginDetails(this)!!.UserList!![0].UserId.toString()
        )
    }

    override fun callObservers() {
        ObserverSetUp()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_mapping)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        fromDateEdt.setOnClickListener(this)
        toDateEdt.setOnClickListener(this)
        filterBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)

        viewModel = ViewModelProvider(this).get(UserMappingViewModel::class.java)

        name = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Name.toString()
        mobile = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Mobile.toString()



    }


    override fun onResume() {
        super.onResume()

    }


    fun getData(actionType: Int, UserID: String?) {

        nameText.text = "Name : \n$name"
        mobileNumber.text = "Mobile Number : \n$mobile"

        LoadingDialogue.showDialog(this)
        selectedActionType = actionType

        val userMappedRequest = UserMappedParentRequest()

        userMappedRequest.ActionType = actionType.toString()
        userMappedRequest.UserID = UserID

        if (!fromDate.isNullOrEmpty())
            userMappedRequest.JDateFrom = AppController.dateFormat(fromDate)

        if (!toDate.isNullOrEmpty())
            userMappedRequest.JDateTo = AppController.dateFormat(toDate)
        if (Internet.isNetworkConnected()) {
            viewModel.getMappedParentChildList(userMappedRequest)
        }

    }

    private fun ObserverSetUp() {


        viewModel.getMappedParentChildLiveData.observe(this, Observer {

            LoadingDialogue.dismissDialog()

            if (!it.lstCustParentChildMapping.isNullOrEmpty()) {

                if (selectedActionType == Enrollment) {
                    enrollmentDisplayed = true

                    detailsLayout.visibility = View.VISIBLE
                } else {
                    detailsLayout.visibility = View.GONE
                }

                recyclerViewv.visibility = View.VISIBLE
                error_txtt.visibility = View.GONE

                recyclerViewv.adapter = UserMappingAdapter(
                    it.lstCustParentChildMapping,
                    selectedActionType,
                    this
                )

            } else {
                detailsLayout.visibility = View.GONE
                recyclerViewv.visibility = View.GONE
                error_txtt.visibility = View.VISIBLE
            }

        })

    }

    override fun onRewardItemClickListenerCallback(
        position: Int,
        allGroups: List<LstCustParentChildMapping?>?,
        actionType: String
    ) {

        if (actionType == Enrollment.toString()) {
            detailsLayout.visibility = View.VISIBLE
            userName.text = allGroups!![position]!!.FirstName
            enrollCount.text = allGroups[position]!!.TotalEnrollCount.toString()
        } else {
            detailsLayout.visibility = View.GONE
        }

        val gson = Gson()

        Log.d("jfkdsl", gson.toJson(allGroups))

        summeryReportss.add(allGroups!![position]!!)
        name = allGroups[position]!!.FirstName!!
        mobile = allGroups[position]!!.Mobile!!
        getData(actionType.toInt(), allGroups[position]!!.MasterCustomerUserId.toString())

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBackPressed() {


        if (summeryReportss.size > 0) {
            summeryReportss.removeAt(summeryReportss.size - 1)
        }

        if (enrollmentDisplayed) {
            if (summeryReportss.size > 0) {
                name = summeryReportss[summeryReportss.size - 1].FirstName!!
                mobile = summeryReportss[summeryReportss.size - 1].Mobile!!
                if (Internet.isNetworkConnected()) {
                    getData(
                        UserDetails,
                        summeryReportss[summeryReportss.size - 1].MasterCustomerUserId?.toString()
                    )
                }
            } else {
                if (Internet.isNetworkConnected()) {
                    name = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Name.toString()
                    mobile = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Mobile.toString()
                    getData(
                        UserDetails,
                        PreferenceHelper.getLoginDetails(this)!!.UserList!![0].UserId.toString()
                    )
                }
            }
            enrollmentDisplayed = false
            return
        }

        if (summeryReportss.size > 0) {
            if (Internet.isNetworkConnected()) {
                name = summeryReportss[summeryReportss.size - 1].FirstName!!
                mobile = summeryReportss[summeryReportss.size - 1].Mobile!!
                getData(
                    UserDetails,
                    summeryReportss[summeryReportss.size - 1].MasterCustomerUserId?.toString()
                )
            }
        } else {
            if (mobile == PreferenceHelper.getLoginDetails(this)?.UserList!![0].Mobile.toString())
                super.onBackPressed()
            else {
                if (Internet.isNetworkConnected()) {
                    name = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Name.toString()
                    mobile = PreferenceHelper.getLoginDetails(this)?.UserList!![0].Mobile.toString()
                    getData(
                        UserDetails,
                        PreferenceHelper.getLoginDetails(this)!!.UserList!![0].UserId.toString()
                    )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tab1_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> filterShow()
        }
        return super.onOptionsItemSelected(item)
    }

    fun filterShow() {
        if (filterDisplay.visibility == View.VISIBLE) {
            filterDisplay.animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
            filterDisplay.visibility = View.GONE
        } else if (filterDisplay.visibility == View.GONE) {
            filterDisplay.visibility = View.VISIBLE
            filterDisplay.animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        }
    }

    override fun onClick(v: View?) {


        when (v!!.id) {

            R.id.fromDateEdt -> {

                DatePickerBox.date(this) {
                    fromDateEdt.text = it
                    fromDate = it
                    fromDateEdt.setTextColor(resources.getColor(R.color.light_dark))
                    DatePickerBox.dateCompare(this, fromDate, toDate) {
                        if (!it) {
                            fromDate = ""
                            fromDateEdt.text = "From Date"
                            fromDateEdt.setTextColor(resources.getColor(R.color.textColorSecondary))
                        }
                    }
                }

            }

            R.id.toDateEdt -> {
                DatePickerBox.date(this) {
                    toDateEdt.text = it
                    toDate = it
                    toDateEdt.setTextColor(resources.getColor(R.color.light_dark))
                    DatePickerBox.dateCompare(this, fromDate, toDate) {
                        if (!it) {
                            toDate = ""
                            toDateEdt.text = "To Date"
                            toDateEdt.setTextColor(resources.getColor(R.color.textColorSecondary))
                        }
                    }
                }

            }

            R.id.filterBtn -> {
                if (fromDate.isNotEmpty() && toDate.isNotEmpty()) {
                    getData(
                        UserDetails,
                        PreferenceHelper.getLoginDetails(this)!!.UserList!![0].UserId.toString()
                    )
                    filterShow()
                } else Toast.makeText(this, "Please select date range", Toast.LENGTH_SHORT).show()

            }

            R.id.resetBtn -> {
                fromDate = ""
                toDate = ""
                getData(
                    UserDetails,
                    PreferenceHelper.getLoginDetails(this)!!.UserList!![0].UserId.toString()
                )
                fromDateEdt.text = "From Date"
                toDateEdt.text = "To Date"
                filterShow()

            }

        }

    }


}
