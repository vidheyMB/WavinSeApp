package com.loyaltyworks.wavinseapp.ui.enrollment.identification

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity.Companion.TAG
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.general_fragment.*
import kotlinx.android.synthetic.main.identification_fragment.*
import kotlinx.android.synthetic.main.selected_retailer_detail_fragment.*

class IdentificationFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {


    private lateinit var viewModel: IdentificationViewModel
    private var lstCustomerIdentityInfo: ArrayList<LstCustomerIdentityInfo>?=null
    private  var lstCustomerJsons: ArrayList<LstCustomerJsons>?=null

    var mAddressProoffilePath = ""
    var mIdentityProofFilePath = ""

    var isNewAddressImage = 0
    var isNewIdentityImage = 0

    var successMsg = "Saved successful"
    var errorMsg = "Failed to save"

    var mSelectedAddresProof: CommonSpinner? = null
    var mSelectedIdentificatonProof: CommonSpinner? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(IdentificationViewModel::class.java)
        return inflater.inflate(R.layout.identification_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*AddressProof Identification Listing Response*/
        viewModel.getAddreseProofIdentificationListingLiveData.observe(
            viewLifecycleOwner,
            Observer {
                if (!it.lstAttributesDetails.isNullOrEmpty()) {
                    var j = 0
                    val gradeList = java.util.ArrayList<CommonSpinner>()

                    it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

//                        if (it.lstAttributesDetails[index].AttributeType.equals("IdentificationType") && it.lstAttributesDetails[index].AttributeId == 4) {
                        val tierStatus = CommonSpinner()
                        tierStatus.id = it.lstAttributesDetails[index].AttributeId
                        tierStatus.name = it.lstAttributesDetails[index].AttributeValue
                        tierStatus.type = "Address"
                        gradeList.add(tierStatus)
//                            return@forEachIndexed
//                        }
                    }

                    val tierStatus = CommonSpinner()
                    tierStatus.id = -1
                    tierStatus.name = "Select Address Proof"
                    tierStatus.type = "Address"

                    gradeList.add(0, tierStatus)

                    idn_add_proof_spinner.adapter = CommonAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        gradeList
                    )

                    if (lstCustomerIdentityInfo != null) {
                        gradeList.forEach { gradeTypeDetail ->
                            if (gradeTypeDetail.id == lstCustomerIdentityInfo!![0].IdentityID) {
                                idn_add_proof_spinner.setSelection(j)
                                j = 0
                                return@forEach
                            }
                            j++
                        }
                    }

                }
            })

        /*IdentityProof Identification Listing Response*/
        viewModel.getIdentityProofIdentificationListingLiveData.observe(
            viewLifecycleOwner,
            Observer {
                if (!it.lstAttributesDetails.isNullOrEmpty()) {
                    var j = 0
                    val gradeList = java.util.ArrayList<CommonSpinner>()

                    it.lstAttributesDetails.forEachIndexed { index, lstAttributesDetail ->

//                        if (it.lstAttributesDetails[index].AttributeType.equals("IdentificationType") && it.lstAttributesDetails[index].AttributeId != 6 &&
//                            it.lstAttributesDetails[index].AttributeId != 4 && it.lstAttributesDetails[index].AttributeId != 7
//                        ) {
                        val tierStatus = CommonSpinner()
                        tierStatus.id = it.lstAttributesDetails[index].AttributeId
                        tierStatus.name = it.lstAttributesDetails[index].AttributeValue
                        tierStatus.type = "Identity"
                        gradeList.add(tierStatus)
//                            return@forEachIndexed
//                        }
                    }

                    val tierStatus = CommonSpinner()
                    tierStatus.id = -1
                    tierStatus.name = "Select Identity Proof"
                    tierStatus.type = "Identity"

                    gradeList.add(0, tierStatus)

                    idn_identity_proof_spinner.adapter = CommonAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        gradeList
                    )

                    if (lstCustomerIdentityInfo != null) {
                        gradeList.forEach { gradeTypeDetail ->
                            if (gradeTypeDetail.id == lstCustomerIdentityInfo!![1].IdentityID) {
                                idn_identity_proof_spinner.setSelection(j)
                                j = 0
                                return@forEach
                            }
                            j++
                        }
                    }

                }
                LoadingDialogue.dismissDialog()
            })

        /*Identification Number existency check response*/
        viewModel.getIdentificationNumberExistencyCheckLiveData.observe(
            viewLifecycleOwner,
            Observer {
                if (it!!.CheckUserNameExistsResult != null) {
                    when (it.CheckUserNameExistsResult!!.ReturnValue) {
                        0 -> {
                            if (mSelectedAddresProof!!.type == "1" && mSelectedAddresProof!!.id!! > -1) {
                                viewModel.setIdentificationNumberCheckExistencyRequest(
                                    IdentificationNumberExistencyCheckRequest(
                                        ActionType = "240",
                                        ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                                        Locations(
                                            UserName = idn_add_proof_fld.text.toString(),
                                            BillingTypeID = 1,
                                            ProductID = mSelectedAddresProof!!.id
                                        )
                                    )
                                )
                            } else if (mSelectedIdentificatonProof!!.type == "2" && mSelectedIdentificatonProof!!.id!! > -1) {
                                saveUpdateIdentificationProof()
                            } else {
                                saveUpdateIdentificationProof()
                            }
                        }
                        1 -> {
                            if (mSelectedIdentificatonProof!!.type == "2") {
                                (activity as EnrollmentActivity).snackBar(
                                    "Identity proof already exists",
                                    R.color.red
                                )
                            } else {
                                (activity as EnrollmentActivity).snackBar(
                                    "Address proof already exists",
                                    R.color.red
                                )
                            }
                            LoadingDialogue.dismissDialog()
                        }
                    }
                }
            })

        viewModel.getSaveUpdateIdentificationLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.ReturnValue.toString().split(":")[0] > 0.toString()) {
                (activity as EnrollmentActivity).snackBar(
                    successMsg,
                    R.color.primary_color
                )

            } else {
                (activity as EnrollmentActivity).snackBar(
                    errorMsg,
                    R.color.red
                )
            }
            findNavController().popBackStack()
            LoadingDialogue.dismissDialog()
        })

    }

    private fun saveUpdateIdentificationProof() {
        var lstIdentityInfoList = mutableListOf<LstIdentityInfo>()


        var lstAddressInfo = LstIdentityInfo()

        lstAddressInfo.IdentityDocument = mAddressProoffilePath

       /* if(isNewAddressImage>0){
            lstAddressInfo.IdentityDocument = mAddressProoffilePath
        }else{
            lstAddressInfo.IdentityDocument = lstCustomerIdentityInfo!![0].IdentityDocument
        }*/
        lstAddressInfo.IdentityID = mSelectedAddresProof!!.id.toString()
        lstAddressInfo.IdentityNo = idn_add_proof_fld.text.toString()
        lstAddressInfo.IdentityType = mSelectedAddresProof!!.type
        lstAddressInfo.IsNewIdentity = isNewAddressImage.toString()
        lstIdentityInfoList.add(lstAddressInfo)

        var lstIdentityInfo = LstIdentityInfo()
        lstIdentityInfo.IdentityDocument = mIdentityProofFilePath


      /*  if(isNewIdentityImage>0){
            lstIdentityInfo.IdentityDocument = mIdentityProofFilePath
        }else{
            lstIdentityInfo.IdentityDocument = lstCustomerIdentityInfo!![1].IdentityDocument
        }*/
        lstIdentityInfo.IdentityID = mSelectedIdentificatonProof!!.id.toString()
        lstIdentityInfo.IdentityNo = idn_identity_numb_fld.text.toString()
        lstIdentityInfo.IdentityType = mSelectedIdentificatonProof!!.type
        lstIdentityInfo.IsNewIdentity = isNewIdentityImage.toString()
        lstIdentityInfoList.add(lstIdentityInfo)

        viewModel.setSaveUpdateRequest(
            IdentificationSaveUpdateRequest(
                ActionType = "3",
                ActorId = (activity as EnrollmentActivity).customerList!!.UserId.toString(),
                ObjCustomersss(
                    CustomerId = lstCustomerJsons!![0].CustomerId.toString(),
                    LoyaltyId = lstCustomerJsons!![0].LoyaltyId.toString(),
                    MerchantId = lstCustomerJsons!![0].MerchantId.toString()
                ),
                ObjCustomerDetailsss(CustomerDetailId = lstCustomerJsons!![0].CustomerDetailId.toString()),
                lstIdentityInfo = lstIdentityInfoList
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoadingDialogue.showDialog(requireContext())
        /*AddressProof Type request*/
        viewModel.getAddressProofListing(TierRequest(ActionType = "23"))
        /*IdentityProof Type request*/
        viewModel.getIdentityProofListing(TierRequest(ActionType = "23"))

        val bundle = arguments
        try {

            lstCustomerIdentityInfo = bundle?.getSerializable("IDENTIFICATION_DETAIL") as ArrayList<LstCustomerIdentityInfo>

            if (lstCustomerIdentityInfo!=null) {

                identification_submit.text = "Update"

                successMsg = "Update successful"
                errorMsg = "Failed to update"

                if(lstCustomerIdentityInfo!![0].IdentityType!!.contains("Address Proof", true)){
                    idn_add_proof_fld.setText(lstCustomerIdentityInfo!![0].IdentityNo)
                   if(lstCustomerIdentityInfo!![0].IdentityDocument!=null){
                       Glide.with(requireContext()).load(BuildConfig.PROMO_IMAGE_BASE + lstCustomerIdentityInfo!![0].IdentityDocument!!.replace("~","")).placeholder(R.drawable.ic_default_img).into(idn_add_attched_img)
                       Log.d(TAG, "onViewCreated: "+BuildConfig.PROMO_IMAGE_BASE + lstCustomerIdentityInfo!![0].IdentityDocument!!)
                   }
                    add_img_host.visibility = View.VISIBLE
                }

                if(lstCustomerIdentityInfo!![1].IdentityType!!.contains("Identity Proof", true)){
                    idn_identity_numb_fld.setText(lstCustomerIdentityInfo!![1].IdentityNo)
                    if(lstCustomerIdentityInfo!![1].IdentityDocument!=null){
                        Glide.with(requireContext()).load(BuildConfig.PROMO_IMAGE_BASE + lstCustomerIdentityInfo!![1].IdentityDocument!!.replace("~","")).placeholder(R.drawable.ic_default_img).into(idn_identity_attched_img)
                        Log.d(TAG, "onViewCreated: "+BuildConfig.PROMO_IMAGE_BASE + lstCustomerIdentityInfo!![1].IdentityDocument!!)
                    }
                    idenity_img_host.visibility = View.VISIBLE
                }

            }else{
                successMsg = "Saved successful"
                errorMsg = "Failed to save"
            }

        } catch (e: Exception){}

        lstCustomerJsons = bundle?.getSerializable("GENERAL_DETAIL") as ArrayList<LstCustomerJsons>

        idn_add_proof_spinner.onItemSelectedListener=this
        idn_identity_proof_spinner.onItemSelectedListener=this

        add_proof_attach.setOnClickListener(this)
        identity_proof_attach.setOnClickListener(this)
        identification_submit.setOnClickListener(this)

/*        idenity_img_host.setOnClickListener(this)
        idn_identity_numb_fld.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                viewModel.setIdentificationNumberCheckExistencyRequest(
                    IdentificationNumberExistencyCheckRequest(
                        ActionType = "240",
                        ActorId = (activity as EnrollmentActivity).customerList!!.CustomerId.toString(),
                        Locations(
                            UserName = idn_identity_numb_fld.text.toString(),
                            BillingTypeID = 2,
                            ProductID = mSelectedIdentificatonProof!!.id
                        )
                    )
                )
            }
            }*/

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent!!.id){
            R.id.idn_add_proof_spinner -> {
                mSelectedAddresProof = parent.getItemAtPosition(position) as CommonSpinner
            }

            R.id.idn_identity_proof_spinner -> {
                mSelectedIdentificatonProof = parent.getItemAtPosition(position) as CommonSpinner
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add_proof_attach -> {
                // Browse Image or Files
                FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                    FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        val uri = fileSelectorData.uri
                        Log.d(BaseActivity.TAG, "onResponse: $uri")
                        mAddressProoffilePath = fileSelectorData.responseInBase64!!
                        idn_add_attched_img.setImageBitmap(fileSelectorData.thumbnail)
                        add_img_host.visibility = View.VISIBLE
                        if (mAddressProoffilePath.isNotEmpty()) {
                            isNewAddressImage = 1
                        } else {
                            isNewAddressImage = 0
                        }
                    }
                })
            }

            R.id.identity_proof_attach -> {
                // Browse Image or Files
                FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                    FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        val uri = fileSelectorData.uri
                        Log.d(BaseActivity.TAG, "onResponse: $uri")
                        mIdentityProofFilePath = fileSelectorData.responseInBase64!!
                        idn_identity_attched_img.setImageBitmap(fileSelectorData.thumbnail)
                        idenity_img_host.visibility = View.VISIBLE
                        if (mIdentityProofFilePath.isNotEmpty()) {
                            isNewIdentityImage = 1
                        } else {
                            isNewIdentityImage = 0
                        }

                    }
                })
            }

            R.id.identification_submit -> {

                if(identification_submit.text.toString().equals("Update", true)){

                    saveUpdateIdentificationProof()

                } else{

                    if (validateNumber()) {

                        LoadingDialogue.showDialog(requireContext())
                        saveUpdateIdentificationProof()

                        /*if (mSelectedIdentificatonProof!!.id!! > -1) {
                            viewModel.setIdentificationNumberCheckExistencyRequest(
                                IdentificationNumberExistencyCheckRequest(
                                    ActionType = "240",
                                    ActorId = (activity as EnrollmentActivity).customerList!!.CustomerId.toString(),
                                    Locations(
                                        UserName = idn_identity_numb_fld.text.toString(),
                                        BillingTypeID = 2,
                                        ProductID = mSelectedIdentificatonProof!!.id
                                    )
                                )
                            )
                        }
                        else{

                            val addressProofNumber = idn_add_proof_fld.text.toString().toInt()

                            viewModel.setIdentificationNumberCheckExistencyRequest(
                                IdentificationNumberExistencyCheckRequest(
                                    ActionType = "240",
                                    ActorId = (activity as EnrollmentActivity).customerList!!.CustomerId.toString(),
                                    Locations(
                                        UserName = mSelectedIdentificatonProof!!.id.toString(),
                                        BillingTypeID = 1,
                                        ProductID = addressProofNumber
                                    )
                                )
                            )
                        }*/

                    }

                }

            }
        }
    }

    private fun validateNumber(): Boolean {

        /*
         *  Passport = 1
         *  Aadhaar Card = 2
         *  Voter ID = 3
         *  Driving License = 4
         *  Ration Card = 5
         *  Pan Card = 6
         *  Others = 7
         * */
        if (mSelectedAddresProof!!.id == -1 || mSelectedIdentificatonProof!!.id == -1) {
            (activity as EnrollmentActivity).snackBar(
                "Please add both of the identity proof and address proof.",
                R.color.red
            )
            return false
        }
        val identityProof: String = idn_identity_numb_fld.text.toString()
        val addressProof: String = idn_add_proof_fld.text.toString()
        var valid = true
        if (addressProof.isEmpty() && mAddressProoffilePath.isEmpty()) {
            (activity as EnrollmentActivity).snackBar(
                "Please add address proof number and attachment.",
                R.color.red
            )
            return false
        }


         if (TextUtils.isEmpty(addressProof)) {
            valid = validateAddressProofAndIdentity(
                addressProof,
                valid,
                mSelectedAddresProof!!,
                idn_add_proof_fld,
                "Enter address proof number"
            )
        }

        if (!TextUtils.isEmpty(addressProof)) {
            valid = validateAddressProofAndIdentity(
                addressProof,
                valid,
                mSelectedAddresProof!!,
                idn_add_proof_fld,
                "Enter valid address proof id"
            )
        }

        if (addressProof.isEmpty() && mAddressProoffilePath.isEmpty()) {
            if (lstCustomerIdentityInfo != null && TextUtils.isEmpty(lstCustomerIdentityInfo!![0].IdentityDocument)) {
                (activity as EnrollmentActivity).snackBar(
                    "Please add address proof attachment",
                    R.color.red
                )
                return false
            }

            (activity as EnrollmentActivity).snackBar(
                "Please add address proof number and attachment",
                R.color.red
            )

            return false

        }

       /* if (mAddressProoffilePath.isEmpty()) {
            (activity as EnrollmentActivity).snackBar(
                "Please add address proof attachment",
                R.color.red
            )
            return false
        }*/

          if (TextUtils.isEmpty(identityProof)) {
            valid = validateAddressProofAndIdentity(
                identityProof,
                valid,
                mSelectedIdentificatonProof!!,
                idn_identity_numb_fld,
                "Enter identity proof number"
            )

        }

        if (!TextUtils.isEmpty(identityProof)) {
            valid = validateAddressProofAndIdentity(
                identityProof,
                valid,
                mSelectedIdentificatonProof!!,
                idn_identity_numb_fld,
                "Enter valid identity proof id"
            )

        }

    /*    if (!TextUtils.isEmpty(identityProof) && TextUtils.isEmpty(mIdentityProofFilePath)) {
            if (lstCustomerIdentityInfo != null && TextUtils.isEmpty(lstCustomerIdentityInfo!![1].IdentityDocument)) {
                (activity as EnrollmentActivity).snackBar(
                    "Please add identity proof attachment",
                    R.color.red
                )
                return false
            }

                (activity as EnrollmentActivity).snackBar(
                    "Please add identity proof number and attachment",
                    R.color.red
                )
                return false

        }*/

       /* if (mIdentityProofFilePath.isEmpty()) {
            (activity as EnrollmentActivity).snackBar(
                "Please add identity proof attachment",
                R.color.red
            )
            return false
        }
*/
        return valid

    }

    private fun validateAddressProofAndIdentity(
        identityProof: String,
        valid: Boolean,
        mSelectedIdentity: CommonSpinner,
        mIdentityProofFld: EditText,
        s: String
    ): Boolean {
        var valid = valid
        when (mSelectedIdentity.id) {
            6 -> if (identityProof.length == 10) {
                var i = 0
                while (i < 5) {
                    if (!Character.isLetter(identityProof[i])) {
                        valid = false
                        mIdentityProofFld.requestFocus()
                        mIdentityProofFld.error = s
                        break
                    }
                    i++
                }
            } else {
                valid = false
                mIdentityProofFld.requestFocus()
                mIdentityProofFld.error = s
            }
            1 -> if (identityProof.length < 7 || identityProof.length > 9 || !Character.isLetter(
                    identityProof[0]
                )
            ) {
                valid = false
                mIdentityProofFld.requestFocus()
                mIdentityProofFld.error = s
            }
            2 -> if (identityProof.length == 12) {
                var i = 0
                while (i < identityProof.length) {
                    if (!Character.isDigit(identityProof[i])) {
                        valid = false
                        mIdentityProofFld.requestFocus()
                        mIdentityProofFld.error = s
                        break
                    }
                    i++
                }
            } else {
                valid = false
                mIdentityProofFld.requestFocus()
                mIdentityProofFld.error = s
            }
            4 -> if (identityProof.length < 10 || !Character.isLetter(identityProof[0]) || !Character.isLetter(
                    identityProof[1]
                )
            ) {
                valid = false
                mIdentityProofFld.requestFocus()
                mIdentityProofFld.error = s
            }
            else -> if (identityProof.length < 8) {
                valid = false
                mIdentityProofFld.requestFocus()
                mIdentityProofFld.error = s
            } else mIdentityProofFld.error = null
        }
        return valid
    }


}