package com.loyaltyworks.wavinseapp.ui.enrollment.selected_retailer_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity
import com.loyaltyworks.wavinseapp.ui.dashboard.DashboardViewModel
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.member_list_row.*
import kotlinx.android.synthetic.main.member_list_row.general_profile_photo
import kotlinx.android.synthetic.main.selected_retailer_detail_fragment.*

class SelectedRetailerDetailFragment : Fragment() {


    private lateinit var viewModel: SelectedRetailerDetailViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    private var lstCustomerIdentityInfo: ArrayList<LstCustomerIdentityInfo>?=null

    private  var lstCustomerJsons: ArrayList<LstCustomerJsons>?=null

    private var  lstCustomerOfficalInfoJson: ArrayList<LstCustomerOfficalInfoJson>?=null

    var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        viewModel = ViewModelProvider(this).get(SelectedRetailerDetailViewModel::class.java)
        return inflater.inflate(R.layout.selected_retailer_detail_fragment, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dashboardViewModel.getCustomerDetailsLiveData.observe(viewLifecycleOwner, Observer {
            if(it!=null && !it.CustomerDetailsResponseJson.isNullOrEmpty()){
                /*Selected Retailer Header Detail by CustomerID*/

//                if (!it.CustomerDetailsResponseJson[0].CreatedDate.toString().isNullOrEmpty()) {
//                    mem_list_date.text = it.CustomerDetailsResponseJson!![0].CreatedDate
//                }else{
//                    mem_list_date.text = "-"
//                }
//
//
//                if (!it.CustomerDetailsResponseJson!![0].PointsBalance.toString().isNullOrEmpty()) {
//                    mem_list_point.text = it.CustomerDetailsResponseJson!![0].PointsBalance.toString()
//                }
//                else{
//                    mem_list_point.text = "0"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].CustomerGrade.isNullOrEmpty()) {
//                    mem_list_tier.text =
//                        it.CustomerDetailsResponseJson!![0].CustomerGrade
//                }
//                else{
//                    mem_list_tier.text = "-"
//                }
//
//                if (it.CustomerDetailsResponseJson!![0].IsActive!!) {
//                    mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
//                    mem_list_status.text = "Active"
//                } else {
//                    mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
//                    mem_list_status.text = "Inactive"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].VerifiedName.isNullOrEmpty()) {
//                    mem_list_approve.text =
//                        it.CustomerDetailsResponseJson!![0].VerifiedName
//                }
//                else{
//                    mem_list_approve.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].FullName.isNullOrEmpty()){
//                    mem_list_username.text =
//                        it.CustomerDetailsResponseJson!![0].FullName
//                }
//                else{
//                    mem_list_username.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].LoyaltyId.isNullOrEmpty()) {
//                    mem_list_membershipId.text =
//                        it.CustomerDetailsResponseJson!![0].LoyaltyId
//                }
//                else {
//                    mem_list_membershipId.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].UserOneHeader.isNullOrEmpty()) {
//                    ASMHeader.text =
//                        it.CustomerDetailsResponseJson!![0].UserOneHeader
//                }
//                else {
//                    ASMHeader.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].AsmName.isNullOrEmpty()){
//                    mem_list_asm.text = it.CustomerDetailsResponseJson!![0].AsmName
//                }
//                else{
//                    mem_list_asm.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].UserTwoHeader.isNullOrEmpty()) {
//                    SEHeader.text =
//                        it.CustomerDetailsResponseJson!![0].UserTwoHeader
//                }
//                else{
//                    SEHeader.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].SeName.isNullOrEmpty()) {
//                    mem_list_se.text = it.CustomerDetailsResponseJson!![0].SeName
//                }
//                else{
//                    mem_list_se.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].CreatedByName.isNullOrEmpty()) {
//                    mem_list_createdby.text =
//                        it.CustomerDetailsResponseJson!![0].CreatedByName
//                }
//                else{
//                    mem_list_createdby.text = "-"
//                }
//
//                if (!it.CustomerDetailsResponseJson!![0].LocationName.isNullOrEmpty()) {
//                    mem_list_branch.text =
//                        it.CustomerDetailsResponseJson!![0].LocationName
//                }
//                else{
//                    mem_list_branch.text = "-"
//                }
//
//                if(it.CustomerDetailsResponseJson!![0].ProfilePicture!=null) {
//                    Log.d(
//                        BaseActivity.TAG,
//                        "onProfileImage: ${BuildConfig.PROFILE_IMAGE_BASE + it.CustomerDetailsResponseJson!![0].ProfilePicture!!.replace(
//                            "~/UploadFiles/CustomerImage/",
//                            ""
//                        )}"
//                    )
//                    Glide.with(requireContext())
//                        .load(
//                            BuildConfig.PROFILE_IMAGE_BASE + it.CustomerDetailsResponseJson!![0].ProfilePicture!!.replace(
//                                "~/UploadFiles/CustomerImage/",
//                                ""
//                            )
//                        )
//                        .transform(CircleCrop())
//                        .placeholder(R.drawable.ic_person)
//                        .error(R.drawable.ic_person)
////                        .apply(RequestOptions().transform(CircleCrop()))
//                        .into(general_profile_photo)
//                }
            }
        })


        viewModel.getSelectedRetailerDetailsByCustomerIDLiveData.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    /*General & Personal Detail By CustomerByID*/
                    if (!it.GetCustomerDetailsMobileAppResult.lstCustomerJson.isNullOrEmpty()) {
                        (activity as EnrollmentActivity).supportActionBar?.title = "Verification"
                        PreferenceHelper.setSECustomerDetails(requireContext(),it.GetCustomerDetailsMobileAppResult)

                        lstCustomerJsons = it.GetCustomerDetailsMobileAppResult.lstCustomerJson as ArrayList<LstCustomerJsons>

//                        if(!it.GetCustomerDetailsMobileAppResult.lstCustomerIdentityInfo.isNullOrEmpty()){
//                            lstCustomerIdentityInfo = it.GetCustomerDetailsMobileAppResult.lstCustomerIdentityInfo as ArrayList<LstCustomerIdentityInfo>?
//                        }

                        /*CustomerIdentityInfo Detail By CustomerByID*/
                        if (!it.GetCustomerDetailsMobileAppResult.lstCustomerIdentityInfo.isNullOrEmpty()) {
                            lstCustomerIdentityInfo =
                                it.GetCustomerDetailsMobileAppResult.lstCustomerIdentityInfo as ArrayList<LstCustomerIdentityInfo>
                        }

                        /*CustomerOfficalInfoJson Detail By CustomerByID*/
                        if (!it.GetCustomerDetailsMobileAppResult.lstCustomerOfficalInfoJson.isNullOrEmpty()) {
                            lstCustomerOfficalInfoJson = it.GetCustomerDetailsMobileAppResult.lstCustomerOfficalInfoJson as ArrayList<LstCustomerOfficalInfoJson>
                        }


                        if (!(activity as EnrollmentActivity).customerList!!.CreatedDate.toString().isNullOrEmpty()) {
                            mem_list_date.text = (activity as EnrollmentActivity).customerList!!.CreatedDate
                        }else{
                            mem_list_date.text = "-"
                        }


                        if (!(activity as EnrollmentActivity).customerList!!.PointsBalance.toString().isNullOrEmpty()) {
                            mem_list_point.text = (activity as EnrollmentActivity).customerList!!.PointsBalance.toString()
                        }
                        else{
                            mem_list_point.text = "0"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.CustomerGrade.isNullOrEmpty()) {
                            mem_list_tier.text =
                                (activity as EnrollmentActivity).customerList!!.CustomerGrade
                        }
                        else{
                            mem_list_tier.text = "-"
                        }

                        if ((activity as EnrollmentActivity).customerList!!.IsActive!!) {
                            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
                            mem_list_status.text = "Active"
                        } else {
                            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
                            mem_list_status.text = "Inactive"
                        }

                        if ((activity as EnrollmentActivity).customerList!!.VerifiedTypeId == 1) {
                            mem_list_verifyImg.setImageDrawable(
                                requireContext().resources.getDrawable(R.drawable.ic_active)
                            )
                            mem_list_approve.text = (activity as EnrollmentActivity).customerList!!.VerifiedName
                        } else {
                            mem_list_verifyImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
                            mem_list_approve.text = (activity as EnrollmentActivity).customerList!!.VerifiedName
                        }

                        if((activity as EnrollmentActivity).customerList!!.VerifiedName.equals("Verified", true)){
                            mem_list_verifyImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
                        }else{
                            mem_list_verifyImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
                        }

                        if(mem_list_status.text.toString().equals("Active", true)){
                            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
                        }else{
                            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
                        }


//                        if ((activity as EnrollmentActivity).customerList!!.IsActive!!) {
//                            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
//                            mem_list_status.text = "Active"
//                        } else {
//                            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
//                            mem_list_status.text = "Inactive"
//                        }

//                        if (!(activity as EnrollmentActivity).customerList!!.VerifiedName.isNullOrEmpty() ) {
//                            if((activity as EnrollmentActivity).customerList!!.VerifiedName.equals("Verified", true)){
//                                mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
//                                mem_list_approve.text = (activity as EnrollmentActivity).customerList!!.VerifiedName
//                            }else{
//                                mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
//                                mem_list_approve.text = (activity as EnrollmentActivity).customerList!!.VerifiedName
//                            }
//
//                        }
//                        else{
//                            mem_list_approve.text = "-"
//                        }

                        if (!(activity as EnrollmentActivity).customerList!!.FullName.isNullOrEmpty()){
                            mem_list_username.text =
                                (activity as EnrollmentActivity).customerList!!.FullName
                        }
                        else{
                            mem_list_username.text = "-"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.LoyaltyId.isNullOrEmpty()) {
                            mem_list_membershipId.text =
                                (activity as EnrollmentActivity).customerList!!.LoyaltyId
                        }
                        else {
                            mem_list_membershipId.text = "-"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.UserOneHeader.isNullOrEmpty()) {
                            ASMHeader.text =
                                (activity as EnrollmentActivity).customerList!!.UserOneHeader
                        }
                        else {
                            ASMHeader.text = "-"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.AsmName.isNullOrEmpty()){
                            mem_list_asm.text = (activity as EnrollmentActivity).customerList!!.AsmName
                        }
                        else{
                            mem_list_asm.text = "-"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.UserTwoHeader.isNullOrEmpty()) {
                            SEHeader.text =
                                (activity as EnrollmentActivity).customerList!!.UserTwoHeader
                        }
                        else{
                            SEHeader.text = "-"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.SeName.isNullOrEmpty()) {
                            mem_list_se.text = (activity as EnrollmentActivity).customerList!!.SeName
                        }
                        else{
                            mem_list_se.text = "-"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.CreatedByName.isNullOrEmpty()) {
                            mem_list_createdby.text =
                                (activity as EnrollmentActivity).customerList!!.CreatedByName
                        }
                        else{
                            mem_list_createdby.text = "-"
                        }

                        if (!(activity as EnrollmentActivity).customerList!!.LocationName.isNullOrEmpty()) {
                            mem_list_branch.text =
                                (activity as EnrollmentActivity).customerList!!.LocationName
                        }
                        else{
                            mem_list_branch.text = "-"
                        }

                        if((activity as EnrollmentActivity).customerList!!.ProfilePicture!=null) {
                            Log.d(
                                BaseActivity.TAG,
                                "onProfileImage: ${BuildConfig.PROFILE_IMAGE_BASE + (activity as EnrollmentActivity).customerList!!.ProfilePicture!!.replace(
                                    "~/UploadFiles/CustomerImage/",
                                    ""
                                )}"
                            )
                            Glide.with(requireContext())
                                .load(
                                    BuildConfig.PROFILE_IMAGE_BASE + (activity as EnrollmentActivity).customerList!!.ProfilePicture!!.replace(
                                        "~/UploadFiles/CustomerImage/",
                                        ""
                                    )
                                )
                                .transform(CircleCrop())
                                .placeholder(R.drawable.ic_person)
                                .error(R.drawable.ic_person)
//                        .apply(RequestOptions().transform(CircleCrop()))
                                .into(general_profile_photo)
                        }


                        /*Enable and Disable Content*/
                        rewardsCard.visibility = View.VISIBLE
                        general_complete_status.visibility = View.VISIBLE
                        row3.visibility = View.VISIBLE

                        personal_complete_status.visibility = View.GONE
                        identity_complete_status.visibility = View.GONE
                        bussiness_complete_status.visibility = View.GONE

                        if (!lstCustomerJsons!![0].Address1.isNullOrEmpty() && lstCustomerJsons!![0].StateId.toString()
                                .toInt() > 0 && !lstCustomerJsons!![0].Zip.isNullOrEmpty()
                        ) {
                            personalCard.isClickable = true
                            personal_complete_status.visibility = View.VISIBLE
                            personal_complete_status.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_check_complete))
                        }

                        if (lstCustomerIdentityInfo!=null) {
                            identificationCard.isClickable = true
                            identity_complete_status.visibility = View.VISIBLE
                            identity_complete_status.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_check_complete))
                        }

                        if(lstCustomerOfficalInfoJson!=null){
                            businessCard.isClickable = true
                            bussiness_complete_status.visibility = View.VISIBLE
                            bussiness_complete_status.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_check_complete))
                        }


                        if (lstCustomerJsons!![0].IsVerified == 1) {
                            rewardsCard.visibility = View.VISIBLE
                            row3.visibility = View.VISIBLE
                        } else {
                            rewardsCard.visibility = View.GONE
                            row3.visibility = View.GONE
                        }

                    } else {
                        /*Enable and Disable Content*/
                        rewardsCard.visibility = View.GONE
                        row3.visibility = View.GONE
                        personalCard.isClickable = false
                        identificationCard.isEnabled = false
                        businessCard.isEnabled = false
                        mappingCard.isEnabled = false
                    }


                } else {
                    rewardsCard.visibility = View.GONE
                    row3.visibility = View.GONE
                    personalCard.isEnabled = false
                    identificationCard.isEnabled = false
                    businessCard.isEnabled = false
                    mappingCard.isEnabled = false
                    (activity as EnrollmentActivity).supportActionBar?.title = "Enrollment"
                }

                /*Selected Retailer Header Detail by CustomerID*/

                LoadingDialogue.dismissDialog()

            })


        // dashboard observer 1
        viewModel.dashboardLiveData.observe(
            viewLifecycleOwner,
            Observer {

                if (it != null && !it.lstCustomerFeedBackJson.isNullOrEmpty()) {

                    /*  PreferenceHelper.setStringValue(
                          requireContext(),
                          "SkuMinPrice",
                          it.lstCustomerFeedBackJson[0].SkuMinPrice!!
                      )
                      PreferenceHelper.setStringValue(
                          requireContext(),
                          "SkuMaxPrice",
                          it.lstCustomerFeedBackJson[0].SkuMaxPrice!!
                      )


                      // this line adding  inside dashboard api because of dely it will set after create option menu
                      (activity as DashboardActivity).setBadgeCount(it.lstCustomerFeedBackJson[0].CustomerCartCount)

                      retiler_name.text = it.lstCustomerFeedBackJson[0].FirstName
                      retailer_code.text = it.lstCustomerFeedBackJson[0].LoyaltyId
                      tier_type.text = it.lstCustomerFeedBackJson[0].CustomerGrade


                      if (it.lstCustomerFeedBackJson[0].CustomerGrade.equals("Bronze")) {
                          Glide.with(requireContext())
                              .load(requireContext().getDrawable(R.drawable.tier_bronze))
                              .into(dashbord_tier_cup)
                      } else if (it.lstCustomerFeedBackJson[0].CustomerGrade.equals("Gold")) {
                          Glide.with(requireContext())
                              .load(requireContext().getDrawable(R.drawable.tier_gold))
                              .into(dashbord_tier_cup)
                      } else if (it.lstCustomerFeedBackJson[0].CustomerGrade.equals("Platinum")) {
                          Glide.with(requireContext())
                              .load(requireContext().getDrawable(R.drawable.tier_platinum))
                              .into(dashbord_tier_cup)
                      } else {
                          Glide.with(requireContext())
                              .load(requireContext().getDrawable(R.drawable.tier_silver))
                              .into(dashbord_tier_cup)
                      }
  */

                    // set in preference helper
                    PreferenceHelper.setDashboardDetails(requireContext(), it)

                }
            })


        // dashboard observer 2
        viewModel.dashboardLiveData2.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

//                LoadingDialogue.dismissDialog()

                if (it != null && !it.ObjCustomerDashboardList.isNullOrEmpty()) {

//                    points.text = it.ObjCustomerDashboardList[0].OverAllPoints.toString()

                    // set in preference helper
                    PreferenceHelper.setCustomerDashboard(requireContext(), it)

                }

            })




    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var customerID = ""
        if((activity as EnrollmentActivity).customerList!=null){
            customerID = (activity as EnrollmentActivity).customerList!!.UserId.toString()
        }

        var bundle = arguments
        if(bundle!=null) {
            customerID = bundle.getString("CustomerID", "")
        }
        if(customerID.toString().isNotEmpty()){
            LoadingDialogue.showDialog(requireContext())
            //  Dashboard1 API request
            viewModel.getDashBoardData(
                DashboardRequest(
                    CustomerDetails(
                        CustomerId = customerID
                    )
                )
            )

            // call dashboard details 2
            viewModel.getDashBoardData2(
                DashboardCustomerRequest(
                    ActionType = "32",
                    IsActive = "true",
                    ActorId = customerID
                )
            )

            viewModel.getSelectedRetailerDetailbyCustomerId(
                GetSelectedRetailerDetailByCustomerIDRequest(
                    ActionType = 6,
                    CustomerId = customerID
                )
            )

            dashboardViewModel.getCustomerDetails(
                CustomerDetailsRequest(
                    ActionType = "1",
                    ActiveStatus = "-1",
                    ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                    CustomerGradId = "-1",
                    FromDate = "",
                    ToDate = "",
                    IsVerified = "-1",
                    MaxPointRange = "0",
                    MerchantID = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].MerchantId.toString(),
                    MinPointRange = "0",
                    PageSize = "10",
                    Type = customerID,
                    StartIndex = "1",

                    )
            )

        }


        generalCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                bundle.putString("MOBILENUMBER", "")
                bundle.putSerializable("GENERAL_DETAIL", lstCustomerJsons)
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_generalFragment, bundle)
                } catch (e: Exception) {
                }
            } else{
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_mobileNumberVerificationFragment)
                } catch (e: Exception) {
                }
            }
        }

        personalCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                bundle.putSerializable("PERSONAL_DETAIL", lstCustomerJsons)
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_personalFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }

        identificationCard.setOnClickListener {
            val bundle = Bundle()
            if(lstCustomerIdentityInfo!=null) {
                bundle.putSerializable("IDENTIFICATION_DETAIL", lstCustomerIdentityInfo)
                bundle.putSerializable("GENERAL_DETAIL", lstCustomerJsons)
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_identificationFragment, bundle)
                } catch (e: Exception) {
                }
            }else{
                bundle.putSerializable("GENERAL_DETAIL", lstCustomerJsons)
                findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_identificationFragment,bundle)
            }
        }

        businessCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
            val bundle = Bundle()
            bundle.putSerializable("GENERAL_DETAIL", lstCustomerJsons)
            bundle.putSerializable("BUSINESS_DETAIL", lstCustomerOfficalInfoJson)
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_businessFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }


        memberStatus.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("MEMBER_STATUS", lstCustomerJsons)
            try {
                findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_memberStatusFragment, bundle)
            } catch (e: Exception) {
            }
        }

        mappingCard.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_retailerMappingFragment)
            } catch (e: Exception) {
            }
        }

        rewardsCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                bundle.putSerializable("GENERAL_DETAIL", lstCustomerJsons)
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_retailerEarningFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }

        redemptionCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                var bundle = Bundle()
                bundle.putSerializable(
                    "GENERAL_DETAIL",
                    lstCustomerJsons
                )
                findNavController().navigate(
                    R.id.action_selectedRetailerDetailFragment_to_retailerRedemptionFragment,
                    bundle
                )
            }
        }

        promotionCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                bundle.putSerializable(
                    "SelectedReatilerDetail",
                    lstCustomerJsons
                )
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_retailerPromotionFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }

        ticketCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                bundle.putSerializable(
                    "SelectedReatilerDetail",
                    (activity as EnrollmentActivity).customerList!!
                )
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_retailerSupportFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }

        redemptionCatalogueCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                /*   bundle.putSerializable(
                       "SelectedReatilerDetail",
                       (activity as EnrollmentActivity).customerList!!
                   ) */
                bundle.putSerializable(
                    "SelectedCustomerDetail",lstCustomerJsons
                )
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_selectRedemptionFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }

        plannerCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                bundle.putSerializable("SelectedCustomerDetail",lstCustomerJsons)
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_MyWishListFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }

        dream_giftCard.setOnClickListener {
            if(lstCustomerJsons!=null) {
                val bundle = Bundle()
                bundle.putSerializable(
                    "SelectedReatilerDetail",
                    (activity as EnrollmentActivity).customerList!!
                )
                try {
                    findNavController().navigate(R.id.action_selectedRetailerDetailFragment_to_reatilerDreamGiftFragment, bundle)
                } catch (e: Exception) {
                }
            }
        }

    }
}