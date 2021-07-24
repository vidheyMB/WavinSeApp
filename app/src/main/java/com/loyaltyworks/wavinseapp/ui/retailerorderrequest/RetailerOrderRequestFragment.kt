package com.loyaltyworks.wavinseapp.ui.retailerorderrequest

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.AttributeRequest
import com.loyaltyworks.wavinseapp.model.CommonSpinner
import com.loyaltyworks.wavinseapp.model.LstCustOrderDeliveryDetail
import com.loyaltyworks.wavinseapp.model.RetailerOrderRequest
import com.loyaltyworks.wavinseapp.ui.retailerorderrequest.adapter.RetailerOrderRequestAdapter
import com.loyaltyworks.wavinseapp.utils.*
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.retailer_order_request_fragment.*
import kotlinx.android.synthetic.main.retailer_order_request_fragment.filter_reset_btn
import kotlinx.android.synthetic.main.retailer_order_request_fragment.fromDateEdt
import kotlinx.android.synthetic.main.retailer_order_request_fragment.toDateEdt
import java.util.*

class RetailerOrderRequestFragment : Fragment(), RetailerOrderRequestAdapter.ClickOnItemClick,
    AdapterView.OnItemSelectedListener, View.OnClickListener {

    companion object {
        fun newInstance() = RetailerOrderRequestFragment()
    }

    private lateinit var viewModel: RetailerOrderRequestViewModel

    private var fromDate = ""
    private var toDate = ""

    var FromDate = ""
    var ToDate = ""


    var _retailerLists: ArrayList<CommonSpinner>? = null

    var isRefresh = true
    var isLoaded = false
    var listFull = false


    var startIndex = 1
    var NoOfRow = 20
    var selectedStatusName: String? = null

    var selectedStatusId = -2
    var selectedRetailerId = -1
    var sourceTypeId = -1


    var mSelectedStatus: CommonSpinner? = null
    var mSourceType: CommonSpinner? = null


    // Store a member variable for the listener
    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    var spinnerArrayAdapter: ArrayAdapter<String>? = null

    //    private LinearLayoutManager mLayoutManager;
    //    private RecyclerView.Adapter mMemberListAdapter;
    private var mMemberLayoutManager: LinearLayoutManager? = null
    var mMemberListAdapter: RetailerOrderRequestAdapter? = null

    lateinit var filter: MenuItem
    lateinit var notification: MenuItem


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(RetailerOrderRequestViewModel::class.java)

        /*  val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
          toolbar.inflateMenu(R.menu.main)
          toolbar.setOnMenuItemClickListener(this)*/

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.retailer_order_request_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        plumber_status_recycler.adapter = retailerOrderRequestAdapter

        filterSpinner.onItemSelectedListener = this
        status_spinner.onItemSelectedListener = this
        source_spinner.onItemSelectedListener = this
        toDateEdt.setOnClickListener(this)
        fromDateEdt.setOnClickListener(this)
        filter_reset_btn.setOnClickListener(this)
        filterButton.setOnClickListener(this)

        // use a linear layout manager
        mMemberLayoutManager = LinearLayoutManager(requireContext())
        mMemberLayoutManager!!.isAutoMeasureEnabled = true
        mMemberLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        plumber_status_recycler.layoutManager = mMemberLayoutManager
        plumber_status_recycler.isNestedScrollingEnabled = true
        plumber_status_recycler.setHasFixedSize(false)
        plumber_status_recycler.setRecycledViewPool(RecyclerView.RecycledViewPool())
        plumber_status_recycler.itemAnimator = DefaultItemAnimator()


        loadPlumberList(selectedStatusId, selectedRetailerId,sourceTypeId)
        loadRetailer()
        setRedemStatusList()
        loadSorceType()

    }


    private fun setRedemStatusList() {

        val mStatusList = ArrayList<CommonSpinner>()

        val status1 = CommonSpinner()
        status1.id = -2
        status1.name = "Select Status"

        val status2 = CommonSpinner()
        status2.id = 0
        status2.name = "Pending"

        val status3 = CommonSpinner()
        status3.id = 1
        status3.name = "Approved"

        val status4 = CommonSpinner()
        status4.id = -1
        status4.name = "Rejected"

        val status5 = CommonSpinner()
        status5.id = 2
        status5.name = "Escalated"

        val status6 = CommonSpinner()
        status6.id = 3
        status6.name = "Cancelled"

        mStatusList.add(status1)
        mStatusList.add(status2)
        mStatusList.add(status3)
        mStatusList.add(status4)
        mStatusList.add(status5)

        status_spinner.adapter = CommonAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mStatusList
        )


    }

    private fun loadSorceType() {

        val mSoruceTypeList = ArrayList<CommonSpinner>()

        val status1 = CommonSpinner()
        status1.id = -1
        status1.name = "Select source type"

        val status2 = CommonSpinner()
        status2.id = 1
        status2.name = "App"

        val status3 = CommonSpinner()
        status3.id = 2
        status3.name = "KloudQ"

        mSoruceTypeList.add(status1)
        mSoruceTypeList.add(status2)
        mSoruceTypeList.add(status3)

        source_spinner.adapter = CommonAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mSoruceTypeList)

    }

    private fun loadRetailer() {

        viewModel.getAttributeDetailsList(
            AttributeRequest(
                ActionType = "45",
                ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString()
            )
        )

        viewModel.getAttributeDetailsLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                val retailerList = ArrayList<CommonSpinner>()

                if (!it.lstAttributesDetails.isNullOrEmpty()) {

                    it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->
                        val commonspinner = CommonSpinner()

                        commonspinner.id = it.lstAttributesDetails[index].AttributeId
                        commonspinner.name = it.lstAttributesDetails[index].AttributeValue

                        retailerList.add(commonspinner)

                    }

                    retailerList.add(0, CommonSpinner(id = -1, name = "Select retailer"))

                    _retailerLists = retailerList

                    val retailerListName = ArrayList<String>()

                    for (commonSpinner in retailerList) {
                        retailerListName.add(commonSpinner.name!!)
                    }

                    spinnerArrayAdapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        retailerListName
                    )
                    spinnerArrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    filterSpinner.adapter = spinnerArrayAdapter

                } else {
                    setDefaultForSpinner()
                }


            })

    }


    private fun setDefaultForSpinner() {
        val productFromPrograms = ArrayList<CommonSpinner>()
        val commonSpinner = CommonSpinner()
        commonSpinner.name = "Select name"
        commonSpinner.id = -1
        productFromPrograms.add(commonSpinner)
        filterSpinner.adapter = CommonAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            productFromPrograms
        )
    }

    private fun loadPlumberList(Status: Int, RetailerId: Int, SourceTypeId:Int) {

        if (!FromDate.isNullOrEmpty())
            fromDate = AppController.dateFormat(FromDate)

        if (!ToDate.isNullOrEmpty())
            toDate = AppController.dateFormat(ToDate)

        LoadingDialogue.showDialog(requireContext())

     /*   viewModel.getPlumberClaimList(
            RetailerOrderRequest(
                ActionType = "3",
                ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                JFromDate = fromDate,
                JToDate = toDate,
                OrderStatusId = Status.toString(),
                RetailerLoyaltyId = RetailerId.toString(),
                SearchText = filterSearch.text.toString(),
                FilterTypeID = SourceTypeId
            )
        )*/

       val retailerOrderReqeust = RetailerOrderRequest()

        retailerOrderReqeust.ActionType = "3"
        retailerOrderReqeust.ActorId =  PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString()
        retailerOrderReqeust.JFromDate = fromDate
        retailerOrderReqeust.JToDate = toDate
        retailerOrderReqeust.OrderStatusId = Status.toString()
        retailerOrderReqeust.RetailerLoyaltyId =  RetailerId.toString()
        retailerOrderReqeust.SearchText = filterSearch.text.toString()

        if (SourceTypeId != -1)
        retailerOrderReqeust.FilterByID = SourceTypeId

        viewModel.getPlumberClaimList(retailerOrderReqeust)


        viewModel.getPlumberClaimLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            LoadingDialogue.dismissDialog()

            if (it != null && !it.lstCustOrderDeliveryDetails.isNullOrEmpty()) {

                error_textOrder.visibility = View.GONE
                plumber_status_recycler.visibility = View.VISIBLE

                plumber_status_recycler.adapter = RetailerOrderRequestAdapter(
                    it.lstCustOrderDeliveryDetails,
                    this
                )

            } else {
                plumber_status_recycler.visibility = View.GONE
                error_textOrder.visibility = View.VISIBLE

            }

        })

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent!!.id) {

            R.id.filterSpinner -> {
                val name = parent.selectedItem.toString()
                val namePosition = spinnerArrayAdapter!!.getPosition(name)
                selectedRetailerId = _retailerLists!![namePosition].id!!
            }

            R.id.status_spinner -> {
                mSelectedStatus = parent.getItemAtPosition(position) as CommonSpinner
                selectedStatusId = mSelectedStatus!!.id!!
                selectedStatusName = mSelectedStatus!!.name
            }

            R.id.source_spinner -> {
                mSourceType = parent.getItemAtPosition(position) as CommonSpinner
                sourceTypeId = mSourceType!!.id!!
            }


        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


    override fun onClick(v: View?) {
        if (BlockMultipleClick.click()) return
        when (v!!.id) {

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


            R.id.filter_reset_btn -> {

                filterSearch.setText("")
                FromDate = ""
                ToDate = ""
                fromDateEdt.text = "From Date"
                toDateEdt.text = "To Date"
                filterSpinner.setSelection(0)
                status_spinner.setSelection(0)
                source_spinner.setSelection(0)
                selectedRetailerId - 1
                selectedStatusId - 2
                filter_view.animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_to_right)
                filter_view.visibility = View.GONE
                loadPlumberList(-2, -1,-1)
            }

            R.id.filterButton -> {
                if (fromDate.isNotEmpty() && toDate.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "To date should not be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (fromDate.isEmpty() && toDate.isNotEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "From date should not be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    filter_view.animation =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_to_right)
                    filter_view.visibility = View.GONE
                    loadPlumberList(selectedStatusId, selectedRetailerId, sourceTypeId)
                }

            }


        }


    }


    override fun onClickOnItemClick(lstCustOrderDeliveryDetails: LstCustOrderDeliveryDetail) {
        if (BlockMultipleClick.click()) return

        if (filter_view.visibility == View.VISIBLE) {
            filter_view.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_to_right)
            filter_view.visibility = View.GONE
        }

        val bundle = Bundle()
        bundle.putSerializable("LSTCUSTOMER_DELIVERY_DETIALS", lstCustOrderDeliveryDetails)

        findNavController().navigate(
            R.id.action_retailerOrderRequestFragment_to_retailerOrderRequestDetailsFragment,
            bundle
        )

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {

                if (filter_view.visibility == View.GONE) {
                    filter_view.visibility = View.VISIBLE
                    filter_view.animation =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_from_right)
                } else {
                    filter_view.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_to_left_fast)


                    filter_view.visibility = View.GONE
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}