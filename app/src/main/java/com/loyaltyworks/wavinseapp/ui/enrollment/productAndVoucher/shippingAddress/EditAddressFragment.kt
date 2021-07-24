package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.shippingAddress


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import com.loyaltyworks.wavinseapp.model.ObjCustShippingAddressDetail
import com.loyaltyworks.wavinseapp.model.RedeemGiftCatalogueRequest
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.CityAdapter
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.DeactivateDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.DialogueCallBack
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.StateAdapter
import kotlinx.android.synthetic.main.edit_address_fragment.*
import kotlinx.android.synthetic.main.my_cart_fragment.*
import java.util.*

class EditAddressFragment : BottomSheetDialogFragment(), OnItemSelectedListener {

    var cartProductList: List<ObjCatalogueList> = ArrayList<ObjCatalogueList>()
    val shippingAddressDetails = ObjCustShippingAddressDetail()

    // DreamGiftData to redeem dreamGift
    lateinit var dreamGiftData: RedeemGiftCatalogueRequest

    private lateinit var reddemGiftViewModel: ReddemGiftViewModel
    private lateinit var viewModel: ShippingAddressViewModel
    var savedCustomerDetails: LstCustomerJsons? = null
    var mSelectedState: StateList? = null
    var mSelectedCity: CityList? = null
    var selectedStateStatus = false

    var cityList = mutableListOf<CityList>()
    var stateList = mutableListOf<StateList>()

    var cityID : Int = -1
    var stateID : Int = -1
    var i = 0
    var j = 0
    var error = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
//        val filterMenu = menu.findItem(R.id.filter)
        val cart = menu.findItem(R.id.cart)
//        if (filterMenu != null) filterMenu.isVisible = false
        if (cart != null) cart.isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        reddemGiftViewModel = ViewModelProvider(this).get(ReddemGiftViewModel::class.java)
        viewModel = ViewModelProvider(this).get(ShippingAddressViewModel::class.java)
        return inflater.inflate(R.layout.edit_address_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        state.onItemSelectedListener = this
        city.onItemSelectedListener = this

        viewModel.myProfileResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { it ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()

                if (it != null && !it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson.isNullOrEmpty()) {


                    it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!!.forEach { lstCustomerDetail ->
                        savedCustomerDetails = lstCustomerDetail
                    }

//                savedCustomerDetails = it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson
                    /* Edit field Text data binding*/
                    country.text = it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].CountryName
                    mobile.setText(it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].Mobile)
                    address.setText(it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].Address1)
                    zip_code.setText(it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].Zip)

                    cityID = it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].CityId!!
                    stateID = it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].StateId!!

                    viewModel.setStateRequest(
                        GetStateRequest(
                            ActionType = "2",
                            IsActive = "true",
                            SortColumn = "STATE_NAME",
                            SortOrder = "ASC",
                            StartIndex = "1",
                            CountryID = it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].CountryId
                        )
                    )

                }
            }

            viewModel.getStateResponse.observe(viewLifecycleOwner, Observer {
                if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    if (it != null && !it.StateList.isNullOrEmpty())
//                    LoaderDialogue.dismissDialog()

                        stateList.addAll(it.StateList!!)
                    stateList.add(
                        0, StateList(
                            StateName = "Select State",
                            StateId = -1
                        )
                    )

                    state.adapter = StateAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        stateList
                    )

                    stateList.forEach { stateDetail ->
                        if (stateDetail.StateId == stateID) {
                            state.setSelection(i)
                            i = 0
                            return@forEach
                        }
                        i++
                    }
                }

            })

            viewModel.getCityResponse.observe(viewLifecycleOwner, Observer {
                if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    if (it != null && !it.CityList.isNullOrEmpty()) {
                        if (cityList.size > 0) {
                            cityList.clear()
                        }
                        cityList.addAll(it.CityList!!)
                        cityList.add(
                            0, CityList(
                                CityName = "Select City",
                                CityId = -1
                            )
                        )

                        city.adapter = CityAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            cityList
                        )

                        cityList.forEach { cityDetail ->
                            if (cityDetail.CityId == cityID) {
                                city.setSelection(j)
                                j = 0
                                return@forEach
                            }
                            j++
                        }

                    } else {
                        if (cityList.size > 0) {
                            cityList.clear()
                        }
                        cityList.add(
                            0, CityList(
                                CityName = "Select City",
                                CityId = -1
                            )
                        )

                        city.adapter = CityAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            cityList
                        )
                    }
                }

            })

        })

    }


    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        LoaderDialogue.showDialog(requireContext())

        if(arguments!=null) {
            // If this data not null, than redemption will happen for dreamGift
            if(requireArguments().getSerializable("DreamGiftData")!=null)
                dreamGiftData = requireArguments().getSerializable("DreamGiftData") as RedeemGiftCatalogueRequest
        }

        /* val bundle = this.arguments
         if (bundle != null) {
             cartProductList = arguments?.getSerializable("CatalogueData") as ArrayList<ObjCatalogueList>

             if(!cartProductList.isNullOrEmpty()){

                 var redeemDate: String? = ""
 //                val (ActionType, ActorId, ActorRole, IsActive, BrandTermsAndConditions, CatalogueBrandCode, CatalogueBrandDesc, CatalogueBrandId, CatalogueBrandName, CategoryParentID, CatogoryId, CatogoryImage, CatogoryName, Color_Code, Color_Id, Color_Name, FromDate, JFromDate, JToDate, MerchantId, MerchantName, ModelId, ModelName, Status, SubCategoryID, SubCategoryName, TermsCondition, ToDate, UserAccess, ActiveStatus, ActualRedemptionDate, AdditionalRemarks, ApproverName, AverageEarning, AvgExpDate, AvgGreaterExpDate, AvgLesserExpDate, Barcode, CashPerUnit, CashValue, CatalogueId, CatalogueType, CatalougeBrandName, CategoryID, CommandName, CountryCurrencyCode, CountryID, CreatedBy, CreatedDate, DailyAvgCash, DeliveryType, DreamGiftId, ExpectedDelivery, ExpiryDate, ExpiryOn, GreaterAvgCash, HasPartialPayment, IsApproved, IsCash, IsPlanner, IsPopularCount, JRedemptionDate, LesserAvgCash, LocationId, LoyaltyId, MSQA, MemberName, MinimumStockQunty, Mobile, MultipleRedIds, NoOfPointsDebit, NoOfQuantity, PartialPaymentCash, PlannerStatus, PointBalance, PointRedem, PointReqToAcheiveProduct, PointsPerUnit, PointsRequired, ProductCode, ProductDesc, ProductImage, ProductImageServerPath, ProductName, Product_type, RedeemableAverageEarning, RedeemableAverageEarning12, RedeemableAverageEarning6, RedeemableEncashBalance, RedeemablePointBalance, RedemptionDate, RedemptionId, RedemptionPlannerId, RedemptionRefno, RedemptionStatus, RedemptionTypeId, SegmentDetails, SelectedStatus, TotalCash, Total_Row, VendorId, VendorName, max_points, min_points) = ObjCatalogueList()
                 for (i in cartProductList.indices) {
 //                    val date: String = cartProductList[i].JRedemptionDate!!
                     if (cartProductList[i].JRedemptionDate.isNullOrEmpty()) {
                         redeemDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
                         cartProductList[i].JRedemptionDate = redeemDate
                     } else {
                         redeemDate = cartProductList[i].JRedemptionDate
                         cartProductList[i].JRedemptionDate = redeemDate
                     }

 //                    if (date == null || date.contains("0001-01-01")) {
 //                        redeemDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
 //                        cartProductList[i].JRedemptionDate = redeemDate
 //                    } else {
 //                        redeemDate = cartProductList[i].JRedemptionDate
 //                        cartProductList[i].JRedemptionDate = redeemDate
 //                    }
                 }

             }
         }*/

        LoadingDialogue.showDialog(requireContext())

        viewModel.setUserDetailRequest(
            MyProfileRequest(
                ActionType = "6", CustomerId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString()
            )
        )

        proceedBtn.setOnClickListener {
            if(validate()){

                /*Shipping Address */

                shippingAddressDetails.Mobile = savedCustomerDetails!!.Mobile
                shippingAddressDetails.Zip = savedCustomerDetails!!.Zip
                shippingAddressDetails.Address1 = savedCustomerDetails!!.Address1
                shippingAddressDetails.StateId = savedCustomerDetails!!.StateId.toString()
                shippingAddressDetails.CityId = savedCustomerDetails!!.CityId.toString()
                shippingAddressDetails.CountryId = savedCustomerDetails!!.CountryId.toString()
                shippingAddressDetails.FullName = savedCustomerDetails!!.FirstName.toString()
                shippingAddressDetails.Email = savedCustomerDetails!!.Email.toString()


                val bundle = Bundle()
                bundle.putSerializable("Address", shippingAddressDetails)

                if(this::dreamGiftData.isInitialized) {
                    dreamGiftData.ObjCustShippingAddressDetails = shippingAddressDetails
                    bundle.putSerializable("DreamGiftData", dreamGiftData)
                }

//                val bundle = Bundle()
//                bundle.putSerializable("Address", shippingAddressDetails)
                it.findNavController().navigate(R.id.action_editAddressFragment_to_OTPFragment, bundle)


            } else{

                DeactivateDialogue.showPopUpDialog(
                    requireContext(),
                    false,
                    "",
                    "ok",
                    error,
                    object : DialogueCallBack {
                        override fun onResponse(response: String) {

                        }

                        override fun onAgain() {

                        }

                    })

            }
        }
    }

    fun validate(): Boolean {
        var valid = true
        error = ""

        val mobiles: String = mobile.text.toString()
        val zipcode: String = zip_code.text.toString()
        val addresss: String = address.text.toString()

        if (mobiles.isEmpty()) {
            error += "Enter mobile number"
            valid = false
        } else if(!mobiles.isNullOrEmpty() && mobiles.length<10){
            error +="\n"+"Enter valid mobile number"
            valid = false
        }else {
            savedCustomerDetails!!.Mobile = mobiles
            mobile.error = null
        }


        if (TextUtils.isEmpty(zipcode)) {
            error += "\n"+"Enter zip code"
            valid = false
        }else if(zipcode.length < 6){
            error += "\n"+"Invalid zip code"
            valid = false
        }else{
            savedCustomerDetails!!.Zip = zipcode
            zip_code.error = null
        }

        if (TextUtils.isEmpty(addresss)) {
            error += "\n"+"Enter address"
            valid = false
        }else{
            savedCustomerDetails?.Address1 = addresss
        }

        if (mSelectedState == null || mSelectedState!!.StateId === -1) {
            error += "\n"+"Please Select State"
            valid = false
        }else{
            savedCustomerDetails!!.StateId = mSelectedState!!.StateId
        }

        if (mSelectedCity == null || mSelectedCity!!.CityId === -1) {
            error += "\n"+" Please Select City"
            valid = false
        }else{
            savedCustomerDetails!!.CityId = mSelectedCity!!.CityId
        }

        return valid
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        when ((parent as Spinner).id) {

            R.id.state -> {
                mSelectedState = parent.getItemAtPosition(position) as StateList
                savedCustomerDetails?.StateId = mSelectedState!!.StateId
                savedCustomerDetails?.StateName = mSelectedState!!.StateName
                if (mSelectedState!!.StateId!! > 0) {

                    selectedStateStatus = false
                    viewModel.setCityRequest(
                        GetCityDetailsRequest(
                            ActionType = "2",
                            IsActive = "true",
                            SortColumn = "CITY_NAME",
                            SortOrder = "ASC",
                            StateId = mSelectedState!!.StateId.toString()

                        )
                    )

                }else{

                    if(cityList.size>0) {
                        cityList.clear()
                    }

                    cityList.add(
                        0, CityList(
                            CityName = "Select City",
                            CityId = -1
                        )
                    )

                    city.adapter = CityAdapter(requireContext(), android.R.layout.simple_spinner_item, cityList)
                }
            }

            R.id.city -> {
                mSelectedCity = parent.getItemAtPosition(position) as CityList
            }

        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}

}