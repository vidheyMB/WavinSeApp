package com.loyaltyworks.wavinseapp.ui.enrollment.wishlist

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.DeactivateDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.DialogueCallBack
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.SuccessDialogCallback
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import kotlinx.android.synthetic.main.fragment_my_wish_llist_details.*
import java.util.*

class MyWishListDetailsFragment : Fragment() {


    var dreamGift: ObjCatalogue? = null
    var selectedActorId: String = ""
    private lateinit var viewModel: WishListViewModel

    private val productViewModel: ProductViewModel by viewModels {
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(WishListViewModel::class.java)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_my_wish_llist_details, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            dreamGift = arguments?.getSerializable("WishListGift") as ObjCatalogue?
            selectedActorId = arguments?.getString("SelectedActorId")!!
        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (dreamGift!!.PointReqToAcheiveProduct!!.toInt() <= 0 && dreamGift!!.RedeemablePointBalance!!.toInt() >= dreamGift!!.PointsRequired!!.toInt()) {
            point_requred_description.text =
                    "Congratulations! You are eligible to redeem your Dream Gift."
            dream_gift_redeem.setCardBackgroundColor(resources.getColor(R.color.red))
            dream_gift_redeem.visibility = View.VISIBLE
        } else {
            dream_gift_redeem.visibility = View.GONE
            dream_gift_redeem.setCardBackgroundColor(resources.getColor(R.color.grey))
            dream_gift_redeem.isClickable = false
            val pointsReq = dreamGift!!.CashValue!! - dreamGift!!.RedeemablePointBalance!!
            point_requred_description.text =
                    "You need " + pointsReq + " more points to redeem"

        }


        Glide.with(requireActivity()).asBitmap().error(R.drawable.ic_default_img)
                .placeholder(R.drawable.ic_default_img).load(
                BuildConfig.PROMO_IMAGE_BASE + "UploadFiles/CatalogueImages/" + dreamGift!!.ProductImage
                ).into(detils_image)

        product_name.text = dreamGift!!.ProductName
        product_value.text = "Points : " + dreamGift!!.CashValue.toString()
        avergaePointOne.text = "Redeemable points as on today"+" "+dreamGift!!.RedeemablePointBalance.toString()+" Points"
        avergaePointTwo.text = "Average earnings per month required per month "+" "+dreamGift!!.RedeemableAverageEarning.toString()+" Points"
        avergaePointThree.text = "Average earning required per month "+dreamGift!!.RedeemableAverageEarning12.toString()+" Points"

//        PossibelDateOne.text = dreamGift!!.AvgGreaterExpDate.toString()
//        PossibelDateTwo.text = dreamGift!!.AvgExpDate.toString()
//        PossibelDateThree.text = dreamGift!!.AvgLesserExpDate.toString()

        remove.setOnClickListener {
            LoadingDialogue.showDialog(requireContext())
            viewModel.getRemoveWishListLiveData(
                RemoveWishListRequest(ActionType = 17, ActorId = selectedActorId,
                    RedemptionPlannerId = dreamGift!!.RedemptionPlannerId.toString())
            )
        }


        dream_gift_redeem.setOnClickListener {
//            redeemPlanner()

            val dreamGiftList = mutableListOf<ObjCatalogueList>()
            val objCatalogueLists = ObjCatalogueList(
                DreamGiftId = dreamGift!!.DreamGiftId,
                LoyaltyId = dreamGift!!.LoyaltyId,
                NoOfPointsDebit = dreamGift!!.PointsRequired!!.toInt(),
                NoOfQuantity = 1, // Default Quantity = 1
                PointBalance = dreamGift!!.PointBalance,
                PointsRequired = dreamGift!!.PointsRequired,
                ProductName = dreamGift!!.ProductName,
                RedemptionTypeId = 1,
                CatalogueId=  dreamGift!!.CatalogueId
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
            view?.findNavController()
                ?.navigate(R.id.shippingAddressFragment, bundle)

        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.getRemoveWishListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it!=null && it.ReturnValue!!.toInt()>0){
                viewModel.getWishListLiveData(WishListRequest(ActionType = 19, ActorId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString() ))
                (activity as EnrollmentActivity).snackBar("Wishlist Removed Successfully",R.color.primary_color)
                findNavController().popBackStack()
            }else{
                (activity as EnrollmentActivity).snackBar("Failed to Remove Wishlist",R.color.red)
            }
            LoadingDialogue.dismissDialog()
        })

        productViewModel.redeemGiftVoucherResponse.observe(viewLifecycleOwner, Observer {

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


/*
        productViewModel.redeemGiftLiveData.observe(viewLifecycleOwner, Observer {

//            LoadingDialogue.dismissDialog()

            if (it != null && !it.ObjCatalogueList.isNullOrEmpty()) {

                val objCatalogueList: ArrayList<ObjCatalogueListss> =
                        ArrayList<ObjCatalogueListss>()

                val objCatalogue = ObjCatalogueListss()

                objCatalogue.CatalogueId = it.ObjCatalogueList!![0].CatalogueId
                objCatalogue.DeliveryType = it.ObjCatalogueList!![0].DeliveryType
                objCatalogue.PointsRequired = it.ObjCatalogueList!![0].PointsRequired!!.toFloat()
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
                objCatalogue.NoOfPointsDebit = dreamGift!!.PointsRequired
                objCatalogue.HasPartialPayment = it.ObjCatalogueList!![0].HasPartialPayment
                objCatalogue.CountryCurrencyCode = it.ObjCatalogueList!![0].CountryCurrencyCode

                objCatalogueList.add(objCatalogue)

                val name = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].Name
                var email = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].Email

                val bundle = Bundle()
                bundle.putSerializable("DreamGiftData", objCatalogueList)
                view?.findNavController()
                    ?.navigate(R.id.action_myWishListDetailsFragment_to_shippingAddressFragment, bundle)

            } else {

            }
        })*/

    }


    private fun redeemVoucher(
            email: String?,
            name: String?,
            objeCatalogueList: ArrayList<ObjCatalogueListss>?
    ) {

        /*  val bundle = Bundle()
          bundle.putSerializable("DreamGiftData", objeCatalogueList)
          view?.findNavController()
              ?.navigate(R.id.action_MyWishListFragment_to_shippingAddressFragment, bundle)*/

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


    private fun redeemPlanner() {

        productViewModel.setRedeemGiftRequest(
                RedeemGiftRequest(
                        ActionType = "6",
                        ActorId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString(),
                        ObjCatalogueDetail(
                                CatalogueId = dreamGift!!.CatalogueId.toString(),
                                MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                                        0
                                )?.MerchantId.toString()
                        )
                )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


}