package com.loyaltyworks.wavinseapp.ui.plumberretailerquery

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.CommonSpinner
import com.loyaltyworks.wavinseapp.model.ObjQueryCenterAPIInfo
import com.loyaltyworks.wavinseapp.model.SEQueryListingRequest
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter.HelpTopicSpinnersAdapter
import com.loyaltyworks.wavinseapp.ui.plumberretailerquery.adapter.PlumberRetailerQueryAdapter
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.GetHelpTopicRetrieveRequest
import com.loyaltyworks.wavinseapp.model.HelpTopicRetrieveRequest
import com.loyaltyworks.wavinseapp.model.ObjHelpTopicList
import com.loyaltyworks.wavinseapp.utils.*
import kotlinx.android.synthetic.main.common_date_filter.*
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.*
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.filter_btn
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.filter_ok_btn
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.filter_pop_up
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.filter_reset_btn
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.fromDateEdt
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.seach_fld
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query.toDateEdt
import java.util.*


class PlumberRetailerQueryFragment : Fragment(), PlumberRetailerQueryAdapter.ClickOnItemClick,  View.OnClickListener {


//    var plumberRetailerQueryAdapter: PlumberRetailerQueryAdapter? = null
    private lateinit var viewModel: PlumberRetailerQueryViewModel

    var isRefresh = true
    var isLoaded = false
    var listFull = false

    var FromDate = ""
    var ToDate = ""

    var startIndex = 1
    var pageLimit = 10

    var selectedFilterTypeId = -1
    var selectedFilterTopicId = -1
    var selectedStatusId = -1

    var selectedFilterType: CommonSpinner? = null
    var selectedFilterTopic: ObjHelpTopicList? = null
    var selectedStatus: CommonSpinner? = null


    var tempMemberList = ArrayList<ObjQueryCenterAPIInfo>()
    var currentList = ArrayList<ObjQueryCenterAPIInfo>()

    var mMemberLayoutManager: LinearLayoutManager? = null
    var plumberRetailerQueryAdapter: RecyclerView.Adapter<*>? = null

    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(PlumberRetailerQueryViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plumber_retailer_query, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val statusSpinnerList: ArrayList<CommonSpinner> = ArrayList<CommonSpinner>()
        statusSpinnerList.add(CommonSpinner("Select Status", -1))
        statusSpinnerList.add(CommonSpinner("Pending", 1))
        statusSpinnerList.add(CommonSpinner("Re-Open", 2))
        statusSpinnerList.add(CommonSpinner("Resolved", 3))
        statusSpinnerList.add(CommonSpinner("Closed", 4))
        statusSpinnerList.add(CommonSpinner("Resolved-Follow Up", 5))

        status_spinner.adapter =  CommonAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statusSpinnerList
        )

        val TypeSpinner: ArrayList<CommonSpinner> = ArrayList<CommonSpinner>()
        TypeSpinner.add(CommonSpinner("Select Type", -1))
        TypeSpinner.add(CommonSpinner("Post Reply", 0))
        TypeSpinner.add(CommonSpinner("Post Internal Query", 3))
        filter_type_spinner.adapter =
            CommonAdapter(requireContext(), android.R.layout.simple_spinner_item, TypeSpinner)

        viewModel.getHelpTopicListLiveData(
            HelpTopicRetrieveRequest(
                GetHelpTopicRetrieveRequest(
                    "4",
                    PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                    "true"
                )
            )
        )

//        setSEQueryListingRequest()

//        filter_topic_spinner.onItemSelectedListener = this
//        filter_type_spinner.onItemSelectedListener = this
//        status_spinner.onItemSelectedListener = this

        filter_topic_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedFilterTopic = parent!!.getItemAtPosition(position) as ObjHelpTopicList
                selectedFilterTopicId  = selectedFilterTopic!!.HelpTopicId!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        filter_type_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedFilterType = parent!!.getItemAtPosition(position) as CommonSpinner
                selectedFilterTypeId  = selectedFilterType!!.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = parent!!.getItemAtPosition(position) as CommonSpinner
                selectedStatusId  = selectedStatus!!.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        fromDateEdt.setOnClickListener(this)
        toDateEdt.setOnClickListener(this)
        filter_reset_btn.setOnClickListener(this)
        filter_ok_btn.setOnClickListener(this)
        filter_btn.setOnClickListener(this)
        searchBtn.setOnClickListener(this)

        // use a linear layout manager
        mMemberLayoutManager = LinearLayoutManager(requireContext())
        mMemberLayoutManager!!.isAutoMeasureEnabled = true
        mMemberLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        contactus_rv.layoutManager = mMemberLayoutManager
        contactus_rv.isNestedScrollingEnabled = true
        contactus_rv.setHasFixedSize(false)
        contactus_rv.setRecycledViewPool(RecyclerView.RecycledViewPool())
        contactus_rv.itemAnimator = DefaultItemAnimator()

        loadMemberListObserver()
        setFileterSpinners()

    }

    private fun setFileterSpinners() {

        scrollListener = object : EndlessRecyclerViewScrollListener(mMemberLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadMemberList(page + 1)
            }
        }

        contactus_rv.addOnScrollListener(scrollListener!!)

    }

    fun loadMemberList(startIndex: Int) {

        if (listFull) return

        this.startIndex = startIndex

        if (startIndex == 1)
            scrollListener!!.resetState()

  /*      val minRange = if (min_range.text.toString().isEmpty()) 0
        else min_range.text.toString().toInt()

        val maxRange = if (max_range.text.toString().isEmpty()) 0
        else max_range.text.toString().toInt()

        if (minRange > maxRange) {
            Toast.makeText(
                requireContext(),
                "Min Range shouldn't be more than Max Range.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }*/

        LoadingDialogue.showDialog(requireContext())


//        if (FromDate.isNotEmpty())
//            FromDate = AppController.dateFormat(FromDate)
//
//        if (ToDate.isNotEmpty())
//            ToDate = AppController.dateFormat(ToDate)

        val seQueryListingRequest = SEQueryListingRequest()

        seQueryListingRequest.ActionType = 1
        seQueryListingRequest.ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId
       seQueryListingRequest.DateFrom = AppController.dateFormat(FromDate)
       seQueryListingRequest.DateTo = AppController.dateFormat(ToDate)
        if(selectedFilterTopicId>0) seQueryListingRequest.HelpTopicSearchID = selectedFilterTopicId
        if(selectedFilterTypeId>0) seQueryListingRequest.QueryStatusSearchID = selectedFilterTypeId
        if(selectedStatusId>0) seQueryListingRequest.QueryStatusSearchID = selectedStatusId
        if(seach_fld.text.toString().isNotEmpty()) seQueryListingRequest.SearchText = seach_fld.text.toString()
        seQueryListingRequest.PageSize = pageLimit
        seQueryListingRequest.StartIndex = startIndex


        viewModel.getSEQueryListingData(seQueryListingRequest)


    }

    private fun loadMemberListObserver() {

        viewModel.seQueryListingResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null && !it.objQueryCenterAPIInfoList.isNullOrEmpty()) {

                contactus_rv.visibility = View.VISIBLE
                no_contactus_tv.visibility = View.GONE
//                noResult_txt.text =
//                    "Total search result : ${it!!.CustomerDetailsResponseJson!![0].TotalRows}"

                // check if search or not
                if (startIndex == 1) {
                    listFull = false
                    // 1. First, clear the array of data
                    tempMemberList.clear()
                    currentList.clear()
                    // 2. Notify the adapter of the update
                    plumberRetailerQueryAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                    isRefresh = false
                }



                for (customer in it.objQueryCenterAPIInfoList) {
                    tempMemberList.add(tempMemberList.size, customer)
                }


                currentList.clear()

                currentList.addAll(tempMemberList)


                //AppController.getInstance().showToastMSG(context,tempMemberList.size()+"");
                //this.categoryName =categoryName;
                if (!isLoaded) {
                    isLoaded = true
                    plumberRetailerQueryAdapter = PlumberRetailerQueryAdapter(currentList, this)
                    contactus_rv.adapter = plumberRetailerQueryAdapter
                } else {
                    plumberRetailerQueryAdapter!!.notifyDataSetChanged()
                }

            }else{

                if (startIndex == 1) {
                    no_contactus_tv.visibility = View.VISIBLE
                    contactus_rv.visibility = View.GONE
                    LoadingDialogue.dismissDialog()
                } else {
                    listFull = true
                }

            }
            LoadingDialogue.dismissDialog()
        })


    }


//    private fun setSEQueryListingRequest() {
//
//        var seQueryListingRequest = SEQueryListingRequest()
//
//        seQueryListingRequest.ActionType = 1
//        seQueryListingRequest.ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId
//        if(FromDate.isNotEmpty()) seQueryListingRequest.DateFrom = FromDate
//        if(ToDate.isNotEmpty()) seQueryListingRequest.DateTo = ToDate
//        if(selectedFilterTopicId>0) seQueryListingRequest.HelpTopicSearchID = selectedFilterTopicId
//        if(selectedFilterTypeId>0) seQueryListingRequest.QueryStatusSearchID = selectedFilterTypeId
//        if(seach_fld.text.toString().isNotEmpty()) seQueryListingRequest.SearchText = seach_fld.text.toString().toLong()
//        seQueryListingRequest.PageSize = 10
//        seQueryListingRequest.StartIndex = 1
//
//        viewModel.getSEQueryListingData(seQueryListingRequest)
//    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        LoadingDialogue.showDialog(requireContext())

        viewModel.topicListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null && it.GetHelpTopicsResult?.objHelpTopicList != null) {
//                val helpTypeList: MutableList<ObjHelpTopicList> = it.getHelpTopicsResult?.objHelpTopicList as MutableList<ObjHelpTopicList>
                var helpTypeList = mutableListOf<ObjHelpTopicList>()
                helpTypeList.addAll(it.GetHelpTopicsResult.objHelpTopicList)
                helpTypeList.add(
                    0, ObjHelpTopicList(
                        HelpTopicName = "Select Query Topic",
                        HelpTopicId = -1
                    )
                )
                filter_topic_spinner.adapter = HelpTopicSpinnersAdapter(
                    requireContext(),
                    helpTypeList
                )

                LoadingDialogue.dismissDialog()
            }
        })




       /* viewModel.seQueryListingResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null && !it.objQueryCenterAPIInfoList.isNullOrEmpty()) {
                plumberRetailerQueryAdapter = PlumberRetailerQueryAdapter(
                    this,
                    it.objQueryCenterAPIInfoList
                )
                contactus_rv.adapter = plumberRetailerQueryAdapter
            }else{
                no_contactus_tv.visibility = View.VISIBLE
                contactus_rv.visibility = View.GONE
            }
            LoadingDialogue.dismissDialog()
        })*/

    }
    override fun onClickOnItemClick(objQueryCenterAPIInfo: ObjQueryCenterAPIInfo) {
        if (filter_pop_up.visibility == View.VISIBLE) {
            filter_pop_up.visibility = View.GONE
            return
        }
        var bundle = Bundle()
        bundle.putSerializable("ObjQueryCenterAPIInfo",objQueryCenterAPIInfo)
        findNavController().navigate(R.id.action_plumberRetailerQueryFragment_to_plumberRetailerQueryChatFragment,bundle)
    }

   /* override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedId: Int = (parent!!.getItemAtPosition(position) as CommonSpinner).id!!

        when (parent.id) {

            R.id.filter_topic_spinner -> {
                selectedFilterTopicId =  selectedId
            }

            R.id.filter_type_spinner -> {
                selectedFilterTypeId =  selectedId
            }

            R.id.status_spinner -> {
                selectedStatusId =  selectedId
            }

        }
    }*/

//    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onClick(v: View?) {
        if(BlockMultipleClick.click()) return@onClick
        when(v!!.id){
            R.id.fromDateEdt -> {
                DatePickerBox.date(activity) {
                    fromDateEdt.text = it
                    FromDate = it
                    try {
                        DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                            if (!it) {
                                FromDate = ""
                                fromDateEdt.text = ""
                            }
                        }
                    } catch (e: Exception) { }
                }
            }

            R.id.toDateEdt -> {
                DatePickerBox.date(activity) {
                    toDateEdt.text = it
                    ToDate = it
                    try {
                        DatePickerBox.dateCompare(activity, FromDate, ToDate) {
                            if (!it) {
                                ToDate = ""
                                toDateEdt.text = ""
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }

            R.id.filter_btn -> {
                if (filter_pop_up.visibility == View.VISIBLE) {
                    filter_pop_up.visibility = View.GONE
                } else {
                    filter_pop_up.visibility = View.VISIBLE
                }

            }

            R.id.searchBtn -> {

                if (searchBtn.text.toString().contains("X")) {
                    searchBtn.text = "";
                    listFull = false;
                    if (TextUtils.isEmpty(searchBtn.text)) {
                        currentList.clear();
                    }

                    listFull = false
                    isLoaded = false
                    seach_fld.setText("")
                    loadMemberList(1);
                    searchBtn.text = "SEARCH";

                } else {
                    if (!TextUtils.isEmpty(searchBtn.text.toString())) {
                        listFull = false;
                        if (!TextUtils.isEmpty(searchBtn.text)) {
                            currentList.clear();
                        }

                        loadMemberList(1);
                        searchBtn.text = "X";
                    }
                }

            }

            R.id.filter_ok_btn -> {
                if (filter_pop_up.visibility == View.VISIBLE) {
                    filter_pop_up.visibility = View.GONE
                } else {
                    filter_pop_up.visibility = View.VISIBLE
                }

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

                filter_pop_up.animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_to_right)
                filter_pop_up.visibility = View.GONE
//                add_new_member.visibility = View.VISIBLE
                listFull = false
                currentList.clear()
                loadMemberList(1)


//                setSEQueryListingRequest()
            }

            R.id.filter_reset_btn -> {

                resetCustomerList()


            }

        }
    }

    private fun resetCustomerList() {
        fromDateEdt.hint = "From Date"
        toDateEdt.hint = "To Date"

        status_spinner.setSelection(0)
        filter_topic_spinner.setSelection(0)
        filter_type_spinner.setSelection(0)
        selectedFilterTopicId = 0
        selectedFilterTypeId = 0
        selectedStatusId = 0
        filter_pop_up.visibility = View.GONE

        isRefresh = true
        listFull = false
        fromDateEdt.text = ""
        toDateEdt.text = ""
        FromDate = ""
        ToDate = ""

//                setSEQueryListingRequest()

        currentList.clear()

        loadMemberList(1)

    }

    private fun filterShowHide() {
        if (filter_pop_up.visibility == View.VISIBLE) {
            filter_pop_up.visibility = View.GONE
            return
        }
    }

    override fun onResume() {
        super.onResume()

        listFull = false
        isLoaded = false
        seach_fld.setText("")
        searchBtn.text = "SEARCH"
        loadMemberList(1)

    }











    /*      mSearchBtn.setOnClickListener(v -> {
            if (mSearchBtn.getText().toString().contains("X")) {
                mSeachFld.setText("");
                //        $$$$$$$$  vidhey  $$$$$$$$$$$$$$
                listFull = false;
                if (TextUtils.isEmpty(mSeachFld.getText())) {
                    currentList.clear();
                }
                //        $$$$$$$$  vidhey  $$$$$$$$$$$$$$

                loadMemberList(1);
                mSearchBtn.setText("SEARCH");
            } else {
                if (!TextUtils.isEmpty(mSeachFld.getText().toString())) {
                    //        $$$$$$$$  vidhey  $$$$$$$$$$$$$$
                    listFull = false;
                    if (!TextUtils.isEmpty(mSeachFld.getText())) {
                        currentList.clear();
                    }
                    //        $$$$$$$$  vidhey  $$$$$$$$$$$$$$
                    loadMemberList(1);
                    mSearchBtn.setText("X");
                }
            }
        });
    */









}