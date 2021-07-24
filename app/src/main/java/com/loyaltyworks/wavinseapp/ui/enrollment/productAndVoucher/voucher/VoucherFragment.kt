package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.voucher

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.ProductActivity
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.EGiftVoucherAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.SuccessDialogCallback
import kotlinx.android.synthetic.main.voucher_fragment.*
import kotlinx.android.synthetic.main.voucher_fragment.points
import java.lang.Exception
import java.util.ArrayList

class VoucherFragment : Fragment(), EGiftVoucherAdapter.voucherListAdpaterCallback {


    private val viewModel: ProductViewModel by viewModels {
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.voucher_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


/*
    override fun onPrepareOptionsMenu(menu: Menu) {
        val filterMenu = menu.findItem(R.id.filter)
        if (filterMenu != null) filterMenu.isVisible = false
    }
*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.redeemGiftLiveData.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if (it != null && !it.ObjCatalogueList.isNullOrEmpty()) {

                it.ObjCatalogueList!!.forEach { vouchers ->
                    vouchers.ObjCatalogueFixedPoints =
                        it.ObjCatalogueFixedPoints!!.filter { it.ProductCode == vouchers.ProductCode }
                }

                voucher_rv.layoutManager = GridLayoutManager(context, 2)
                voucher_rv.adapter = EGiftVoucherAdapter(it, this)
                voucher_rv.visibility = View.VISIBLE
                error_txt.visibility = View.GONE
            } else {
                voucher_rv.visibility = View.GONE
                error_txt.visibility = View.VISIBLE
            }
        })


        viewModel.redeemGiftVoucherResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if (it?.ReturnMessage != null) {
                val statusCode = it.ReturnMessage.toString()
                try {
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
                } catch (exception: Exception) {
                    SuccessDialogCallback.setApproveDialogue(
                        requireContext(),
                        false,
                        "Failed",
                        "Something went wrong try later",
                        object : SuccessDialogCallback.CustomApproveCallback {
                            override fun onClickOK(mDialogs: Dialog?) {
                                findNavController().popBackStack()
                            }

                        })
                }

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set points
        points.text =
            PreferenceHelper.getCustomerDashboard(requireContext())?.ObjCustomerDashboardList?.get(
                0
            )?.RedeemableEncashBalance.toString()


        LoadingDialogue.showDialog(requireContext())

        viewModel.setRedeemGiftRequest(
            RedeemGiftRequest(
                ActionType = "6",
                ActorId = PreferenceHelper.getLoginDetails(requireContext())?.UserList!![0].UserId.toString(),
                ObjCatalogueDetail(
                    CatalogueType = "4",
                    MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                        0
                    )?.MerchantId.toString()
                )
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRedeemVoucherFromAdapter(CatalogueVouchers: ObjCatalogueList, amount: String) {
        if (amount.isNotEmpty()) {

            if (amount.toLong() <= PreferenceHelper.getCustomerDashboard(requireContext())!!.ObjCustomerDashboardList!![0].OverAllPoints.toString()
                    .toLong()
            ) {

                if (CatalogueVouchers.Product_type == 0) {
                    RedeemVoucher(CatalogueVouchers, amount)
                    return
                }

                if (CatalogueVouchers.Product_type == 1 && amount.toLong() >= CatalogueVouchers.min_points.toString()
                        .toLong() && amount.toLong() <= CatalogueVouchers.max_points.toString()
                        .toLong()
                ) {

                    RedeemVoucher(CatalogueVouchers, amount)

                } else {
                    (activity as ProductActivity).snackBar(
                        "Enter redeemable amount in range",
                      R.color.red
                    )
                }
            } else {
                (activity as ProductActivity).snackBar(
                    "You do not have sufficient point balance to redeem voucher",
                    R.color.red
                )
            }
        } else {
            (activity as ProductActivity).snackBar("Enter redeemable amount",  R.color.red)
        }

    }

    private fun RedeemVoucher(CatalogueVouchers: ObjCatalogueList, amount: String) {
        SuccessDialogCallback.setApproveDialogue(
            requireContext(),
            true,
            "Are you sure...?",
            "Do you want to redeem this voucher?",
            object : SuccessDialogCallback.CustomApproveCallback {
                @SuppressLint("SimpleDateFormat")
                override fun onClickOK(mDialogs: Dialog?) {

//                                LoaderDialogue.showDialog(requireContext())
                    val objCatalogueList: ArrayList<ObjCatalogueListss> =
                        ArrayList<ObjCatalogueListss>()
                    val objCatalogueListss = ObjCatalogueListss();

                    objCatalogueListss.CatalogueId = CatalogueVouchers.CatalogueId
                    objCatalogueListss.CountryCurrencyCode = ""
                    objCatalogueListss.DeliveryType = CatalogueVouchers.DeliveryType
                    objCatalogueListss.HasPartialPayment = CatalogueVouchers.HasPartialPayment
                    objCatalogueListss.NoOfPointsDebit = amount.toString().toInt()
                    objCatalogueListss.PointsRequired = CatalogueVouchers.PointsRequired!!.toFloat()
                    objCatalogueListss.ProductCode = CatalogueVouchers.ProductCode
                    objCatalogueListss.ProductImage = CatalogueVouchers.ProductImage
                    objCatalogueListss.ProductName = CatalogueVouchers.ProductName
                    objCatalogueListss.RedemptionId = CatalogueVouchers.RedemptionId
                    objCatalogueListss.RedemptionTypeId = 4
                    objCatalogueListss.NoOfQuantity = 1
                    objCatalogueListss.Status = 0
                    objCatalogueListss.VendorId = CatalogueVouchers.VendorId
                    objCatalogueListss.VendorName = CatalogueVouchers.VendorName

                    objCatalogueList.add(objCatalogueListss)

                    LoadingDialogue.showDialog(requireContext())

                    viewModel.setRedeemGiftVoucherRequest(
                        RedeemGiftVoucherRequest(
                            ActionType = "51",
                            ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                            CountryID = CatalogueVouchers.CountryID.toString(),
                            MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                                0
                            )?.MerchantId.toString(),
                            ReceiverEmail = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                                0
                            )?.CustomerEmail,
                            ReceiverName = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                                0
                            )?.FirstName,
                            ReceiverMobile = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                                0
                            )?.CustomerMobile,
                            SourceMode = BuildConfig.SourceDevice,
                            ObjCatalogueList = objCatalogueList
                        )
                    )
                }

            })
    }


    override fun onAddToWishListVoucherFromAdapter(
        CatalogueVouchers: ArrayList<ObjCatalogueList>,
        pos: Int,
        mailid: String?,
        redeemablePoint: String?,
        name: String?
    ) {

    }

    override fun onDetailVoucherFromAdapter(CatalogueVouchers: ObjCatalogueList) {
        val bundle = Bundle()
        bundle.putSerializable("VoucherData", CatalogueVouchers)
        findNavController().navigate(R.id.action_voucherFragment_to_voucherDialogFragment, bundle)
    }

}