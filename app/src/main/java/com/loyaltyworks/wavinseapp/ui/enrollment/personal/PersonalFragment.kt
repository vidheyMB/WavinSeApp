package com.loyaltyworks.wavinseapp.ui.enrollment.personal

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.ui.enrollment.personal.adapter.CountryAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.personal.adapter.DistrictAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.StateAdapter
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.DatePickerBox
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.StateList
import kotlinx.android.synthetic.main.edit_address_fragment.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.general_fragment.*
import kotlinx.android.synthetic.main.personal_fragment.*
import kotlinx.android.synthetic.main.personal_fragment.per_birthday

class PersonalFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var viewModel: PersonalViewModel

    private lateinit var lstCustomerJsons: ArrayList<LstCustomerJsons>

    var mSelectedCountry: CountryList? = null
    var mSelectedState: StateList? = null
    var mSelectedNativeState: StateList? = null
    var mSelectedDistrict: LstDistrict? = null
    var mSelectedGender: CommonSpinner? = null
    var mSelectedIsSmartPhoneUser: CommonSpinner? = null
    var mSelectedIsWhatsApp: CommonSpinner? = null

    var countrylist = mutableListOf<CountryList>()
    var districtlist = mutableListOf<LstDistrict>()
    var stateList = mutableListOf<StateList>()
    var nativeStateList = mutableListOf<StateList>()

    var i = 0
    var j = 0
    var k = 0

    var successMsg = "Saved successful"
    var errorMsg = "Failed to save"

    var dob_date = ""
    var anniversary_dob_date = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(PersonalViewModel::class.java)
        return inflater.inflate(R.layout.personal_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*Country Response*/
        viewModel.getCountryListingLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.CountryList!!.isNotEmpty()) {
//                per_country_spinner.isEnabled = false
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

                per_country_spinner.adapter = CountryAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    countryList
                )

                countryList.forEach { countryListDetail ->
                    if (countryListDetail.CountryId == 15) {
                        per_country_spinner.setSelection(i)
                        i = 0
                        return@forEach
                    }
                    i++
                }

                LoadingDialogue.dismissDialog()

            }
        })

        /*State Response*/

        viewModel.getStateListingLiveData.observe(viewLifecycleOwner, Observer {
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

                per_state_spinner.adapter = StateAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    stateList
                )

                stateList.forEach { stateListDetail ->
                    if (stateListDetail.StateId == lstCustomerJsons[0].StateId) {
                        per_state_spinner.setSelection(i)
                        i = 0
                        return@forEach
                    }
                    i++
                }
            }

            LoadingDialogue.dismissDialog()

        })

        /*Native State Response*/

        viewModel.getStateListingLiveData.observe(viewLifecycleOwner, Observer {
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

                per_native_state_spinner.adapter = StateAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    stateList
                )

                stateList.forEach { stateListDetail ->
                    if (stateListDetail.StateId == lstCustomerJsons[0].NativeStateId) {
                        per_native_state_spinner.setSelection(i)
                        i = 0
                        return@forEach
                    }
                    i++
                }
            }

            LoadingDialogue.dismissDialog()

        })

        /*District Response*/

        viewModel.getDistrictListingLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.lstDistrict!!.isNotEmpty()) {
                val districtList = java.util.ArrayList<LstDistrict>()
                var i = 0
                it.lstDistrict.forEachIndexed { index, lstStateDetail ->
                    val stateStatus = LstDistrict()
                    stateStatus.DistrictId = it.lstDistrict[index].DistrictId
                    stateStatus.DistrictName = it.lstDistrict[index].DistrictName
                    districtList.add(stateStatus)
                }

                val districtStatus = LstDistrict()
                districtStatus.DistrictId = -1
                districtStatus.DistrictName = "Select District"

                districtList.add(0, districtStatus)

                per_district_spinner.adapter = DistrictAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    districtList
                )

                districtList.forEach { districtListDetail ->
                    if (districtListDetail.DistrictId == lstCustomerJsons[0].DistrictId) {
                        per_district_spinner.setSelection(i)
                        i = 0
                        return@forEach
                    }
                    i++
                }
            }

            LoadingDialogue.dismissDialog()

        })

        viewModel.getPersonalLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.ReturnMessage!!.split(":")[0].toInt() > 0) {
                (activity as EnrollmentActivity).snackBar(
                    "Update Successful",
                    R.color.primary_dark_blue
                )
                findNavController().popBackStack()
            } else {
                (activity as EnrollmentActivity).snackBar("Failed to Update", R.color.red)
                findNavController().popBackStack()
            }
            LoadingDialogue.dismissDialog()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadingDialogue.showDialog(requireContext())

        val genderList: java.util.ArrayList<CommonSpinner> = java.util.ArrayList<CommonSpinner>()

        val defaultgender = CommonSpinner()
        defaultgender.id = -1
        defaultgender.name = "Select Gender"

        val gender1 = CommonSpinner()
        gender1.id = 1
        gender1.name = "Male"

        val gender2 = CommonSpinner()
        gender2.id = 2
        gender2.name = "Female"

        genderList.add(defaultgender)
        genderList.add(gender1)
        genderList.add(gender2)


        val smartList: java.util.ArrayList<CommonSpinner> = java.util.ArrayList<CommonSpinner>()

        val smart1 = CommonSpinner()
        smart1.id = 0
        smart1.name = "No"

        val smart2 = CommonSpinner()
        smart2.id = 1
        smart2.name = "Yes"

        smartList.add(smart1)
        smartList.add(smart2)


        val whatsappList: java.util.ArrayList<CommonSpinner> = java.util.ArrayList<CommonSpinner>()

        val whatsapp1 = CommonSpinner()
        whatsapp1.id = 0
        whatsapp1.name = "No"

        val whatsapp2 = CommonSpinner()
        whatsapp2.id = 1
        whatsapp2.name = "Yes"

        whatsappList.add(whatsapp1)
        whatsappList.add(whatsapp2)


        per_gender_spinner.adapter = CommonAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            genderList
        )
        per_whatsapp_spinner.adapter = CommonAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            whatsappList
        )
        per_smart_phone_user.adapter = CommonAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            smartList
        )


        val bundle = this.arguments
        if (bundle != null) {
            lstCustomerJsons =
                arguments?.getSerializable("PERSONAL_DETAIL") as ArrayList<LstCustomerJsons>

            if (!lstCustomerJsons.isNullOrEmpty()) {

                personal_submit.text = "Update"

                successMsg = "Update successful"
                errorMsg = "Failed to update"

                per_address.setText(lstCustomerJsons[0].Address1)
                per_pin.setText(lstCustomerJsons[0].Zip)
                if (lstCustomerJsons[0].JDOB != null) {
                    per_birthday.text =
                        AppController.dateAPIFormat(lstCustomerJsons[0].JDOB!!.split(" ")[0])
                } else {
                    per_birthday.hint = "DD/MM/YYYY"
                }
                if (lstCustomerJsons[0].Anniversary != null) {
                    per_aniversary.text =
                        AppController.dateAPIFormat(lstCustomerJsons[0].Anniversary!!.split(" ")[0])
                } else {
                    per_aniversary.hint = "DD/MM/YYYY"
                }
                genderList.forEach { genderListDetail ->
                    if (genderListDetail.name == lstCustomerJsons[0].Gender) {
                        per_gender_spinner.setSelection(i)
                        i = 0
                        return@forEach
                    }
                    i++
                }

                if (lstCustomerJsons[0].IsSmartphoneUser!!) {
                    per_smart_phone_user.setSelection(1)
                } else {
                    per_smart_phone_user.setSelection(0)
                }

                if (lstCustomerJsons[0].IsWhatsappUser!!) {
                    per_whatsapp_spinner.setSelection(1)
                } else {
                    per_whatsapp_spinner.setSelection(0)
                }

            }

        }

        per_country_spinner.onItemSelectedListener = this
        per_state_spinner.onItemSelectedListener = this
        per_district_spinner.onItemSelectedListener = this
        per_native_state_spinner.onItemSelectedListener = this
        per_gender_spinner.onItemSelectedListener = this
        per_smart_phone_user.onItemSelectedListener = this
        per_whatsapp_spinner.onItemSelectedListener = this

        per_birthday.setOnClickListener(this)
        per_aniversary.setOnClickListener(this)
        personal_submit.setOnClickListener(this)

        viewModel.setCountryRequest(
            CountryRequest(
                SortColumn = "COUNTRY_NAME",
                SortOrder = "ASC",
                StartIndex = 1
            )
        )


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {

            R.id.per_country_spinner -> {
                mSelectedCountry = parent.getItemAtPosition(position) as CountryList

                if (mSelectedCountry!!.CountryId!! > 0) {
                    LoadingDialogue.showDialog(requireContext())
                    viewModel.setStateRequest(
                        StateRequest(
                            ActionType = "2",
                            CountryID = mSelectedCountry!!.CountryId!!,
                            IsActive = "true"
                        )
                    )

                    viewModel.setNativeStateRequest(
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

                    per_state_spinner.adapter = StateAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        stateList
                    )

                    if (nativeStateList.size > 0) {
                        nativeStateList.clear()
                    }

                    nativeStateList.add(
                        0, StateList(
                            StateName = "Select State",
                            StateId = -1
                        )
                    )

                    per_native_state_spinner.adapter = StateAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        stateList
                    )
                }

            }

            R.id.per_state_spinner -> {
                mSelectedState = parent.getItemAtPosition(position) as StateList

                if (mSelectedState!!.StateId!! > 0) {

                    LoadingDialogue.showDialog(requireContext())

                    viewModel.setDistrictRequest(
                        DistrictRequest(
                            StateId = mSelectedState!!.StateId.toString()
                        )
                    )

                } else {

                    if (districtlist.size > 0) {
                        districtlist.clear()
                    }

                    districtlist.add(
                        0, LstDistrict(
                            DistrictName = "Select District",
                            DistrictId = -1
                        )
                    )

                    per_district_spinner.adapter = DistrictAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        districtlist
                    )
                }
            }

            R.id.per_district_spinner -> {
                mSelectedDistrict = parent.getItemAtPosition(position) as LstDistrict
            }

            R.id.per_native_state_spinner -> {
                mSelectedNativeState = parent.getItemAtPosition(position) as StateList
            }

            R.id.per_gender_spinner -> {
                mSelectedGender = parent.getItemAtPosition(position) as CommonSpinner
            }

            R.id.per_smart_phone_user -> {
                mSelectedIsSmartPhoneUser = parent.getItemAtPosition(position) as CommonSpinner
            }

            R.id.per_whatsapp_spinner -> {
                mSelectedIsWhatsApp = parent.getItemAtPosition(position) as CommonSpinner
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.per_birthday -> {
                DatePickerBox.date(activity) {
                    dob_date = AppController.dateAPIFormats(it)!!
                    per_birthday.text = it
                    lstCustomerJsons[0].JDOB = AppController.dateAPIFormats(it)!!
                }
            }

            R.id.per_aniversary -> {
                DatePickerBox.date(activity) {
                    anniversary_dob_date = AppController.dateAPIFormats(it)!!
                    per_aniversary.text = it
                    lstCustomerJsons[0].Anniversary = AppController.dateAPIFormats(it)!!
                }
            }

            R.id.personal_submit -> {
                if (per_address.text.toString().isEmpty()) {
                    per_address.error = "Enter Address"
                }
                /*else if(mSelectedCountry!=null && mSelectedCountry!!.CountryId==-1){
                    (activity as EnrollmentActivity).snackBar("Select Country", R.color.red)
                }*/
                else if (mSelectedState == null || mSelectedState!!.StateId == -1) {
                    (activity as EnrollmentActivity).snackBar("Select State", R.color.red)
                }
                /*else if(mSelectedDistrict==null || mSelectedDistrict!!.DistrictId==-1){
                    (activity as EnrollmentActivity).snackBar("Select District", R.color.red)
                }*/
                else if (per_pin.text.toString().isEmpty()) {
                    per_pin.error = "Enter Pincode"
                }
                /*else if(mSelectedNativeState==null || mSelectedNativeState!!.StateId==-1){
                    (activity as EnrollmentActivity).snackBar("Select Native State", R.color.red)
                }else if(per_birthday.text.toString().isEmpty()){
                    per_birthday.error="Select Birthday"
                }else if(per_aniversary.text.toString().isEmpty()){
                    per_aniversary.error="Select Anniversary"
                }else if(mSelectedGender==null || mSelectedGender!!.id==-1){
                    (activity as EnrollmentActivity).snackBar("Select Gender", R.color.red)
                }else if(mSelectedIsSmartPhoneUser==null || mSelectedIsSmartPhoneUser!!.id==-1){
                    (activity as EnrollmentActivity).snackBar("Select Smart Phone User", R.color.red)
                }else if(mSelectedIsWhatsApp==null || mSelectedIsWhatsApp!!.id==-1){
                    (activity as EnrollmentActivity).snackBar("Select WhatsApp", R.color.red)
                }*/
                else {
                    LoadingDialogue.showDialog(requireContext())
                    viewModel.setUpdateProfileRequest(
                        PersonalSaveUpdateRequest(
                            ActionType = "2", ActorId = lstCustomerJsons[0].UserId.toString(),
                            ObjCustomerss(
                                Address1 = per_address.text.toString(),
                                AddressId = lstCustomerJsons[0].AddressId.toString(),
                                CountryId = mSelectedCountry!!.CountryId.toString(),
                                CustomerId = lstCustomerJsons[0].CustomerId.toString(),
                                CustomerZip = per_pin.text.toString(),
                                JDOB = AppController.dateAPIFormats(per_birthday.text.toString()),
                                DistrictId = mSelectedDistrict!!.DistrictId.toString(),
                                LoyaltyId = lstCustomerJsons[0].LoyaltyId.toString(),
                                MerchantId = lstCustomerJsons[0].MerchantId.toString(),
                                NativeStateId = mSelectedNativeState!!.StateId.toString(),
                                StateId = mSelectedState!!.StateId.toString()
                            ), ObjCustomerDetailss(
                                JAnniversary = AppController.dateAPIFormats(per_aniversary.text.toString()),
                                CustomerDetailId = lstCustomerJsons[0].CustomerDetailId.toString(),
                                Gender = mSelectedGender!!.name!!,
                                IsSmartphoneUser = mSelectedIsSmartPhoneUser!!.id!!.toString(),
                                IsWhatsappUser = mSelectedIsWhatsApp!!.id!!.toString()
                            )
                        )
                    )
                }
            }

        }

    }

}