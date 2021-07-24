package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.shippingAddress

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.DeactivateDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.DialogueCallBack
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.SuccessDialogCallback
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift.ReatilerDreamGiftViewModel
import kotlinx.android.synthetic.main.o_t_p_fragment.*

class ShippingOTPFragment : Fragment() {

    private lateinit var OTPNumber: String
    var TotalPoints = 0
    var pointOfDefit = 0
    var ProductList: String = ""

    // if this is dreamGift Redemption than it will be True
    var dreamGiftStatus = false

    lateinit var ObjCatalogueList: List<ObjCatalogueList>
    lateinit var shippingAddressDetails: ObjCustShippingAddressDetail

    // DreamGiftData to redeem dreamGift
    lateinit var dreamGiftData: RedeemGiftCatalogueRequest

    private lateinit var viewModel: ShippingAddressViewModel
    private lateinit var myDreamGiftviewModel: ReatilerDreamGiftViewModel

    private val productViewModel : ProductViewModel by viewModels{
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }

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
        savedInstanceState: Bundle?
    ): View? {
        myDreamGiftviewModel = ViewModelProvider(this).get(ReatilerDreamGiftViewModel::class.java)
        viewModel = ViewModelProvider(this).get(ShippingAddressViewModel::class.java)

        return inflater.inflate(R.layout.o_t_p_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        myDreamGiftviewModel.getRemoveDreamGiftLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it.ReturnValue!!.toInt() > 0) {

            } else {

            }

        })


        productViewModel.netPoints.observe(viewLifecycleOwner, Observer {
            if(it!=null) TotalPoints = it
        })

        productViewModel.allProducts.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                ObjCatalogueList = it

                it.forEach {
                    ProductList += "${it.ProductName},"
                }

            }
        })

        viewModel.otp.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if(viewLifecycleOwner.lifecycle.currentState== Lifecycle.State.RESUMED) {
                if (!it.ReturnMessage.isNullOrEmpty()) {
                    OTPNumber = it.ReturnMessage
                } else {
                    Toast.makeText(context, BuildConfig.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                }
            }
        })

        viewModel.redeemGiftCatalogueResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                val message: String = it.ReturnMessage!!

                if (message != "-1" && message.split("-").toTypedArray()[1] == "00") {
                    // If its 00 , then member is deactivated.
                    DeactivateDialogue.showPopUpDialog(
                        requireContext(),
                        false,
                        "",
                        "OK",
                        "Your account has been deactivated!\\n Kindly contact the administrator or call to +91 9772294246",
                        object : DialogueCallBack {
                            override fun onResponse(response: String) {

                            }

                            override fun onAgain() {
                                findNavController().popBackStack()
                            }
                        })

                } else if (message.split("-").toTypedArray()[1].toInt() > 0) {

                    val msgss:String = if(!this::dreamGiftData.isInitialized) {
                        "Dear ${shippingAddressDetails!!.FullName.toString()} you have successfully redeemed" + " $ProductList from our catalogue. Total ${TotalPoints.toString()} points debited from your account."
                    }else{
                        "Dear ${shippingAddressDetails!!.FullName.toString()} you have successfully redeemed" + " ${dreamGiftData.ObjCatalogueList?.get(0)?.ProductName} from our DreamGift. Total ${dreamGiftData.ObjCatalogueList?.get(0)?.PointsRequired} points debited from your account."
                    }

                    if(dreamGiftStatus)  {
                        DreamGiftRemovedCall()
                    }

                    SuccessDialogCallback.setApproveDialogue(
                        requireContext(),
                        false,
                        "Thankyou...",
                        msgss,
                        object : SuccessDialogCallback.CustomApproveCallback {
                            override fun onClickOK(mDialogs: Dialog?) {

                                if(dreamGiftStatus) {
                                    // navigate back to dashboard
                                    view?.findNavController()?.navigate(R.id.action_OTPFragment_to_selectedRetailerDetailFragment)
                                }else {
                                    // remove all products from room db
                                    productViewModel.deleteAllProduct()
                                    // finish product activity
                                    requireActivity().finish()
                                }
                            }

                        })


                    // Send Alert Service

                    LoadingDialogue.showDialog(requireContext())

                    ObjCatalogueList.forEach {
                        it.RedemptionRefno = message.split("-").toTypedArray()[1]
                    }

                    if(dreamGiftStatus) {
                        pointOfDefit = dreamGiftData.ObjCatalogueList?.get(0)?.PointsRequired!!.toInt()
                    }else {
                        pointOfDefit = TotalPoints
                    }

                    viewModel.setSendCatalogueRedemptionAlertMobileAppRequest(
                        SendCatalogueRedemptionAlertMobileAppRequest(
                            MemberName = shippingAddressDetails.FullName,
                            MerchantEmailID = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(0)?.MerchantEmail,
                            MerchantID = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(0)?.MerchantId.toString(),
                            MerchantMobileNo = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(0)?.MerchantMobile,
                            TotalPointsRedeemed = pointOfDefit.toString(),
                            UserName = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].LoyaltyId,
                            ObjCustShippingAddressDetails = shippingAddressDetails,
                            ObjCatalogueList = ObjCatalogueList
                        )
                    )

                    // Send SMS Service

                    viewModel.setSendSMSForSuccessfulRedemptionRequest(
                        SendSMSForSuccessfulRedemptionMobileAppRequest(
                            CustomerName = shippingAddressDetails.FullName,
                            EmailID = shippingAddressDetails.Email,
                            LoyaltyID = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].LoyaltyId,
                            Mobile = shippingAddressDetails.Mobile,
                            PointBalance = PreferenceHelper.getCustomerDashboard(requireContext())?.ObjCustomerDashboardList?.get(0)?.RedeemablePointsBalance.toString(),
                            RedeemedPoint = pointOfDefit.toString()
                        )
                    )
                }else {
//                LoaderDialogue.dismissDialog()
                    SuccessDialogCallback.setApproveDialogue(
                        requireContext(),
                        false,
                        "Failed",
                        BuildConfig.SOMETHING_WENT_WRONG,
                        object : SuccessDialogCallback.CustomApproveCallback {
                            override fun onClickOK(mDialogs: Dialog?) {
                                findNavController().popBackStack()
                            }

                        })
                }

            } else {
//                LoaderDialogue.dismissDialog()
                SuccessDialogCallback.setApproveDialogue(
                    requireContext(),
                    false,
                    "Failed",
                    BuildConfig.SOMETHING_WENT_WRONG,
                    object : SuccessDialogCallback.CustomApproveCallback {
                        override fun onClickOK(mDialogs: Dialog?) {
                            findNavController().popBackStack()
                        }

                    })
            }
        })


        viewModel.alertMobileAppResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if (it != null && it.ReturnValue!! > 0) {

            }
            else {
//                LoaderDialogue.dismissDialog()
                SuccessDialogCallback.setApproveDialogue(
                    requireContext(),
                    false,
                    "Failed",
                    BuildConfig.SOMETHING_WENT_WRONG,
                    object : SuccessDialogCallback.CustomApproveCallback {
                        override fun onClickOK(mDialogs: Dialog?) {
                            findNavController().popBackStack()
                        }

                    })
            }
        })


        viewModel.sendSMSForSuccessfulRedemptionMobileAppResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

                if (it != null && it.SendSMSForSuccessfulRedemptionMobileAppResult!!) {

                }
                else {

//                    LoaderDialogue.dismissDialog()
                    SuccessDialogCallback.setApproveDialogue(
                        requireContext(),
                        false,
                        "Failed",
                        BuildConfig.SOMETHING_WENT_WRONG,
                        object : SuccessDialogCallback.CustomApproveCallback {
                            override fun onClickOK(mDialogs: Dialog?) {
                                findNavController().popBackStack()
                            }

                        })
                }
            })
    }

    private fun DreamGiftRemovedCall() {

        val removeDreamGiftRequest = RemoveDreamGiftRequest()

        removeDreamGiftRequest.ActionType = 4
        removeDreamGiftRequest.ActorId =
            PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId!!.toInt()
        removeDreamGiftRequest.DreamGiftId = dreamGiftData.ObjCatalogueList?.get(0)?.DreamGiftId
        removeDreamGiftRequest.GiftStatusId = 5 //

        myDreamGiftviewModel.getRemoveDreamGiftNew(removeDreamGiftRequest)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(arguments!=null) {
            shippingAddressDetails = requireArguments().getSerializable("Address") as ObjCustShippingAddressDetail
            // If this data not null, than redemption will happen for dreamGift
            if(requireArguments().getSerializable("DreamGiftData")!=null)
            dreamGiftData = requireArguments().getSerializable("DreamGiftData") as RedeemGiftCatalogueRequest

            if(this::dreamGiftData.isInitialized)
                dreamGiftStatus = true
        }

        // send OTP
        LoadingDialogue.showDialog(requireContext())
        sendOTP()

        var booleanValue = true

        // Verify OTP
        submits.setOnClickListener {
            if (BlockMultipleClick.click()){
                return@setOnClickListener
            }
            booleanValue = true
            if (otp_views.otp.toString() == "") {
                booleanValue = false;
                Toast.makeText(context, "Please enter otp", Toast.LENGTH_SHORT).show()
            }else if (otp_views.otp.toString() != "" && otp_views.otp.toString().length < 6 && booleanValue){
                booleanValue = false
                Toast.makeText(context, "Error! Please enter a valid One-Time Password (OTP)", Toast.LENGTH_SHORT).show();
            }

            if (otp_views.otp == OTPNumber) {

                otp_views.showSuccess()
//                ProgressDialogue.showDialog(requireContext())

                val redeemGiftCatalogueRequest: RedeemGiftCatalogueRequest = if(this::dreamGiftData.isInitialized){
                    dreamGiftData  // Submit DreamGift data
                }else{
                     // Submit Redemption Catalogue data
                    RedeemGiftCatalogueRequest(
                        ActionType = "51",
                        ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                        MemberName = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].FirstName,
                        SourceMode = BuildConfig.SourceDevice,
                        ObjCatalogueList = ObjCatalogueList,
                        ObjCustShippingAddressDetails = shippingAddressDetails
                    )
                }

                // Final redemption request
                viewModel.setRedeemGiftCatalogueRequest(redeemGiftCatalogueRequest)


            } else {

                if (booleanValue) {
                    booleanValue = false
                    Toast.makeText(context, "Error! Please enter a valid One-Time Password (OTP)", Toast.LENGTH_SHORT).show()
                }
                otp_views.showError()
            }
        }


        // Resend OTP
        resend_otp.setOnClickListener {
            if (BlockMultipleClick.click()){
                return@setOnClickListener
            }
            LoadingDialogue.showDialog(requireContext())
            otp_views.setOTP("")
            sendOTP()
        }
    }

    private fun sendOTP() {
        LoadingDialogue.showDialog(requireContext())

        viewModel.getOTP(
            OTPRequest(
                EmailID = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].Email,
                Name = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].FirstName,
                MerchantUserName = BuildConfig.MerchantUserName,
                MobileNo = shippingAddressDetails.Mobile,
                UserId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                UserName = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].FirstName
            )
        )
    }

}