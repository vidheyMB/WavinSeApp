package com.loyaltyworks.wavinseapp.ui.enrollment.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.ui.enrollment.general.GeneralViewModel
import com.loyaltyworks.wavinseapp.ui.enrollment.personal.PersonalViewModel
import com.loyaltyworks.wavinseapp.ui.enrollment.personal.adapter.CountryAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.CityAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.StateAdapter
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.CityList
import com.loyaltyworks.wavinseapp.model.GetCityDetailsRequest
import com.loyaltyworks.wavinseapp.model.StateList
import kotlinx.android.synthetic.main.business_fragment.*
import kotlinx.android.synthetic.main.edit_address_fragment.*
import kotlinx.android.synthetic.main.general_fragment.*
import kotlinx.android.synthetic.main.identification_fragment.*
import kotlinx.android.synthetic.main.personal_fragment.*

class BusinessFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: BusinessViewModel
    private lateinit var viewModelPersonal: PersonalViewModel
    private lateinit var viewModelGeneral: GeneralViewModel

    private var lstCustomerIdentityInfo: ArrayList<LstCustomerIdentityInfo>? = null
    private var lstCustomerOfficalInfoJson: ArrayList<LstCustomerOfficalInfoJson>? = null
    private var lstCustomerJsons: ArrayList<LstCustomerJsons>? = null

    var mSelectedCountry: CountryList? = null
    var mSelectedState: StateList? = null
    var mSelectedCity: CityList? = null

    var countrylist = mutableListOf<CountryList>()
    var citylist = mutableListOf<CityList>()
    var stateList = mutableListOf<StateList>()

    var mSelectedFirmType: CommonSpinner? = null
    var mSelectedJobType: CommonSpinner? = null

    var saveUpdateBusinesMsg = ""
    var failUpdateBusinesMsg = ""

    var i = 0
    var j = 0
    var k = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelGeneral = ViewModelProvider(this).get(GeneralViewModel::class.java)
        viewModelPersonal = ViewModelProvider(this).get(PersonalViewModel::class.java)
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)
        return inflater.inflate(R.layout.business_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*Country Response*/
        viewModelPersonal.getCountryListingLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.CountryList!!.isNotEmpty()) {
//                buss_country.isEnabled = false
                val countryList = java.util.ArrayList<CountryList>()
                var i = 0

                it.CountryList!!.forEachIndexed { index, lstCountryListDetail ->
                    if (it.CountryList!![index].CountryId == 15) {
                        val countryStatus = CountryList()
                        countryStatus.CountryId = it.CountryList!![index].CountryId
                        countryStatus.CountryName = it.CountryList!![index].CountryName
                        countryList.add(countryStatus)
                    }
                }

                val countryStatus = CountryList()
                countryStatus.CountryId = -1
                countryStatus.CountryName = "Select Country"

                countryList.add(0, countryStatus)

                buss_country.adapter = CountryAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    countryList
                )

                if (lstCustomerOfficalInfoJson != null) {
                    countryList.forEach { countryListDetail ->
                        if (countryListDetail.CountryId == lstCustomerOfficalInfoJson!![0].CountryId) {
                            buss_country.setSelection(i)
                            i = 0
                            return@forEach
                        }
                        i++
                    }
                }

                LoadingDialogue.dismissDialog()

            }
        })

        /*State Response*/
        viewModelPersonal.getStateListingLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.StateList!!.isNotEmpty()) {
                val stateList = java.util.ArrayList<StateList>()
                var i = 0
                it.StateList!!.forEachIndexed { index, lstStateDetail ->
                    val stateStatus = StateList()
                    stateStatus.StateId = it.StateList!![index].StateId
                    stateStatus.StateName = it.StateList!![index].StateName
                    stateList.add(stateStatus)
                }

                val stateStatus = StateList()
                stateStatus.StateId = -1
                stateStatus.StateName = "Select State"

                stateList.add(0, stateStatus)

                buss_state.adapter = StateAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    stateList
                )

                if (lstCustomerOfficalInfoJson != null) {
                    stateList.forEach { stateListDetail ->
                        if (stateListDetail.StateId == lstCustomerOfficalInfoJson!![0].StateId) {
                            buss_state.setSelection(i)
                            i = 0
                            return@forEach
                        }
                        i++
                    }
                }
            }

            LoadingDialogue.dismissDialog()

        })

        /*City Response*/
        viewModelPersonal.getCityResponse.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && !it.CityList.isNullOrEmpty()) {
                    if (citylist.size > 0) {
                        citylist.clear()
                    }
                    citylist.addAll(it.CityList!!)
                    citylist.add(
                        0, CityList(
                            CityName = "Select City",
                            CityId = -1
                        )
                    )

                    buss_city.adapter = CityAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        citylist
                    )

                    if (lstCustomerOfficalInfoJson != null) {
                        citylist.forEach { cityDetail ->
                            if (cityDetail.CityId == lstCustomerOfficalInfoJson!![0].CityId) {
                                buss_city.setSelection(j)
                                j = 0
                                return@forEach
                            }
                            j++
                        }
                    }

                } else {
                    if (citylist.size > 0) {
                        citylist.clear()
                    }
                    citylist.add(
                        0, CityList(
                            CityName = "Select City",
                            CityId = -1
                        )
                    )

                    buss_city.adapter = CityAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        citylist
                    )
                }
            }
            LoadingDialogue.dismissDialog()
        })

        /*FirmType Response*/
        viewModel.getFirmTypeListingLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.lstAttributesDetails.isNullOrEmpty()) {
                var j = 0
                val firmTypeList = java.util.ArrayList<CommonSpinner>()

                it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

//                        if (it.lstAttributesDetails[index].AttributeType.equals("CustomerGrade") && it.lstAttributesDetails[index].AttributeId == 2) {
                    val firmTypeStatus = CommonSpinner()
                    firmTypeStatus.id = it.lstAttributesDetails[index].AttributeId
                    firmTypeStatus.name = it.lstAttributesDetails[index].AttributeValue
                    firmTypeList.add(firmTypeStatus)

                }

                val firmTypeStatus = CommonSpinner()
                firmTypeStatus.id = -1
                firmTypeStatus.name = "Select Firm Type"

                firmTypeList.add(0, firmTypeStatus)

                buss_firm_type.adapter = CommonAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    firmTypeList
                )

                if (lstCustomerOfficalInfoJson != null) {
                    firmTypeList.forEach { firmTypeDetail ->
                        if (firmTypeDetail.id == lstCustomerOfficalInfoJson!![0].FirmTypeID) {
                            buss_firm_type.setSelection(j)
                            j = 0
                            return@forEach
                        }
                        j++
                    }
                }

            }

        })

        /*JobType Response*/
        viewModel.getJobTypeListingLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.lstAttributesDetails.isNullOrEmpty()) {
                var j = 0
                val jobTypeList = java.util.ArrayList<CommonSpinner>()

                it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

//                        if (it.lstAttributesDetails[index].AttributeType.equals("CustomerGrade") && it.lstAttributesDetails[index].AttributeId == 2) {
                    val jobTypeStatus = CommonSpinner()
                    jobTypeStatus.id = it.lstAttributesDetails[index].AttributeId
                    jobTypeStatus.name = it.lstAttributesDetails[index].AttributeValue
                    jobTypeList.add(jobTypeStatus)

                }

                val jobTypeStatus = CommonSpinner()
                jobTypeStatus.id = -1
                jobTypeStatus.name = "Select Job Type"

                jobTypeList.add(0, jobTypeStatus)

                buss_job.adapter = CommonAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    jobTypeList
                )

                if (lstCustomerOfficalInfoJson != null) {
                    jobTypeList.forEach { jobTypeDetail ->
                        if (jobTypeDetail.id == lstCustomerOfficalInfoJson!![0].JobTypeID) {
                            buss_job.setSelection(j)
                            j = 0
                            return@forEach
                        }
                        j++
                    }
                }

            }

        })

        LoadingDialogue.dismissDialog()

        /*Update and Save Business Response*/
        viewModel.getSaveUpdateBusinessLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.ReturnMessage!!.split(":")[0].toInt() > 0) {
                (activity as EnrollmentActivity).snackBar(
                    saveUpdateBusinesMsg,
                    R.color.primary_dark_blue
                )
                findNavController().popBackStack()
            } else {
                (activity as EnrollmentActivity).snackBar(failUpdateBusinesMsg, R.color.red)
                findNavController().popBackStack()
            }
            LoadingDialogue.dismissDialog()
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadingDialogue.showDialog(requireContext())
        /*Country Request*/
        viewModelPersonal.setCountryRequest(
            CountryRequest(
                SortColumn = "COUNTRY_NAME",
                SortOrder = "ASC",
                StartIndex = 1
            )
        )

        /*FirmType Request*/
        viewModel.getFirmTypeListing(TierRequest(ActionType = "21"))

        /*Job Type Request*/
        viewModel.getJobTypeListing(TierRequest(ActionType = "22"))

        val bundle = arguments
        try {

            lstCustomerJsons =
                bundle?.getSerializable("GENERAL_DETAIL") as ArrayList<LstCustomerJsons>
            lstCustomerOfficalInfoJson =
                bundle?.getSerializable("BUSINESS_DETAIL") as ArrayList<LstCustomerOfficalInfoJson>

            if (lstCustomerOfficalInfoJson!!.isNotEmpty()) {

                failUpdateBusinesMsg = "Failed to update business"
                saveUpdateBusinesMsg = "Update business success"

                buss_fname.setText(lstCustomerOfficalInfoJson!![0].CompanyName)
                buss_add.setText(lstCustomerOfficalInfoJson!![0].FirmAddress)
                buss_pincode.setText(lstCustomerOfficalInfoJson!![0].StdCode)
                buss_mobile.setText(lstCustomerOfficalInfoJson!![0].PhoneOffice)
                buss_firm_email.setText(lstCustomerOfficalInfoJson!![0].OfficalEmail)
                buss_firm_phone.setText(lstCustomerOfficalInfoJson!![0].FirmMobile)
                buss_STD.setText(lstCustomerOfficalInfoJson!![0].StdCode)
                buss_firm_size.setText(lstCustomerOfficalInfoJson!![0].FirmSize)

                buss_submit.text = "Update"

            } else {
                failUpdateBusinesMsg = "Failed to save business"
                saveUpdateBusinesMsg = "Saved business success"
            }

        } catch (e: Exception) {
        }

//        lstCustomerJsons = bundle?.getSerializable("GENERAL_DETAIL") as ArrayList<LstCustomerJson>

        /*Spinner onItemSelected*/
        buss_country.onItemSelectedListener = this
        buss_state.onItemSelectedListener = this
        buss_city.onItemSelectedListener = this
        buss_job.onItemSelectedListener = this
        buss_firm_type.onItemSelectedListener = this

        /*SaveUpdateBusiness Profile Request*/
        buss_submit.setOnClickListener {
            LoadingDialogue.showDialog(requireContext())
            viewModel.setSaveUpdateBusinessRequest(
                SaveUpdateBusinessRequest(
                    ActionType = "4",
                    ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                    ObjBusinessCustomer(
                        CustomerId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].CustomerId.toString(),
                        LoyaltyId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].LoyaltyId.toString(),
                        MerchantId = lstCustomerJsons!![0].MerchantId.toString()
                    ),
                    ObjBusinessCustomerDetails(
                        CustomerDetailId = PreferenceHelper.getSECustomerDetails(
                            requireContext()
                        )!!.lstCustomerJson!![0].CustomerDetailId.toString()
                    ),
                    ObjBusinesCustomerOfficalInfo(
                        Address = buss_add.text.toString(),
                        CityId = mSelectedCity!!.CityId.toString(),
                        CompanyName = buss_fname.text.toString(),
                        CountryId = mSelectedCountry!!.CountryId.toString(),
                        CustomerZip = buss_pincode.text.toString(),
                        FirmMobile = buss_mobile.text.toString(),
                        FirmSize = buss_firm_size.text.toString(),
                        FirmTypeID = mSelectedFirmType!!.id.toString(),
                        JobTypeID = mSelectedJobType!!.id.toString(),
                        OfficalEmail = buss_firm_email.text.toString(),
                        PhoneOffice = buss_mobile.text.toString(),
                        StateId = mSelectedState!!.StateId.toString(),
                        StdCode = buss_STD.text.toString()
                    )
                )
            )
        }

    }

//    private fun isValid(): Boolean {
//        var isValid = true
//        val email: String = mEmail.getText().toString()
//        val mobile: String = mFirmMobile.getText().toString()
//        val phone: String = mFirmPhone.getText().toString()
//        val stdCode: String = mFirmSTD.getText().toString()
//        val pin: String = mFirmPin.getText().toString()
//        if (!email.isEmpty()) {
//            if (!(email.length > 6 && email.contains("@") && email.contains("."))) {
//                mEmail.setError("Invalid email Id.")
//                focusView = mEmail
//                isValid = false
//            } else mEmail.setError(null)
//        }
//        if (!mobile.isEmpty()) {
//            if (mobile.length != 10) {
//                mFirmMobile.setError("Invalid mobile number.")
//                focusView = mFirmMobile
//                isValid = false
//            } else mFirmMobile.setError(null)
//        }
//        if (!phone.isEmpty()) {
//            if (phone.length != 10) {
//                mFirmPhone.setError("Invalid phone number.")
//                focusView = mFirmPhone
//                isValid = false
//            } else mFirmPhone.setError(null)
//        }
//        if (!pin.isEmpty()) {
//            if (pin.length != 6) {
//                mFirmPin.setError("Invalid pin code.")
//                focusView = mFirmPin
//                isValid = false
//            } else mFirmPin.setError(null)
//        }
//        if (!stdCode.isEmpty()) {
//            if (stdCode.length != 6) {
//                mFirmSTD.setError("Invalid STD code.")
//                focusView = mFirmSTD
//                isValid = false
//            } else mFirmSTD.setError(null)
//        }
//        return isValid
//    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {

            R.id.buss_country -> {
                mSelectedCountry = parent.getItemAtPosition(position) as CountryList

                if (mSelectedCountry!!.CountryId!! > 0) {
                    LoadingDialogue.showDialog(requireContext())
                    viewModelPersonal.setStateRequest(
                        StateRequest(
                            ActionType = "2",
                            CountryID = mSelectedCountry!!.CountryId!!,
                            IsActive = "true"
                        )
                    )

                } else {

                    if (stateList.size > 0) {
                        stateList.clear()
                    }

                    stateList.add(
                        0, StateList(
                            StateName = "Select State",
                            StateId = -1
                        )
                    )

                    buss_state.adapter = StateAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        stateList
                    )

                }

            }

            R.id.buss_state -> {
                mSelectedState = parent.getItemAtPosition(position) as StateList

                if (mSelectedState!!.StateId!! > 0) {

                    LoadingDialogue.showDialog(requireContext())

                    viewModelPersonal.setCityRequest(
                        GetCityDetailsRequest(
                            ActionType = "2",
                            IsActive = "true",
                            SortColumn = "CITY_NAME",
                            SortOrder = "ASC",
                            StateId = mSelectedState!!.StateId.toString()

                        )
                    )

                } else {

                    if (citylist.size > 0) {
                        citylist.clear()
                    }

                    citylist.add(
                        0, CityList(
                            CityName = "Select City",
                            CityId = -1
                        )
                    )

                    buss_city.adapter = CityAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        citylist
                    )
                }
            }

            R.id.buss_city -> {
                mSelectedCity = parent.getItemAtPosition(position) as CityList
            }

            R.id.buss_firm_type -> {
                mSelectedFirmType = parent.getItemAtPosition(position) as CommonSpinner
            }

            R.id.buss_job -> {
                mSelectedJobType = parent.getItemAtPosition(position) as CommonSpinner
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}