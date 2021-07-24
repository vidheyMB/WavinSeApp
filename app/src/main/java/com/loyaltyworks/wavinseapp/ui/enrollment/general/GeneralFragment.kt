package com.loyaltyworks.wavinseapp.ui.enrollment.general

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity.Companion.TAG
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.edit_address_fragment.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.general_fragment.*
import kotlinx.android.synthetic.main.general_fragment.general_profile_photo
import kotlinx.android.synthetic.main.item_enrollment.*
import kotlinx.android.synthetic.main.new_ticket_fragment.*
import kotlinx.android.synthetic.main.selected_retailer_detail_fragment.*

class GeneralFragment : Fragment() ,View.OnClickListener, AdapterView.OnItemSelectedListener{


    private lateinit var viewModel: GeneralViewModel

    private var lstCustomerJsons: ArrayList<LstCustomerJsons>?=null


    var mSelectedLanguage: CommonSpinner? = null
    var mSelectedGrade: CommonSpinner? = null
    var mSelectedCustType: CommonSpinner? = null
    var mSelectedBranch: CommonSpinner? = null
    var mSelectedCategory: CommonSpinner? = null

    var customerTypeList = mutableListOf<CommonSpinner>()

    var customerTypeID =-1
    var customerDistributorID =-1
    var languageID =-1
    var customerCategoryID =-1
    var gradeID =-1

    var isNewImage = 0

    var successMsg = ""
    var errorMsg = ""

    var actionType = "0"
    private var mProfileImagePath = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GeneralViewModel::class.java)
        return inflater.inflate(R.layout.general_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            if(bundle.getString("MOBILENUMBER").toString().toString().isEmpty()) {
                lstCustomerJsons = arguments?.getSerializable("GENERAL_DETAIL") as ArrayList<LstCustomerJsons>
                if (lstCustomerJsons!=null) {

                    general_submit.text = "Update"

                    successMsg = "Update successful"
                    errorMsg = "Failed to update"

                    general_mem_ship_host.visibility = View.VISIBLE

                    general_mobile.isClickable = false
                    general_mem_id.isClickable = false
                    general_email.isClickable = false

                    general_mem_id.setText(lstCustomerJsons!![0].LoyaltyId)
                    general_first_nm.setText(lstCustomerJsons!![0].FirstName)
                    general_mobile.setText(lstCustomerJsons!![0].Mobile)
                    general_mobile2.setText(lstCustomerJsons!![0].Mobile_Two)
                    general_email.setText(lstCustomerJsons!![0].Email)
                    general_nominee.setText(lstCustomerJsons!![0].Nominee)
                    per_birthday.text = lstCustomerJsons!![0].JDOB
                    if(lstCustomerJsons!![0].ProfilePicture!=null) {
                        Log.d(
                            TAG,
                            "onBindViewHolder: ${BuildConfig.PROFILE_IMAGE_BASE + lstCustomerJsons!![0].ProfilePicture!!}"
                        )
                        Glide.with(requireContext())
                            .load(
                                BuildConfig.PROFILE_IMAGE_BASE + lstCustomerJsons!![0].ProfilePicture!!.replace(
                                    "~/UploadFiles/CustomerImage/",
                                    ""
                                )
                            )
                            .placeholder(R.drawable.ic_person)
                            .error(R.drawable.ic_person)
                            .apply(RequestOptions().transform(CircleCrop()))
                            .into(general_profile_photo)
                    }
                    general_cus_type_spinner.isEnabled = false
                    general_first_nm.isEnabled = false
                    general_category.isEnabled = false
                    general_category.isClickable = false
                    general_grade_spinner.isClickable = false
                    general_language.isClickable = false
                    general_grade_spinner.isEnabled = true

                } else {

                    successMsg = "Saved successful"
                    errorMsg = "Failed to save"

                    general_mem_ship_host.visibility = View.GONE
                }
            }else {
                general_mobile.setText(bundle.getString("MOBILENUMBER").toString().toString())
                general_mem_ship_host.visibility = View.GONE
            }
        }


        /*Category listing*/
        viewModel.getCustomerCategoryLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {
                val categoryList = java.util.ArrayList<CommonSpinner>()
                var i = 0
                it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

                    val tierStatus = CommonSpinner()
                    tierStatus.id = it.lstAttributesDetails[index].AttributeId
                    tierStatus.name = it.lstAttributesDetails[index].AttributeValue
                    categoryList.add(tierStatus)

                }

                val customerCategoryStatus = CommonSpinner()
                customerCategoryStatus.id = -1
                customerCategoryStatus.name = "Select Category"

                categoryList.add(0, customerCategoryStatus)

                general_category.adapter = CommonAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    categoryList
                )

                if (lstCustomerJsons != null) {
                    categoryList.forEach { categoryListDetail ->
                        if (categoryListDetail.id == lstCustomerJsons!![0].CustomerCategoryId) {
                            general_category.setSelection(i)
                            i = 0
                            return@forEach
                        }
                        i++
                    }
                }

            }
        })

        /*Distributor listing*/
        viewModel.getDistributorLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null && !it.lstAttributesDetails.isNullOrEmpty()) {
                val distributorList = java.util.ArrayList<CommonSpinner>()
                var j = 0
                it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->
                    val distributorStatus = CommonSpinner()
                    distributorStatus.id = it.lstAttributesDetails[index].AttributeId
                    distributorStatus.name = it.lstAttributesDetails[index].AttributeValue
                    distributorList.add(distributorStatus)

                }

//                val customerCategoryStatus = CommonSpinner()
//                customerCategoryStatus.id = -1
//                customerCategoryStatus.name = "Select Distributor"
//
//                distributorList.add(0, customerCategoryStatus)

                general_branch.adapter = CommonAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    distributorList
                )

                if (lstCustomerJsons != null) {
                    distributorList.forEach { distributorTypeDetail ->
                        if (distributorTypeDetail.id == lstCustomerJsons!![0].LocationId) {
                            general_branch.setSelection(j)
                            j = 0
                            return@forEach
                        }
                        j++
                    }
                }
            }

        })


        viewModel.getTierListingLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                if (!it.lstAttributesDetails.isNullOrEmpty()) {
                    var j = 0
                    val gradeList = java.util.ArrayList<CommonSpinner>()

                    it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

                        if (it.lstAttributesDetails[index].AttributeType.equals("CustomerGrade")/* && it.lstAttributesDetails[index].AttributeId == 2*/) {
                            val tierStatus = CommonSpinner()
                            tierStatus.id = it.lstAttributesDetails[index].AttributeId
                            tierStatus.name = it.lstAttributesDetails[index].AttributeValue
                            gradeList.add(tierStatus)

                        }
                    }

                    val tierStatus = CommonSpinner()
                    tierStatus.id = -1
                    tierStatus.name = "Select Tier"

                    gradeList.add(0, tierStatus)

                    general_grade_spinner.adapter = CommonAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        gradeList
                    )

                    if (lstCustomerJsons != null) {
                        gradeList.forEach { gradeTypeDetail ->
                            if (gradeTypeDetail.id == lstCustomerJsons!![0].Customer_Grade_ID) {
                                general_grade_spinner.setSelection(j)
                                j = 0
                                return@forEach
                            }
                            j++
                        }
                    }

                }

            })

        /*Customer type listing*/
        viewModel.getCustomerTypeListingLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.lstAttributesDetails.isNullOrEmpty()) {
                var i = 0
                val customerTypeList = java.util.ArrayList<CommonSpinner>()

                it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

                    val customerTypeStatus = CommonSpinner()
                    customerTypeStatus.id = it.lstAttributesDetails[index].AttributeId
                    customerTypeStatus.name = it.lstAttributesDetails[index].AttributeValue
                    customerTypeList.add(customerTypeStatus)

                }

                val tierStatus = CommonSpinner()
                tierStatus.id = -1
                tierStatus.name = "Select Customer Type"

                customerTypeList.add(0, tierStatus)

                general_cus_type_spinner.adapter = CommonAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    customerTypeList
                )

                if (lstCustomerJsons != null) {
                    customerTypeList.forEach { customerTypeDetail ->
                        if (customerTypeDetail.id == lstCustomerJsons!![0].CustomerTypeID) {
                            general_cus_type_spinner.setSelection(i)
                            i = 0
                            return@forEach
                        }
                        i++
                    }
                }

            }

        })

        /*language type listing*/
        viewModel.getLanguageTypeListingLiveData.observe(viewLifecycleOwner, Observer {

            if (!it.lstAttributesDetails.isNullOrEmpty()) {
                var i = 0
                val languageList = java.util.ArrayList<CommonSpinner>()

                it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

                    val languageStatus = CommonSpinner()
                    languageStatus.id = it.lstAttributesDetails[index].AttributeId
                    languageStatus.name = it.lstAttributesDetails[index].AttributeValue
                    languageList.add(languageStatus)
                    return@forEachIndexed

                }

                val tierStatus = CommonSpinner()
                tierStatus.id = -1
                tierStatus.name = "Select Language"

                languageList.add(0, tierStatus)

                general_language.adapter = CommonAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    languageList
                )

                if (lstCustomerJsons != null) {
                    languageList.forEach { languageListDetail ->
                        if (languageListDetail.id == lstCustomerJsons!![0].LanguageId) {
                            general_language.setSelection(i)
                            i = 0
                            return@forEach
                        }
                        i++
                    }
                }

            }

        })

        /*Update and Save General Response*/
        viewModel.getGeneralSubmitLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.ReturnMessage!!.split(":")[0].toInt() > 0) {
                (activity as EnrollmentActivity).snackBar(
                    successMsg,
                    R.color.primary_dark_blue
                )
                var bundle = Bundle()
                var customerID = it!!.ReturnMessage!!.split(":")[2]
                Log.d(TAG, "customerID: $customerID")
                bundle.putString("CustomerID", customerID)
                findNavController().navigate(
                    R.id.action_generalFragment_to_selectedRetailerDetailFragment,
                    bundle
                )
            } else {
                (activity as EnrollmentActivity).snackBar(errorMsg, R.color.red)
                findNavController().navigate(R.id.action_generalFragment_to_selectedRetailerDetailFragment)
            }
            LoadingDialogue.dismissDialog()
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Customer Category Request*/
        viewModel.setCustomerCategoryRequest(
            AttributeRequest(
                ActionType = "43", ActorId = PreferenceHelper.getLoginDetails(
                    requireContext()
                )!!.UserList!![0].UserId.toString()
            )
        )

        /*Distributor Request*/
        viewModel.setDistributorCategoryRequest(
            AttributeRequest(
                ActionType = "55", ActorId = (activity as EnrollmentActivity).customerList!!.CustomerId.toString()
            )
        )

        /*Tier Type request*/
        viewModel.getTierListing(TierRequest(ActionType = "7"))

        /*Customer Type Request*/
        viewModel.getCustomerTypeListing(TierRequest(ActionType = "33"))

        /*Language Type Listing*/
        viewModel.getLanguageTypeListing(TierRequest(ActionType = "24"))

        general_submit.setOnClickListener(this)
        general_photo_btn.setOnClickListener(this)

        general_language.onItemSelectedListener = this
        general_cus_type_spinner.onItemSelectedListener = this
        general_grade_spinner.onItemSelectedListener = this
        general_branch.onItemSelectedListener = this
        general_category.onItemSelectedListener = this

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.general_submit -> {
                if (general_submit.text.toString().equals("Update", true)) {
                    if (general_mem_id.text.toString().isEmpty()) {
                        general_mem_id.error = "Membership Id is required"
                    } else {
                        validateField()
                    }
                } else {
                    validateField()
                }
            }
            R.id.general_photo_btn -> {
                // Browse Image or Files
                FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                    FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        val uri = fileSelectorData.uri
                        Log.d(TAG, "onResponse: $uri")
                        mProfileImagePath = fileSelectorData.responseInBase64!!
//                        general_profile_photo.setImageBitmap(fileSelectorData.thumbnail)
                        Glide.with(requireContext())
                            .load(uri)
                            .transform(CircleCrop())
                            .into(general_profile_photo)

                        if (mProfileImagePath.isNotEmpty()) {
                            isNewImage = 1
                        } else {
                            isNewImage = 0
                        }
                    }
                })
            }
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun validateField() {
       /* if (mProfileImagePath.isEmpty()){
            (activity as EnrollmentActivity).snackBar("Select Profile Photo", R.color.red)
        } else*/
        if (customerTypeID == -1){
            (activity as EnrollmentActivity).snackBar("Select Customer Type", R.color.red)
        } else if(general_first_nm.text.toString().isEmpty()){
            general_first_nm.error = "Name is required"
        } else if(general_mobile.text.toString().isEmpty()){
            general_mobile.error = "Mobile number 1 is required"
        } else if (!TextUtils.isEmpty(general_email.text.toString()) && general_email.text.toString().length > 6 && !isValidEmail(general_email.text.toString()) ) {
//            if (!isValidEmail(general_email.text.toString()) && general_email.text.toString().length > 6) {
                general_email.error = "Enter valid email Id"
//                (activity as EnrollmentActivity).snackBar("Enter valid email Id", R.color.red)
//            }
        } else if(customerDistributorID == -1){
            (activity as EnrollmentActivity).snackBar("Select Distributor", R.color.red)
        } else if(languageID == -1){
            (activity as EnrollmentActivity).snackBar("Select Language", R.color.red)
        } else if(customerCategoryID == -1){
            (activity as EnrollmentActivity).snackBar("Select Category", R.color.red)
        } else if(gradeID == -1){
            (activity as EnrollmentActivity).snackBar("Select Tier", R.color.red)
        } else{
            var loyaltyID = ""
            var customerID = ""
            var customerDetailID = ""
            var isActive = ""
            LoadingDialogue.showDialog(requireContext())
            if(lstCustomerJsons!=null){
                customerID = lstCustomerJsons!![0].CustomerId.toString()
                loyaltyID = lstCustomerJsons!![0].LoyaltyId.toString()
                customerDetailID = lstCustomerJsons!![0].CustomerDetailId.toString()
                isActive = "1"
            }else{
                customerID = "0"
                loyaltyID = general_mobile.text.toString()
                customerDetailID = "0"
                isActive = "0"
            }
            viewModel.setGeneralUpdateRequest(
                GeneralRequest(
                    ActionType = "1", ActorId = PreferenceHelper.getLoginDetails(
                        requireContext()
                    )!!.UserList!![0].UserId.toString(), IsActive = isActive,
                    ObjCustomers(
                        CustomerCategoryId = customerCategoryID.toString(),
                        CustomerMobile = general_mobile.text.toString(),
                        CustomerEmail = general_email.text.toString(),
                        CustomerId = customerID,
                        CustomerTypeId = customerTypeID.toString(),
                        Customer_Grade_ID = gradeID.toString(),
                        FirstName = general_first_nm.text.toString(),
                        LoyaltyId = loyaltyID,
                        LoyaltyIdAutoGen = "0",
                        LocationId = customerDistributorID.toString(),
                        MerchantId = 1,
                        Mobile_Two = general_mobile2.text.toString(),
                        MobilePrefix = "+91",
                        RegistrationSource = "3"
                    ),
                    ObjCustomerDetails(
                        IsNewProfilePicture = isNewImage.toString(),
                        LanguageID = languageID.toString(),
                        CustomerDetailId = customerDetailID,
                        ProfilePicture = mProfileImagePath
                    )
                )
            )
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent!!.id){

            R.id.general_language -> {
                mSelectedLanguage = parent.getItemAtPosition(position) as CommonSpinner
                languageID = mSelectedLanguage!!.id!!
            }

            R.id.general_cus_type_spinner -> {
                mSelectedCustType = parent.getItemAtPosition(position) as CommonSpinner
                customerTypeID = mSelectedCustType!!.id!!
            }

            R.id.general_grade_spinner -> {
                mSelectedGrade = parent.getItemAtPosition(position) as CommonSpinner
                gradeID = mSelectedGrade!!.id!!
            }

            R.id.general_branch -> {
                mSelectedBranch = parent.getItemAtPosition(position) as CommonSpinner
                customerDistributorID = mSelectedBranch!!.id!!
            }

            R.id.general_category -> {
                mSelectedCategory = parent.getItemAtPosition(position) as CommonSpinner
                customerCategoryID = mSelectedCategory!!.id!!
            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}