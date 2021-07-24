package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_earning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustomerJsons
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_earning.adapter.RetailerEarningAdapter
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.DatePickerBox
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.MyEarningRequest
import kotlinx.android.synthetic.main.common_date_filter.*
import kotlinx.android.synthetic.main.retailer_earning_fragment.*

class RetailerEarningFragment : Fragment(){

    private lateinit var earningViewModel: RetailerEarningViewModel
    var userID: Int = -1
    var MerchantID: Int = -1
    var FromDate = ""
    var ToDate = ""

    private var lstCustomerJsons: ArrayList<LstCustomerJsons>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        earningViewModel = ViewModelProvider(this).get(RetailerEarningViewModel::class.java)
        return inflater.inflate(R.layout.retailer_earning_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            lstCustomerJsons = arguments?.getSerializable("GENERAL_DETAIL") as ArrayList<LstCustomerJsons>
            if(lstCustomerJsons!=null){
                userID = lstCustomerJsons!![0].UserId!!
                MerchantID = lstCustomerJsons!![0].MerchantId!!
            }

        }

        LoadingDialogue.showDialog(requireContext())
        observer()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CallServiceApi("", "")

        fromdate_tv.setOnClickListener {
            if (BlockMultipleClick.click())
                return@setOnClickListener

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
            if (BlockMultipleClick.click())
                return@setOnClickListener
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
                    AppController.dateAPIFormats(FromDate).toString(),
                    AppController.dateAPIFormats(ToDate).toString()
                )
            } else
                Toast.makeText(activity, "Please select date range", Toast.LENGTH_SHORT).show()


            /*( activity as MyActivity).snackBar("Please select date range", R.color.green)*/
        }
    }


    private fun CallServiceApi(fromDate: String, toDate: String) {

        earningViewModel.setEarningListingRequest(
            MyEarningRequest(
                ActorId = userID.toString(),
                IsActive = "true",
                MerchantId = MerchantID.toString(),
                JFromDate = fromDate,
                JToDate = toDate
            )
        )
    }

    private fun observer() {

        earningViewModel.myEarningLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.lstRewardTransJsonDetails.isNullOrEmpty()) {
                rv_my_earning.adapter = RetailerEarningAdapter(it)
                rv_my_earning.visibility = View.VISIBLE
                earning_error.visibility = View.GONE
            } else {
                rv_my_earning.visibility = View.GONE
                earning_error.visibility = View.VISIBLE
            }
        })
    }


}