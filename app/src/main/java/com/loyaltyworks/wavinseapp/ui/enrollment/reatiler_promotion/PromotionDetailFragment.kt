package com.loyaltyworks.wavinseapp.ui.enrollment.reatiler_promotion

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.GetPromotionDetailsRequest
import com.loyaltyworks.wavinseapp.model.LstPromotionList
import com.loyaltyworks.wavinseapp.model.SaveCustomerPromotionRequest
import kotlinx.android.synthetic.main.promotion_detail_fragment.*

class PromotionDetailFragment : Fragment() {


    //Helper variables :
    var isalreadyReserved = 0
    var promotionID = 0
    var promotionName = ""


    private lateinit var viewModel: RetailerPromotionViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RetailerPromotionViewModel::class.java)
        val root =  inflater.inflate(R.layout.promotion_detail_fragment, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            val promotion: LstPromotionList? = arguments?.getSerializable("offerPromotions") as LstPromotionList?
            if (promotion != null) {
                promotionID = promotion.PromotionId!!
                promotionName = promotion.PromotionName!!
                LoadingDialogue.showDialog(requireContext())
                viewModel.getPromotionDetailByID(
                    GetPromotionDetailsRequest(
                        PreferenceHelper.getLoginDetails(
                            requireContext()
                        )?.UserList!![0].UserId.toString(), promotion.PromotionId
                    )
                )
            }
        }

        viewModel.saveCustomerPromotionLiveData.observe(requireActivity(), Observer {
            if(it!=null && !it.ReturnMessage.isNullOrEmpty()){
                (activity as EnrollmentActivity).snackBar(it.ReturnMessage!!, R.color.black)
            }else{
                (activity as EnrollmentActivity).snackBar(BuildConfig.SOMETHING_WENT_WRONG,R.color.red)
            }
            findNavController().popBackStack()
            LoadingDialogue.dismissDialog()
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPromotionDetailLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.LstPromotionJsonList.isNullOrEmpty()) {


                if(!it.LstPromotionUserActionDetails.isNullOrEmpty()) {

                    comment_ll.visibility = View.VISIBLE

                    if (it.LstPromotionUserActionDetails!![0].CanClaim!!) {
                        claim_btn.visibility = View.VISIBLE
                    }
                    if (it.LstPromotionUserActionDetails!![0].CanReverse!!) {
                        reserve_btn.visibility = View.VISIBLE
                    }
                    if (it.LstPromotionUserActionDetails!![0].AllowUnReserve == 1) {
                        reserve_btn.text = "Unreserve"
                        isalreadyReserved = 0
                    } else {
                        reserve_btn.text = "Reserve"
                        isalreadyReserved = 1
                    }
                    if (it.LstPromotionUserActionDetails!![0].CanComment!!) comment_host.visibility =
                        View.VISIBLE
                }else{
                    //Action control :
                    reserve_btn.visibility = View.GONE
                    comment_ll.visibility = View.GONE
                    claim_btn.visibility = View.GONE
                    comment_host.visibility = View.GONE
                }

                if (it.LstPromotionJsonList!![0].ProShortDesc != null) {
                    short_description_tv.text = it.LstPromotionJsonList!![0].ProShortDesc
                    short_description_tv.visibility = View.VISIBLE
//                    views.visibility = View.VISIBLE
                } else {
                    short_description_tv.visibility = View.GONE
//                    views.visibility = View.GONE
                }
                val webSetting: WebSettings = long_description_tv.settings
                webSetting.allowContentAccess
                webSetting.loadsImagesAutomatically

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    webSetting.mediaPlaybackRequiresUserGesture = true
                }

                webSetting.builtInZoomControls = false
                webSetting.defaultFixedFontSize
                webSetting.textZoom
                webSetting.setSupportMultipleWindows(true)
                webSetting.displayZoomControls = false
                long_description_tv.webViewClient = WebViewClient()
                it.LstPromotionJsonList!![0].ProLongDesc?.let { it1 ->
                    long_description_tv.loadData(
                        it1,
                        "text/html; charset=utf-8",
                        "UTF-8"
                    )
                }
                Log.d(
                    "TAG",
                    "onPromotionDetailsById: " + it.LstPromotionJsonList!![0].ProLongDesc
                )
                long_description_tv.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        return if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                            view.context.startActivity(
                                Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            )
                            true
                        } else {
                            false
                        }
                    }
                }

                Glide.with(this).asBitmap().error(R.drawable.ic_default_img)
                    .placeholder(R.drawable.ic_default_img).load(
                        BuildConfig.PROMO_IMAGE_BASE + it.LstPromotionJsonList!![0].ProImage!!.replace(
                            "..",
                            ""
                        )
                    ).into(prom_image)


            }


        })

        LoadingDialogue.dismissDialog()

        reserve_btn.setOnClickListener {
            LoadingDialogue.showDialog(requireContext())
            if (BlockMultipleClick.click()) return@setOnClickListener
            val IsUnReserved = if (isalreadyReserved == 1) 0 else 1

            viewModel.setSaveCustomerPromotionRequest(
                SaveCustomerPromotionRequest(
                    ActionType = "100",
                    ActorId = PreferenceHelper.getLoginDetails(
                        requireContext()
                    )?.UserList!![0].UserId.toString(),
                    PromotionName = promotionName,
                    PromotioniD = promotionID.toString(),
                    IsReserved = isalreadyReserved.toString(),
                    IsUnReserved = IsUnReserved.toString()
                )
            )

        }

        comment_btn.setOnClickListener {
            if(!comment_fld.text.toString().isNullOrEmpty()) {
                LoadingDialogue.showDialog(requireContext())
                if (BlockMultipleClick.click()) return@setOnClickListener
                viewModel.setSaveCustomerPromotionRequest(
                    SaveCustomerPromotionRequest(
                        ActionType = "102",
                        ActorId = PreferenceHelper.getLoginDetails(
                            requireContext()
                        )?.UserList!![0].UserId.toString(),
                        Comment = comment_fld.text.toString(),
                        PromotioniD = promotionID.toString()
                    )
                )
            }else{
                (activity as EnrollmentActivity).snackBar("Enter Comment",R.color.black)
            }
        }

        claim_btn.setOnClickListener {
            LoadingDialogue.showDialog(requireContext())
            if (BlockMultipleClick.click()) return@setOnClickListener
            viewModel.setSaveCustomerPromotionRequest(
                SaveCustomerPromotionRequest(
                    ActionType = "101",
                    ActorId = PreferenceHelper.getLoginDetails(
                        requireContext()
                    )?.UserList!![0].UserId.toString(),
                    CustomerName = PreferenceHelper.getLoginDetails(
                        requireContext()
                    )?.UserList!![0].Name.toString(),
                    Mobile = PreferenceHelper.getLoginDetails(
                        requireContext()
                    )?.UserList!![0].Mobile.toString(),
                    PromotionName = promotionName,
                    PromotioniD = promotionID.toString()
                )
            )
        }

    }

}