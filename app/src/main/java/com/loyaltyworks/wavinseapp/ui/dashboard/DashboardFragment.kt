package com.loyaltyworks.wavinseapp.ui.dashboard

import android.content.Intent
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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.CommonSpinner
import com.loyaltyworks.wavinseapp.model.CustomerDetailsRequest
import com.loyaltyworks.wavinseapp.model.CustomerDetailsResponseJson
import com.loyaltyworks.wavinseapp.model.TierRequest
import com.loyaltyworks.wavinseapp.ui.dashboard.adapter.DashboardAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.*
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*

class DashboardFragment : Fragment(), View.OnClickListener, DashboardAdapter.OnItemClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var dashboardViewModel: DashboardViewModel

    var dashboardAdapter: DashboardAdapter? = null

    var selectedVerifyStatusId = -1
    var selectedActiveStatusId = -1
    var selectedTeirId = -1

    var FromDate = ""
    var ToDate = ""

    var MaxRange = -1
    var MinRange = -1

    var isRefresh = true
    var isLoaded = false
    var listFull = false

    var startIndex = 1
    var pageLimit = 10


    var tempMemberList = ArrayList<CustomerDetailsResponseJson>()
    var currentList = ArrayList<CustomerDetailsResponseJson>()

    var mMemberLayoutManager: LinearLayoutManager? = null
    var mMemberListAdapter: RecyclerView.Adapter<*>? = null

    // Store a member variable for the listener
    var scrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filter_btn.setOnClickListener(this)
        fromDateEdt.setOnClickListener(this)
        toDateEdt.setOnClickListener(this)
        filter_reset_btn.setOnClickListener(this)
        filter_ok_btn.setOnClickListener(this)
        searchBtn.setOnClickListener(this)

        active_status.onItemSelectedListener = this
        verify_status.onItemSelectedListener = this
        tier_spinner.onItemSelectedListener = this


        add_new_member.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Title", "Enrollment")
            val intent = Intent(requireActivity(), EnrollmentActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent);
        }

        // use a linear layout manager
        mMemberLayoutManager = LinearLayoutManager(requireContext())
        mMemberLayoutManager!!.isAutoMeasureEnabled = true
        mMemberLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        memberlist_recyler.layoutManager = mMemberLayoutManager
        memberlist_recyler.isNestedScrollingEnabled = true
        memberlist_recyler.setHasFixedSize(false)
        memberlist_recyler.setRecycledViewPool(RecycledViewPool())
        memberlist_recyler.itemAnimator = DefaultItemAnimator()

        loadMemberListObserver()
        setFileterSpinners()

    }


    private fun setFileterSpinners() {

        dashboardViewModel.getTierListing(TierRequest(ActionType = "7"))

        dashboardViewModel.getTierListingLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {

                    val gradeList = ArrayList<CommonSpinner>()

                    it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

                        if (it.lstAttributesDetails[index].AttributeType.equals("CustomerGrade")) {
                            val tierStatus = CommonSpinner()
                            tierStatus.id = it.lstAttributesDetails[index].AttributeId
                            tierStatus.name = it.lstAttributesDetails[index].AttributeValue
                            gradeList.add(tierStatus)

                        }
                    }

                    val tierStatus = CommonSpinner()
                    tierStatus.id = -1
                    tierStatus.name = "Select tier"

                    gradeList.add(0, tierStatus)

                    tier_spinner.adapter = CommonAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        gradeList
                    )

                }


            })


        /**
        0	Unverified
        1	Verified
        2	Verification failed
        3	Posted for approval
        4	Verification Pending
         */


        val mVerificationList = ArrayList<CommonSpinner>()

        val verification1 = CommonSpinner()
        verification1.id = -1
        verification1.name = "Select Verification status"

        val verification2 = CommonSpinner()
        verification2.id = 0
        verification2.name = "Unverified"

        val verification3 = CommonSpinner()
        verification3.id = 1
        verification3.name = "Verified"

        val verification4 = CommonSpinner()
        verification4.id = 2
        verification4.name = "Verification failed"

        val verification5 = CommonSpinner()
        verification5.id = 3
        verification5.name = "Posted for approval"

        val verification6 = CommonSpinner()
        verification6.id = 4
        verification6.name = "Verification Pending"

        mVerificationList.add(verification1)
        mVerificationList.add(verification2)
        mVerificationList.add(verification3)
        mVerificationList.add(verification4)
        mVerificationList.add(verification5)
        mVerificationList.add(verification6)

        val mStatusList = ArrayList<CommonSpinner>()

        val status1 = CommonSpinner()
        status1.id = 0
        status1.name = "Select Member status"

        val status2 = CommonSpinner()
        status2.id = 1
        status2.name = "Inactive"

        val status3 = CommonSpinner()
        status3.id = 2
        status3.name = "Active"

        mStatusList.add(status1)
        mStatusList.add(status2)
        mStatusList.add(status3)

        active_status.adapter =
            CommonAdapter(requireContext(), android.R.layout.simple_spinner_item, mStatusList)
        verify_status.adapter =
            CommonAdapter(requireContext(), android.R.layout.simple_spinner_item, mVerificationList)



        scrollListener = object : EndlessRecyclerViewScrollListener(mMemberLayoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadMemberList(page + 1)
            }
        }

        memberlist_recyler.addOnScrollListener(scrollListener!!)


    }


    fun loadMemberList(startIndex: Int) {

        if (listFull) return

        this.startIndex = startIndex

        if (startIndex == 1)
            scrollListener!!.resetState()

        val minRange = if (min_range.text.toString().isEmpty()) 0
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
        }

        LoadingDialogue.showDialog(requireContext())


        dashboardViewModel.getCustomerDetails(

            CustomerDetailsRequest(
                ActionType = "1",
                ActiveStatus = selectedActiveStatusId.toString(),
                ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                CustomerGradId = selectedTeirId.toString(),
                FromDate = AppController.dateFormat(FromDate),
                ToDate = AppController.dateFormat(ToDate),
                IsVerified = selectedVerifyStatusId.toString(),
//            LocationId = "",
                MaxPointRange = maxRange.toString(),
                MerchantID = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].MerchantId.toString(),
                MinPointRange = minRange.toString(),
                PageSize = pageLimit.toString(),
                Type = seach_fld.text.toString(),
                StartIndex = startIndex.toString(),

                )
        )


    }


    private fun loadMemberListObserver() {

        dashboardViewModel.getCustomerDetailsLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                LoadingDialogue.dismissDialog()

                if (it != null && !it.CustomerDetailsResponseJson.isNullOrEmpty()) {

                    memberlist_recyler.visibility = View.VISIBLE

                    noResult_txt.text =
                        "Total search result : ${it!!.CustomerDetailsResponseJson!![0].TotalRows}"


                    // check if search or not
                    if (startIndex == 1) {
                        listFull = false
                        // 1. First, clear the array of data
                        tempMemberList.clear()
                        currentList.clear()
                        // 2. Notify the adapter of the update
                        mMemberListAdapter?.notifyDataSetChanged() // or notifyItemRangeRemoved
                        isRefresh = false
                    }



                    for (customer in it.CustomerDetailsResponseJson) {
                        tempMemberList.add(tempMemberList.size, customer)
                    }


                    currentList.clear()

                    currentList.addAll(tempMemberList)


                    //AppController.getInstance().showToastMSG(context,tempMemberList.size()+"");
                    //this.categoryName =categoryName;
                    if (!isLoaded) {
                        isLoaded = true
                        mMemberListAdapter = DashboardAdapter(currentList, this)
                        memberlist_recyler.adapter = mMemberListAdapter
                    } else {
                        mMemberListAdapter!!.notifyDataSetChanged()
                    }

                } else {

                    if (startIndex == 1) {
                        noResult_txt.text = "No search result found."
                        memberlist_recyler.visibility = View.GONE

                        LoadingDialogue.dismissDialog()
                    } else {
                        listFull = true
                    }

                }

            })
    }


    override fun onResume() {
        super.onResume()
        resetCustomerList()
//        listFull = false
//        seach_fld.setText("")
//        searchBtn.text = "SEARCH"
//        loadMemberList(1)

    }

    override fun onClick(v: View?) {
        if (BlockMultipleClick.click()) return@onClick
        when (v!!.id) {

            R.id.filter_btn -> {

                if (filter_pop_up.visibility == View.GONE) {
                    add_new_member.visibility = View.GONE
                    filter_pop_up.visibility = View.VISIBLE
                    filter_pop_up.animation = AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide_from_right
                    )
                } else {
//                    add_new_member.visibility = View.VISIBLE
                    filter_pop_up.animation = AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide_to_right
                    )
                    filter_pop_up.visibility = View.GONE
                }
            }

            R.id.fromDateEdt -> {

                DatePickerBox.date(requireActivity()) {
                    fromDateEdt.text = it
                    FromDate = it
//                    if (!FromDate.isNullOrEmpty())
//                        FromDate = AppController.dateFormat(FromDate)
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
                DatePickerBox.date(requireActivity()) {
                    toDateEdt.text = it
                    ToDate = it
//                    if (!ToDate.isNullOrEmpty())
//                        ToDate = AppController.dateFormat(ToDate)
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

            R.id.filter_reset_btn -> {
                resetCustomerList()
            }

            R.id.filter_ok_btn -> {

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

            }

            R.id.searchBtn -> {

                if (add_new_member.visibility == View.GONE) {
//                    add_new_member.visibility = View.VISIBLE
                    filter_pop_up.animation =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_to_right)
                    filter_pop_up.visibility = View.GONE
                }
                if (searchBtn.text.toString().contains("X")) {
                    seach_fld.setText("")
                    listFull = false
                    loadMemberList(1)
                    searchBtn.text = "SEARCH"
                } else {
                    if (!TextUtils.isEmpty(seach_fld.text.toString())) {
                        listFull = false
                        if (!TextUtils.isEmpty(seach_fld.text)) {
                            currentList.clear()
                        }
                        loadMemberList(1)
                        searchBtn.text = "X"
                    }
                }

            }

        }


    }

    private fun resetCustomerList() {
        toDateEdt.text = "To Date"
        fromDateEdt.text = "From Date"
        FromDate = ""
        ToDate = ""

        fromDateEdt.setTextColor(requireContext().resources.getColor(R.color.dark_grey))
        toDateEdt.setTextColor(requireContext().resources.getColor(R.color.dark_grey))
        active_status.setSelection(0)
        se_spinner.setSelection(0)
        asm_spinner.setSelection(0)
        tier_spinner.setSelection(0)
        max_range.setText("500000")
        min_range.setText("0")

        seach_fld.setText("")
        searchBtn.text = "SEARCH"
        filter_pop_up.animation = AnimationUtils.loadAnimation(context, R.anim.slide_to_right)
        filter_pop_up.visibility = View.GONE
//        add_new_member.visibility = View.VISIBLE
        isRefresh = true
        listFull = false
        isLoaded = false
        selectedActiveStatusId = -1
        selectedVerifyStatusId = -1
        selectedTeirId = -1
        startIndex = 1
        verify_status.setSelection(0)

        setFileterSpinners()

        currentList.clear()

        loadMemberList(startIndex)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val selectedId: Int = (parent!!.getItemAtPosition(position) as CommonSpinner).id!!

        when (parent.id) {
            R.id.active_status -> {
                selectedActiveStatusId = selectedId
            }

            R.id.verify_status -> {
                selectedVerifyStatusId = selectedId
            }
            R.id.tier_spinner -> {
                selectedTeirId = selectedId
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


    //startActivity(Intent(requireActivity(), EnrollmentActivity::class.java))

    override fun onCustomerListClickResponse(customerList: CustomerDetailsResponseJson) {

        if (filter_pop_up.visibility == View.VISIBLE) {
            filter_pop_up.visibility = View.GONE
//            add_new_member.visibility = View.VISIBLE
            return
        }

        val bundle = Bundle()
        bundle.putString("Title", "Verification")
        bundle.putSerializable("SELECT_MEMBER", customerList)
        val intent = Intent(requireActivity(), EnrollmentActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent);

        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    override fun onCustomerInteractionClickResponse(
        pos: Int,
        customerList: CustomerDetailsResponseJson
    ) {
//        TODO("Not yet implemented")
    }

}