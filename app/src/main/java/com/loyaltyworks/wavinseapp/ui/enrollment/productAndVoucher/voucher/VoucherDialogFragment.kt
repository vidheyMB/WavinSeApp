package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.voucher

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.VoucherPointsAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.SuccessDialogCallback
import com.loyaltyworks.wavinseapp.model.ObjCatalogueFixedPoint
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import com.loyaltyworks.wavinseapp.model.ObjCatalogueListss
import com.loyaltyworks.wavinseapp.model.RedeemGiftVoucherRequest
import kotlinx.android.synthetic.main.fragment_voucher_dialog_list_dialog.*
import kotlinx.android.synthetic.main.fragment_voucher_dialog_list_dialog.mng_price_spinner
import kotlinx.android.synthetic.main.row_e_gift_voucher.*
import java.util.ArrayList

class VoucherDialogFragment : BottomSheetDialogFragment() {

    var defaultData = arrayOf("0")
    var objCatalogueFixedPoint: ObjCatalogueFixedPoint? = null


    private val viewModel: ProductViewModel by viewModels {
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }

    private lateinit var objCatalogueList: ObjCatalogueList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


  /*  override fun onPrepareOptionsMenu(menu: Menu) {
        val filterMenu = menu.findItem(R.id.filter)
        if (filterMenu != null) filterMenu.isVisible = false
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(this).get(viewModel::class.java)
        return inflater.inflate(R.layout.fragment_voucher_dialog_list_dialog, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val bundle = this.arguments
        if (bundle != null) {
            objCatalogueList = arguments?.getSerializable("VoucherData") as ObjCatalogueList

            try {

                Glide.with(requireContext()).asBitmap()
                    .error(R.drawable.ic_baseline_photo_size_select_actual_24)
                    .placeholder(R.drawable.ic_baseline_photo_size_select_actual_24).load(
                        objCatalogueList.ProductImage
                    )
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .optionalFitCenter().into(voucher_imgs)

                voucher_imgs.setPadding(0, 0, 0, 0)

            } catch (e: Exception) {
            }


            detail_voucher_name.text = objCatalogueList.ProductName
            category.text = objCatalogueList.CatogoryName
            amount_range.text = "Amount Range " + objCatalogueList.min_points.toString()
            detailDesc_tv.text = objCatalogueList.ProductDesc
            detailtc_tv.text = objCatalogueList.TermsCondition


            desc_img.setOnClickListener { v: View? ->

                if (detailDesc_tv.visibility == View.VISIBLE) {
                    detailDesc_tv.visibility = View.GONE
                } else {

                    detailDesc_tv.visibility = View.VISIBLE
                    detailtc_tv.visibility = View.GONE
                    detailredeem_tv.visibility = View.GONE
                }
            }

            tc_img.setOnClickListener { v: View? ->

                if (detailtc_tv.visibility == View.VISIBLE) {
                    detailtc_tv.visibility = View.GONE
                } else {
                    detailtc_tv.text = objCatalogueList.TermsCondition
                    detailtc_tv.visibility = View.VISIBLE
                    detailDesc_tv.visibility = View.GONE
                    detailredeem_tv.visibility = View.GONE
                }
            }


            redeem_img.setOnClickListener {
                if (detailredeem_tv.visibility == View.VISIBLE) {
                    detailredeem_tv.visibility = View.GONE
                } else {
                    detailredeem_tv.text = "Redeem at Store"
                    detailredeem_tv.visibility = View.VISIBLE
                    detailDesc_tv.visibility = View.GONE
                    detailtc_tv.visibility = View.GONE
                }
            }

        }

        cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        val amount = mng_amount_fld.text.toString()






        if (objCatalogueList.Product_type == 1) {

            amount_range.text =
                "INR ${objCatalogueList.min_points.toString()} - ${objCatalogueList.max_points.toString()}"
            mng_amount_fld.visibility = View.VISIBLE
            mng_price_spinner.visibility = View.GONE
            mng_price_spinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                defaultData
            )

            mng_redeem_btn.visibility = View.VISIBLE

        } else {

            amount_range.visibility = View.INVISIBLE
            mng_amount_fld.setText("")
            mng_amount_fld.visibility = View.GONE

            if (!objCatalogueList.ObjCatalogueFixedPoints.isNullOrEmpty()) {
//                if (redeemGiftResponse.ObjCatalogueFixedPoints != null)
                mng_price_spinner.adapter =
                    VoucherPointsAdapter(
                        requireContext(),/*redeemGiftResponses.ObjCatalogueFixedPoints!![0].ProductCode!!*/
                        objCatalogueList.ObjCatalogueFixedPoints!!
                    )/* else holder.mSpinnerHost.adapter = ArrayAdapter(
                    holder.itemView.context,
                    R.layout.spinner_row_small_size,
                    defaultData
                )*/
            } else mng_price_spinner.adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_row_small_size,
                defaultData
            )

            mng_price_spinner.visibility = View.VISIBLE


        }






        mng_price_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                objCatalogueFixedPoint =
                    parent!!.getItemAtPosition(position) as ObjCatalogueFixedPoint

                Log.d("fjdskf", objCatalogueFixedPoint!!.FixedPoints.toString())


            }

        }




        mng_redeem_btn.setOnClickListener {

            if (objCatalogueFixedPoint?.FixedPoints != null || !TextUtils.isEmpty(mng_amount_fld.text.toString())) {

                if (objCatalogueList.Product_type == 0) {
                    RedeemVoucher(objCatalogueFixedPoint!!.FixedPoints.toString())
                    return@setOnClickListener
                }

                if (mng_amount_fld.text.toString().toLong() <= PreferenceHelper.getCustomerDashboard(requireContext())!!.ObjCustomerDashboardList!![0].OverAllPoints.toString().toLong()) {

                    if (mng_amount_fld.text.toString().toLong() >= objCatalogueList.min_points.toString().toLong()) {
                        RedeemVoucher(mng_amount_fld.text.toString())

                    } else {
                        mng_amount_fld.error = "Enter redeemable amount in range"
                        mng_amount_fld.requestFocus()
//                        (activity as MainActivity).snackBar(
//                            "Enter redeemable amount in range",
//                            R.color.red
//                        )
                    }
                } else {
                    mng_amount_fld.error =
                        "You do not have sufficient point balance to redeem voucher"
                    mng_amount_fld.requestFocus()

//                    (activity as MainActivity).snackBar(
//                        "You do not have sufficient point balance to redeem voucher",
//                        R.color.red
//                    )
                }
            } else {
                mng_amount_fld.error = "Enter redeemable amount"
                mng_amount_fld.requestFocus()
//                (activity as MainActivity).snackBar("Enter redeemable amount", R.color.red)
            }

        }

    }

    private fun RedeemVoucher(amountss:String) {


        SuccessDialogCallback.setApproveDialogue(
            requireContext(),
            true,
            "Are you sure...?",
            "Do you want to redeem this voucher?",
            object : SuccessDialogCallback.CustomApproveCallback {
                @SuppressLint("SimpleDateFormat")
                override fun onClickOK(mDialogs: Dialog?) {

//                                    LoaderDialogue.showDialog(requireContext())
                    val objCatalogueLists: ArrayList<ObjCatalogueListss> =
                        ArrayList<ObjCatalogueListss>()
                    val objCatalogueListss = ObjCatalogueListss();

                    objCatalogueListss.CatalogueId = objCatalogueList.CatalogueId
                    objCatalogueListss.CountryCurrencyCode = ""
                    objCatalogueListss.Address1 = ""
                    objCatalogueListss.DeliveryType = objCatalogueList.DeliveryType
                    objCatalogueListss.HasPartialPayment = objCatalogueList.HasPartialPayment
                    objCatalogueListss.NoOfPointsDebit =amountss.toInt()
                    objCatalogueListss.PointsRequired = objCatalogueList.PointsRequired!!.toFloat()
                    objCatalogueListss.ProductCode = objCatalogueList.ProductCode
                    objCatalogueListss.ProductImage = objCatalogueList.ProductImage
                    objCatalogueListss.ProductName = objCatalogueList.ProductName
//                                    objCatalogueListss.RedemptionDate =
//                                            SimpleDateFormat("yyyy-MM-dd").format(
//                                                    Date()
//                                            )
                    objCatalogueListss.RedemptionId = objCatalogueList.RedemptionId
                    objCatalogueListss.RedemptionTypeId =
                        objCatalogueList.RedemptionTypeId
                    objCatalogueListss.Status = 0
                    objCatalogueListss.VendorId = objCatalogueList.VendorId
                    objCatalogueListss.VendorName = objCatalogueList.VendorName

                    objCatalogueLists.add(objCatalogueListss)

                    LoadingDialogue.showDialog(requireContext())

                    viewModel.setRedeemGiftVoucherRequest(
                        RedeemGiftVoucherRequest(
                            ActionType = "51",
                            ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                            CountryID = objCatalogueList.CountryID.toString(),
                            MerchantId = PreferenceHelper.getDashboardDetails(requireContext())?.lstCustomerFeedBackJson?.get(
                                0
                            )?.MerchantId.toString(),
                            ReceiverEmail = PreferenceHelper.getLoginDetails(
                                requireContext()
                            )!!.UserList!![0].Email.toString(),
                            ReceiverName = PreferenceHelper.getLoginDetails(
                                requireContext()
                            )!!.UserList!![0].Name.toString(),
                            ReceiverMobile = PreferenceHelper.getLoginDetails(
                                requireContext()
                            )!!.UserList!![0].Mobile.toString(),
                            SourceMode = BuildConfig.SourceDevice,
                            ObjCatalogueList = objCatalogueLists
                        )
                    )
                }

            })

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.redeemGiftVoucherResponse.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if (it?.ReturnMessage != null) {
                val statusCode = it.ReturnMessage.toString()

                try {
                    if (statusCode.split("-").toTypedArray()[1].toInt() > 0) {
//                    LoaderDialogue.dismissDialog()
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

                        /*                    mng_amount_fld.setText("")

                        Dialogue.getInstance().showCustomDialog(
                            false,
                            context,
                            "Thank you for redeeming. The E-voucher will be sent to your registered email id shortly.",
                            "Ok",
                            object : CustomAlertCallback() {
                                fun onPositiveAction() {
                                    dashboardViewModel.getDashboardDetail(
                                        context,
                                        java.lang.String.valueOf(
                                            AppController.getInstance().getLoginUserDetails(context)
                                                .getUserId()
                                        )
                                    )
                                    getDashboardDetailResponse()
                                    onBackPressed()
                                }
                            })*/

                    } else {

//                    LoaderDialogue.dismissDialog()
                        if (statusCode == null || statusCode == "") {
//                        LoaderDialogue.dismissDialog()
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

                            /* mng_amount_fld.setText("")
                            Dialogue.getInstance().showCustomDialog(
                                false,
                                context,
                                context!!.getString(R.string.redeem_failed),
                                "Ok",
                                object : CustomAlertCallback() {
                                    fun onPositiveAction() {
                                        onBackPressed()
                                    }
                                })
                            return*/

                        }

                        if (statusCode != "-1" && statusCode.split("-")
                                .toTypedArray()[0] == "-1~Cannot insert"
                        ) {
//                        LoaderDialogue.dismissDialog()
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

                        if (statusCode != "-1" && statusCode.split("-").toTypedArray()[1] == "00") {
//                        LoaderDialogue.dismissDialog()
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

                            /*If its 00 , then member is deactivated.
                           mng_amount_fld.setText("")
                           Dialogue.getInstance().showCustomDialog(
                               false,
                               context,
                               "Member is de-activated!",
                               "Ok",
                               object : CustomAlertCallback() {
                                   fun onPositiveAction() {
                                       onBackPressed()
                                   }
                               })
                           return*/

                        }

                        if (statusCode != "-1" && statusCode.split("-")
                                .toTypedArray()[1] == "000"
                        ) {
//                        LoaderDialogue.dismissDialog()
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

                            /*                    If its 000 , then member is deactivated.
                                    voucherRedeemCallback.onRedeemVoucher(false, "Thank you for redeeming the voucher. The e-voucher will be sent to your registered EmailID shortly ");
                                mng_amount_fld.setText("")
                                Dialogue.getInstance().showCustomDialog(
                                    false,
                                    context,
                                    "Unfortunately your redemption has not been completed. Your points redeemed will be reinstated shortly. Please try again later once\n",
                                    "Ok",
                                    object : CustomAlertCallback() {
                                        fun onPositiveAction() {
                                            onBackPressed()
                                        }
                                    })
                                return*/

                        }
                    }
//                    LoaderDialogue.dismissDialog()
//                    SuccessDialogCallback.setApproveDialogue(requireContext(), false, "Oops", "Redemption Failed", object : SuccessDialogCallback.CustomApproveCallback {
//                        override fun onClickOK(mDialogs: Dialog?) {
//                            findNavController().popBackStack()
//                        }
//
//                    })
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

}