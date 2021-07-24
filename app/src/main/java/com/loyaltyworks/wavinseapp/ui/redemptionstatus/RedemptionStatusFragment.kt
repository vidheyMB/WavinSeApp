package com.loyaltyworks.wavinseapp.ui.redemptionstatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.CommonSpinner
import com.loyaltyworks.wavinseapp.model.ObjCatalogueDetailss
import com.loyaltyworks.wavinseapp.model.RedemptionsStatusRequest
import com.loyaltyworks.wavinseapp.ui.redemptionstatus.adapter.RedemptionStatusAdapter
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.DatePickerBox
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_redemption_status.*
import kotlinx.android.synthetic.main.fragment_redemption_status.fromDateEdt
import kotlinx.android.synthetic.main.fragment_redemption_status.toDateEdt
import java.util.*


class RedemptionStatusFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var viewModel: RedemptionsStatusViewModel

    var redemptionStatusAdapter: RedemptionStatusAdapter? = null

    var FromDate = ""
    var ToDate = ""

    var mSelectedVendor = -1
    var mSelectedStatus = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(RedemptionsStatusViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_redemption_status, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fromDateEdt.setOnClickListener(this)
        toDateEdt.setOnClickListener(this)
        filterBtn.setOnClickListener(this)

        status_spinner.onItemSelectedListener = this
        vendor_spinner.onItemSelectedListener = this

        setRedemStatusList()

    }

    private fun setRedemStatusList() {


        val mStatusList: ArrayList<CommonSpinner> = ArrayList<CommonSpinner>()

        val status1 = CommonSpinner()
        status1.id = -1
        status1.name = "Redemption Status"

        val status2 = CommonSpinner()
        status2.id = 0
        status2.name = "Pending"

        val status3 = CommonSpinner()
        status3.id = 2
        status3.name = "Processed"

        val status4 = CommonSpinner()
        status4.id = 4
        status4.name = "Delivered"

        val status5 = CommonSpinner()
        status5.id = 3
        status5.name = "Cancelled"

        /*New status added for Redemption Status from here*/

        /*New status added for Redemption Status from here*/
        val status6 = CommonSpinner()
        status6.id = 7
        status6.name = "Returned"

        val status7 = CommonSpinner()
        status7.id = 8
        status7.name = "Redispatched"

        val status8 = CommonSpinner()
        status8.id = 9
        status8.name = "OnHold"

        val status9 = CommonSpinner()
        status9.id = 10
        status9.name = "Dispatched"

        val status10 = CommonSpinner()
        status10.id = 11
        status10.name = "Out for Delivery"

        val status11 = CommonSpinner()
        status11.id = 12
        status11.name = "Address Verified"

        mStatusList.add(status1)
        mStatusList.add(status2)
        mStatusList.add(status3)
        mStatusList.add(status4)
        mStatusList.add(status5)
        mStatusList.add(status6)
        mStatusList.add(status7)
        mStatusList.add(status8)
        mStatusList.add(status9)
        mStatusList.add(status10)
        mStatusList.add(status11)

        status_spinner.adapter = CommonAdapter(
            requireContext(),
            R.layout.spinner_row_small_size,
            mStatusList
        )


        val list = ArrayList<CommonSpinner>()
        val defaultSp = CommonSpinner()
        defaultSp.name = "Redemption Type"
        defaultSp.id = -1

        val defaultSp1 = CommonSpinner()
        defaultSp1.name = "Catalogue"
        defaultSp1.id = 1

        val defaultSp2 = CommonSpinner()
        defaultSp2.name = "Voucher"
        defaultSp2.id = 4

        val defaultSp3 = CommonSpinner()
        defaultSp3.name = "Dream Gift"
        defaultSp3.id = 3

        list.add(0, defaultSp)
        list.add(defaultSp1)
        list.add(defaultSp2)
        list.add(defaultSp3)

        vendor_spinner.adapter = CommonAdapter(
            requireContext(),
            R.layout.spinner_row_small_size,
            list
        )

        loadRedeemList()
    }

    private fun loadRedeemList() {

     /*   if (FromDate.isNotEmpty() || ToDate.isNotEmpty()) {
            if (FromDate.isEmpty() || ToDate.isEmpty()) {
                Toast.makeText(requireContext(),"Both from date and to date should be selected.", Toast.LENGTH_SHORT).show()
                return
            }
        }
*/
        LoadingDialogue.showDialog(requireContext())

        val redemptionStatusRequest = RedemptionsStatusRequest()

        redemptionStatusRequest.ActionType = "52"
        redemptionStatusRequest.ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString()
        redemptionStatusRequest.StartIndex = "1"

        val objCatalogueDetails =  ObjCatalogueDetailss()

        if (!FromDate.isNullOrEmpty())
            objCatalogueDetails.JFromDate = AppController.dateFormat(FromDate)

        if (!ToDate.isNullOrEmpty())
            objCatalogueDetails.JToDate = AppController.dateFormat(ToDate)

        objCatalogueDetails.MerchantId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].MerchantId.toString()
        objCatalogueDetails.RedemptionTypeId = mSelectedVendor.toString()
        objCatalogueDetails.SelectedStatus = mSelectedStatus.toString()

        redemptionStatusRequest.ObjCatalogueDetails = objCatalogueDetails

        viewModel.getRedemptionStatusList(redemptionStatusRequest)


        viewModel.getRedemptionStatusLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                LoadingDialogue.dismissDialog()
                if (it != null && !it.ObjCatalogueRedemReqList.isNullOrEmpty()) {

                    error_txt.visibility = View.GONE
                    commonRecycler.visibility = View.VISIBLE

                    redemptionStatusAdapter = RedemptionStatusAdapter(it.ObjCatalogueRedemReqList)
                    commonRecycler.adapter = redemptionStatusAdapter


                } else {

                    error_txt.visibility = View.VISIBLE
                    commonRecycler.visibility = View.GONE

                }

            })


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val selectedId: Int = (parent!!.getItemAtPosition(position) as CommonSpinner).id!!

        when (parent.id) {

            R.id.status_spinner -> {
                mSelectedStatus = selectedId
            }

            R.id.vendor_spinner -> {
                mSelectedVendor = selectedId
            }

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.filterBtn -> {
                if (FromDate.isNotEmpty() && ToDate.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "To date should not be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else if (FromDate.isEmpty() && ToDate.isNotEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "From date should not be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                loadRedeemList()
            }

            R.id.fromDateEdt -> {

                DatePickerBox.date(activity) {
                    fromDateEdt.text = it
                    FromDate = it
                    fromDateEdt.setTextColor(requireContext().resources.getColor(R.color.light_dark))
                    DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                        if (!it) {
                            FromDate = ""
                            fromDateEdt.text = "From Date"
                            fromDateEdt.setTextColor(requireContext().resources.getColor(R.color.textColorSecondary))
                        }
                    }
                }
            }

            R.id.toDateEdt -> {
                DatePickerBox.date(activity) {
                    toDateEdt.text = it
                    ToDate = it
                    toDateEdt.setTextColor(requireContext().resources.getColor(R.color.light_dark))
                    DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                        if (!it) {
                            ToDate = ""
                            toDateEdt.text = "To Date"
                            toDateEdt.setTextColor(requireContext().resources.getColor(R.color.textColorSecondary))
                        }
                    }
                }

            }


        }


    }


}