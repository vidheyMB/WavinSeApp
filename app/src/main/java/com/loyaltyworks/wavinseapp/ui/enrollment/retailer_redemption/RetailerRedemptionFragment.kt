package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_redemption

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustomerJsons
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_redemption.adapter.RetailerRedemptionsAdapter
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.DatePickerBox
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.MyRedemptionRequest
import com.loyaltyworks.wavinseapp.model.StatusSpiner
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_redemption.adapter.GenderAdapter
import kotlinx.android.synthetic.main.common_date_filter.filter_card_view
import kotlinx.android.synthetic.main.common_date_filter.fromdate_tv
import kotlinx.android.synthetic.main.common_date_filter.todate_tv
import kotlinx.android.synthetic.main.common_filter.*
import kotlinx.android.synthetic.main.retailer_redemption_fragment.*
import java.util.ArrayList

class RetailerRedemptionFragment : Fragment() {


    private lateinit var viewModel: RetailerRedemptionViewModel

    var FromDate = ""
    var ToDate = ""
    var statusFilter: Int = -1
    var userID: Int = -1
    var count: Int = 0

    var StatusSpinnerList: ArrayList<StatusSpiner> = ArrayList<StatusSpiner>()

    private var lstCustomerJsons: ArrayList<LstCustomerJsons>?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RetailerRedemptionViewModel::class.java)
        return inflater.inflate(R.layout.retailer_redemption_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            lstCustomerJsons = arguments?.getSerializable("GENERAL_DETAIL") as ArrayList<LstCustomerJsons>
            if(lstCustomerJsons!=null){
                userID = lstCustomerJsons!![0].UserId!!
            }

        }

        CallServiceApi(statusFilter, "", "")

        fromdate_tv.setOnClickListener {
            DatePickerBox.date(activity) {
                fromdate_tv.text = it
                FromDate = it
                try {
                    DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                        if (!it) {
                            FromDate = ""
                            fromdate_tv.text = ""
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }

        todate_tv.setOnClickListener {
            DatePickerBox.date(activity) {
                todate_tv.text = it
                ToDate = it
                DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                    if (!it) {
                        ToDate = ""
                        todate_tv.text = ""
                    }
                }
            }
        }


        filter_card_view.setOnClickListener { v ->

            if (!TextUtils.isEmpty(FromDate) && !TextUtils.isEmpty(ToDate)) {
                FromDate = fromdate_tv.text.toString()
                ToDate = todate_tv.text.toString()
                CallServiceApi(
                    statusFilter,
                    AppController.dateAPIFormats(FromDate).toString(),
                    AppController.dateAPIFormats(ToDate).toString()
                )
            } else if (statusFilter != -1) {
                CallServiceApi(
                    statusFilter,
                    AppController.dateAPIFormats(FromDate).toString(),
                    AppController.dateAPIFormats(ToDate).toString()
                )
            } else
                Toast.makeText(activity, "Please select date range", Toast.LENGTH_SHORT).show()


            /*( activity as MyActivity).snackBar("Please select date range", R.color.green)*/
        }

//        rv_my_redemption.adapter = MyRedemptionAdapter()


        SpinnerSetUp()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LoadingDialogue.showDialog(requireContext())
        observer()

    }


    private fun SpinnerSetUp() {


        val defaultstatus = StatusSpiner()
        defaultstatus.id = -1;
        defaultstatus.name = "Select";

        val genderSpinner1 = StatusSpiner()
        genderSpinner1.id = 1
        genderSpinner1.name = "Product"

        val genderSpinner2 = StatusSpiner()
        genderSpinner2.id = 4
        genderSpinner2.name = "Voucher"

//        val genderSpinner3 = StatusSpiner()
//        genderSpinner3.id = 5
//        genderSpinner3.name = "Bank Transfer"
//
//        val genderSpinner4 = StatusSpiner()
//        genderSpinner4.id = 6
//        genderSpinner4.name = "Wallet Transfer"

        StatusSpinnerList.add(defaultstatus)
        StatusSpinnerList.add(genderSpinner1)
        StatusSpinnerList.add(genderSpinner2)
//        StatusSpinnerList.add(genderSpinner3)
//        StatusSpinnerList.add(genderSpinner4)

        select_status.adapter = GenderAdapter(requireContext(), android.R.layout.simple_spinner_item, StatusSpinnerList)

        select_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                statusFilter = (parent?.getItemAtPosition(position) as StatusSpiner).id!!

                Log.d("fjhsdhfjds", statusFilter.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }


    private fun CallServiceApi(FilterID: Int, fromDate: String, toDate: String) {

        LoadingDialogue.showDialog(requireContext())

        val myRedemptionRequest = MyRedemptionRequest()

        myRedemptionRequest.ActionType = "54"
        myRedemptionRequest.ActorId = userID.toString()

        if (!fromDate.isNullOrEmpty() && !toDate.isNullOrEmpty()) {
            myRedemptionRequest.JFromDate = fromDate
            myRedemptionRequest.JToDate = toDate
        }

        myRedemptionRequest.RedemptionTypeId = FilterID

        viewModel.setMyRedemptionListingRequest(myRedemptionRequest)

    }

    private fun observer() {

        viewModel.myRedemptionLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.LstGiftCardIssueDetailsJson.isNullOrEmpty()) {
                rv_my_redemption.adapter = RetailerRedemptionsAdapter(it)
                rv_my_redemption.visibility = View.VISIBLE
                no_data_fount.visibility = View.GONE

            } else {
                rv_my_redemption.visibility = View.GONE
                no_data_fount.visibility = View.VISIBLE
            }
        })
    }


}