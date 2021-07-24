package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter.SupportAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_redemption.adapter.GenderAdapter
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.activity_enrollment.*
import kotlinx.android.synthetic.main.retailer_support_fragment.*
import java.util.ArrayList

class RetailerSupportFragment : Fragment(), SupportAdapter.OnClickCallBack  {

    var userID: Int = -1
    var actionType: Int = -1

    var genderList: ArrayList<StatusSpiner> = ArrayList<StatusSpiner>()

    var FromDate = ""
    var ToDate = ""
    var topicFilter: Int = -1
    var statusFilter: Int = -1

    var retilerDetails: LstCustParentChildMapping? = null

    private lateinit var viewModel: RetailerSupportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RetailerSupportViewModel::class.java)
        return inflater.inflate(R.layout.retailer_support_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.topicListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.GetHelpTopicsResult.objHelpTopicList != null) {

                val objHelptopicList = ArrayList(it.GetHelpTopicsResult.objHelpTopicList)

                objHelptopicList.add(
                    0,
                    ObjHelpTopicList(
                        "",
                        false,
                        0,
                        -1,
                        "Select topic",
                        false,
                        -1,
                        "Select topic",
                        0
                    )
                )

                select_topic.adapter = CustomSpinnersAdapter2(requireContext(), objHelptopicList)

            }
        })

        viewModel.queryListLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.objCustomerAllQueryJsonList.isNullOrEmpty()) {
                query_list_recycler?.adapter = SupportAdapter(it, this)
                query_list_recycler?.visibility = View.VISIBLE
//                query_filterDisplay?.visibility = View.VISIBLE
                query_error_hint?.visibility = View.GONE

                // navigate to chat page
                val bundles = this.arguments
                if (bundles != null) {
                    val chatId = arguments?.getInt("ChatID")
                    // clear arguments after data fetch
                    arguments?.clear()
                    if (chatId != null) {
                        it.objCustomerAllQueryJsonList.forEachIndexed { index, objCustomerAllQueryList ->
                            if (chatId == objCustomerAllQueryList.CustomerTicketID) {
                                val bundle = Bundle()
                                bundle.putSerializable(
                                    "SUPPORT_DATA",
                                    it.objCustomerAllQueryJsonList.get(index)
                                )
                                view?.findNavController()?.navigate(
                                    R.id.action_retailerSupportFragment_to_queryChatFragment,
                                    bundle
                                )
                            }
                        }

                    }
                }
                LoadingDialogue.dismissDialog()

            } else {
                LoadingDialogue.dismissDialog()
                query_list_recycler?.visibility = View.GONE
//                query_filterDisplay?.visibility = View.GONE
                query_error_hint?.visibility = View.VISIBLE
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SpinnerSetUp()

        val bundle = this.arguments
        actionType = 0;
        userID = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId!!
        (activity as EnrollmentActivity).toolbar.title = "Support"

        LoadingDialogue.showDialog(requireContext())
        getqueryListing(topicFilter, statusFilter)


        viewModel.getHelpTopicListLiveData(
            HelpTopicRetrieveRequest(
                GetHelpTopicRetrieveRequest(
                    "4", PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(), "true"
                )
            )
        )



        filterBtn.setOnClickListener { v ->

            /* if (!TextUtils.isEmpty(FromDate) && !TextUtils.isEmpty(ToDate)) {
                 FromDate = fromDate_tv.text.toString()
                 ToDate = toDate_tv.text.toString()
                 getQueryListing(topicFilter, statusFilter, AppController.dateAPIFormats(FromDate).toString(), AppController.dateAPIFormats(ToDate).toString()
                 )
             } else */if (topicFilter != -1 || statusFilter != -1) {
            getqueryListing(
                topicFilter, statusFilter,
                /*  AppController.dateAPIFormats(FromDate).toString(),
                  AppController.dateAPIFormats(ToDate).toString()*/
            )
        } else
            Toast.makeText(requireContext(), "Please select one filter", Toast.LENGTH_SHORT).show()


        }

        lodge_query_img.setOnClickListener {

            findNavController().navigate(R.id.action_retailerSupportFragment_to_newTicketFragment)

        }

    }

    private fun getqueryListing(topicFilter: Int, statusFilter: Int) {

        viewModel.getQueryListLiveData(
            QueryListingRequest(
                "1", userID.toString(), statusFilter.toString(), topicFilter.toString()
            )
        )

    }


    private fun SpinnerSetUp() {


        if (genderList.size > 0)
            genderList.clear()

        val defaultstatus = StatusSpiner()
        defaultstatus.id = -1;
        defaultstatus.name = "Select status";

        val genderSpinner1 = StatusSpiner()
        genderSpinner1.id = 1
        genderSpinner1.name = "Pending"

        val genderSpinner2 = StatusSpiner()
        genderSpinner2.id = 2
        genderSpinner2.name = "Re-Open"

        val genderSpinner3 = StatusSpiner()
        genderSpinner3.id = 3
        genderSpinner3.name = "Resolved"

        val genderSpinner4 = StatusSpiner()
        genderSpinner4.id = 4
        genderSpinner4.name = "Closed"

        val genderSpinner5 = StatusSpiner()
        genderSpinner5.id = 5
        genderSpinner5.name = "Resolved"

        genderList.add(defaultstatus)
        genderList.add(genderSpinner1)
        genderList.add(genderSpinner2)
        genderList.add(genderSpinner3)
        genderList.add(genderSpinner4)
        genderList.add(genderSpinner5)

        select_status.adapter = GenderAdapter(requireContext(), android.R.layout.simple_spinner_item, genderList)


        select_topic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                topicFilter =
                    (parent?.getItemAtPosition(position) as ObjHelpTopicList).HelpTopicId!!

                Log.d("fjhsdhfjds", topicFilter.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        select_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                statusFilter = (parent?.getItemAtPosition(position) as StatusSpiner).id!!


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }


    override fun onQueryListItemClickResponse(
        itemView: View,
        position: Int,
        support: List<ObjCustomerAllQueryList?>?
    ) {
        val bundle = Bundle()
        bundle.putSerializable("SUPPORT_DATA", support?.get(position))
        bundle.putInt("ActionId", actionType)
        itemView.findNavController()
            .navigate(R.id.action_retailerSupportFragment_to_queryChatFragment, bundle)
    }

}