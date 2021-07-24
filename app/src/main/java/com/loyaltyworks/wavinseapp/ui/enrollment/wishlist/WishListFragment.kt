package com.loyaltyworks.wavinseapp.ui.enrollment.wishlist

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustomerJsons
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.DeactivateDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.DialogueCallBack
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.SuccessDialogCallback
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import com.loyaltyworks.wavinseapp.ui.enrollment.wishlist.Adapter.MyWishListAdapter
import kotlinx.android.synthetic.main.retailer_wishlist_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class WishListFragment : Fragment(), MyWishListAdapter.OnClickCallBack {


    private lateinit var viewModel: WishListViewModel

    private var lstCustomerJsons: ArrayList<LstCustomerJsons>?=null

    private val productViewModel: ProductViewModel by viewModels {
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }

    lateinit var planner: ObjCatalogue
    var positions = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this).get(WishListViewModel::class.java)
        return inflater.inflate(R.layout.wish_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            lstCustomerJsons = arguments?.getSerializable("SelectedCustomerDetail") as ArrayList<LstCustomerJsons>
        }

        LoadingDialogue.showDialog(requireContext())
        viewModel.getWishListLiveData(
            WishListRequest(
                ActionType = 19,
                ActorId = lstCustomerJsons!![0].UserId.toString()
            )
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        // getting the bundle back from the android
//        val bundle = arguments
//        lstCustomerJsons = bundle!!.getSerializable("SelectedCustomerDetail") as ArrayList<LstCustomerJson>

        productViewModel.redeemGiftVoucherResponse.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {

                LoadingDialogue.dismissDialog()

                if (it?.ReturnMessage != null) {
                    val statusCode = it.ReturnMessage.toString()
                    if (statusCode == null || statusCode == "") {

                        SuccessDialogCallback.setApproveDialogue(
                            requireContext(),
                            false,
                            "Oops",
                            "Redemption Failed",
                            object : SuccessDialogCallback.CustomApproveCallback {
                                override fun onClickOK(mDialogs: Dialog?) {
                                    findNavController().popBackStack()
                                }

                            })

                    }
                    if (statusCode != "-1" && statusCode.split("-").toTypedArray()[1] == "00") {

                        SuccessDialogCallback.setApproveDialogue(
                            requireContext(),
                            false,
                            "Oops",
                            "Member is de-activated!",
                            object : SuccessDialogCallback.CustomApproveCallback {
                                override fun onClickOK(mDialogs: Dialog?) {
                                    findNavController().popBackStack()
                                }

                            })


                    }

                    if (statusCode != "-1" && statusCode.split("-")
                            .toTypedArray()[0] == "-1~Cannot insert"
                    ) {

                        SuccessDialogCallback.setApproveDialogue(
                            requireContext(),
                            false,
                            "Oops",
                            BuildConfig.SOMETHING_WENT_WRONG,
                            object : SuccessDialogCallback.CustomApproveCallback {
                                override fun onClickOK(mDialogs: Dialog?) {
                                    findNavController().popBackStack()
                                }

                            })

                    }

                    if (statusCode != "-1" && statusCode.split("-").toTypedArray()[1] == "000") {

                        SuccessDialogCallback.setApproveDialogue(
                            requireContext(),
                            false,
                            "Oops",
                            "Unfortunately your redemption has not been completed. Your points redeemed will be reinstated shortly. Please try again later once",
                            object : SuccessDialogCallback.CustomApproveCallback {
                                override fun onClickOK(mDialogs: Dialog?) {
                                    findNavController().popBackStack()
                                }

                            })

                    }

                    if (statusCode.split("-").toTypedArray()[1].toInt() > 0) {

                        SuccessDialogCallback.setApproveDialogue(
                            requireContext(),
                            false,
                            "Thank you for redeeming.",
                            "The E-voucher will be sent to your registered email id shortly.",
                            object : SuccessDialogCallback.CustomApproveCallback {
                                override fun onClickOK(mDialogs: Dialog?) {
                                    findNavController().popBackStack()
                                }

                            })


                    } else {

                        SuccessDialogCallback.setApproveDialogue(
                            requireContext(),
                            false,
                            "Oops",
                            "Redemption Failed",
                            object : SuccessDialogCallback.CustomApproveCallback {
                                override fun onClickOK(mDialogs: Dialog?) {
                                    findNavController().popBackStack()
                                }

                            })
                    }
                }
            })

        productViewModel.redeemGiftLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null && !it.ObjCatalogueList.isNullOrEmpty()) {

                    val objCatalogueList: ArrayList<ObjCatalogueListss> =
                        ArrayList<ObjCatalogueListss>()

                    val objCatalogue = ObjCatalogueListss()

                    objCatalogue.CatalogueId = it.ObjCatalogueList!![0].CatalogueId
                    objCatalogue.DeliveryType = it.ObjCatalogueList!![0].DeliveryType
                    objCatalogue.PointsRequired =
                        it.ObjCatalogueList!![0].PointsRequired!!.toFloat()
                    objCatalogue.ProductCode = it.ObjCatalogueList!![0].ProductCode
                    objCatalogue.ProductName = it.ObjCatalogueList!![0].ProductName
                    objCatalogue.ProductImage = it.ObjCatalogueList!![0].ProductImage
                    objCatalogue.VendorId = it.ObjCatalogueList!![0].VendorId
                    objCatalogue.VendorName = it.ObjCatalogueList!![0].VendorName
                    objCatalogue.Status = it.ObjCatalogueList!![0].Status
                    objCatalogue.RedemptionTypeId = 4
                    objCatalogue.CountryCurrencyCode = "INR"
                    objCatalogue.RedemptionId = it.ObjCatalogueList!![0].RedemptionId
                    objCatalogue.JRedemptionDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
                    objCatalogue.NoOfQuantity = 1
                    objCatalogue.HasPartialPayment = false
//                objCatalogue.NoOfPointsDebit = it.ObjCatalogueList!![0].NoOfPointsDebit
                    objCatalogue.NoOfPointsDebit = planner.PointsRequired
                    objCatalogue.HasPartialPayment = it.ObjCatalogueList!![0].HasPartialPayment
                    objCatalogue.CountryCurrencyCode = it.ObjCatalogueList!![0].CountryCurrencyCode

                    objCatalogueList.add(objCatalogue)

                    val name =
                        PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].Name
                    var email =
                        PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].Email

//                redeemVoucher(email, name, objCatalogueList)

                    val bundle = Bundle()
                    bundle.putSerializable("DreamGiftData", objCatalogueList)
                    view?.findNavController()
                        ?.navigate(
                            R.id.action_myDreamGiftFragment_to_shippingAddressFragment,
                            bundle
                        )

                } else {

                }
            })



        viewModel.getWishListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null && !it.ObjCatalogueList.isNullOrEmpty()) {
                no_dream_gift_found.visibility = View.GONE
                rv_my_dream_gift.visibility = View.VISIBLE
                rv_my_dream_gift.adapter = MyWishListAdapter(it.ObjCatalogueList, this)
            } else {
                no_dream_gift_found.visibility = View.VISIBLE
                rv_my_dream_gift.visibility = View.GONE
            }
            LoadingDialogue.dismissDialog()
        })

        viewModel.getRemoveWishListLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null && it.ReturnValue!!.toInt() > 0) {

                    viewModel.getWishListLiveData(
                        WishListRequest(
                            ActionType = 19,
                            ActorId = lstCustomerJsons!![0].UserId.toString()
                        )
                    )
                    if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        (activity as EnrollmentActivity).snackBar(
                            "Product has been removed from your wishlist",
                            R.color.primary_color
                        )
                    }
                } else {

                    (activity as EnrollmentActivity).snackBar(
                        "Failed to remove product from wishlist",
                        R.color.red
                    )

                }
                LoadingDialogue.dismissDialog()
            })

    }


    private fun redeemVoucher(
        email: String?,
        name: String?,
        objeCatalogueList: ArrayList<ObjCatalogueListss>?
    ) {

        DeactivateDialogue.showPopUpDialog(
            requireContext(),
            true,
            "No",
            "Yes",
            "Are you sure you want to redeem?",
            object : DialogueCallBack {
                override fun onResponse(response: String) {

                }

                override fun onAgain() {

                    productViewModel.setRedeemGiftVoucherRequest(
                        RedeemGiftVoucherRequest(
                            ActionType = "51",
                            ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                            CountryCode = "IN",
                            CountryID = "15",
                            MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                                0
                            )?.MerchantId.toString(),
                            ReceiverEmail = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].Email.toString(),
                            ReceiverName = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].Name.toString(),
                            ReceiverMobile = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].Mobile.toString(),
                            SourceMode = BuildConfig.SourceDevice,
                            ObjCatalogueList = objeCatalogueList
                        )
                    )


                }

            })


    }


    override fun onClickRedeemResponse(
        position: Int,
        dreamGiftArrayList: List<ObjCatalogue>,
        dreamGiftID: Int?,
        redeemBtn: CardView?
    ) {
        planner = dreamGiftArrayList[position]

        positions = position


//        val objCatalogueList: ArrayList<ObjCatalogueListss> =
//            ArrayList<ObjCatalogueListss>()
//
//        val objCatalogue = ObjCatalogueListss()
//
//        objCatalogue.CatalogueId = planner.CatalogueId
//        objCatalogue.DeliveryType = planner.DeliveryType
//        objCatalogue.PointsRequired = planner.PointsRequired!!.toFloat()
//        objCatalogue.ProductCode = planner.ProductCode
//        objCatalogue.ProductName = planner.ProductName
//        objCatalogue.ProductImage = planner.ProductImage
//        objCatalogue.VendorId = planner.VendorId
//        objCatalogue.VendorName = planner.VendorName
//        objCatalogue.Status = planner.Status
//        objCatalogue.RedemptionTypeId = 4
//        objCatalogue.CountryCurrencyCode = "INR"
//        objCatalogue.RedemptionId = planner.RedemptionId
//        objCatalogue.JRedemptionDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
//        objCatalogue.NoOfQuantity = 1
//        objCatalogue.HasPartialPayment = false
////                objCatalogue.NoOfPointsDebit = planner.NoOfPointsDebit
//        objCatalogue.NoOfPointsDebit = planner.PointsRequired
//        objCatalogue.HasPartialPayment = planner.HasPartialPayment
//        objCatalogue.CountryCurrencyCode = planner.CountryCurrencyCode
//
//        objCatalogueList.add(objCatalogue)
//
//        val name = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].Name
//        var email = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].Email

//                redeemVoucher(email, name, objCatalogueList)

        val dreamGiftList = mutableListOf<ObjCatalogueList>()
        val objCatalogueLists = ObjCatalogueList(
            DreamGiftId = planner.DreamGiftId,
            LoyaltyId = planner.LoyaltyId,
            NoOfPointsDebit = planner.PointsRequired!!.toInt(),
            NoOfQuantity = 1, // Default Quantity = 1
            PointBalance = planner.PointBalance,
            PointsRequired = planner.PointsRequired,
            ProductName = planner.ProductName,
            RedemptionTypeId = 1,
            CatalogueId = planner.CatalogueId
        )
        dreamGiftList.add(objCatalogueLists)

        val dreamGiftData = RedeemGiftCatalogueRequest(
            ActionType = "51",
            ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
            MemberName = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].FirstName,
            SourceMode = BuildConfig.SourceDevice,
            ObjCatalogueList = dreamGiftList,
        )

        val bundle = Bundle()
        bundle.putSerializable("DreamGiftData", dreamGiftData)
        view?.findNavController()?.navigate(R.id.shippingAddressFragment, bundle)

//        redeemPlanner()

    }

    override fun onClickDetialsResponse(
        position: Int,
        dreamGiftArrayList: List<ObjCatalogue>,
        dreamGiftID: Int?
    ) {
        val bundle = Bundle()
        bundle.putSerializable("WishListGift", dreamGiftArrayList[position])
        bundle.putString("SelectedActorId", lstCustomerJsons!![0].UserId.toString())
        findNavController().navigate(
            R.id.action_wishListFragment_to_myWishListDetailsFragment,
            bundle
        )
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    override fun onClickRemoveResponse(position: Int, wishArrayList: List<ObjCatalogue>) {
        LoadingDialogue.showDialog(requireContext())
        viewModel.getRemoveWishListLiveData(
            RemoveWishListRequest(
                ActionType = 17,
                ActorId = lstCustomerJsons!![0].UserId.toString(),
                RedemptionPlannerId = wishArrayList[position].RedemptionPlannerId.toString()
            )
        )
    }

    private fun redeemPlanner() {

        productViewModel.setRedeemGiftRequest(
            RedeemGiftRequest(
                ActionType = "6",
                ActorId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString(),
                ObjCatalogueDetail(
                    CatalogueId = planner.CatalogueId.toString(),
                    MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                        0
                    )?.MerchantId.toString()
                )
            )
        )
    }

}