package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.DeactivateDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.DialogueCallBack
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.retailer_dream_gift_detail_fragment.*

class RetailerDreamGiftDetailFragment : Fragment() {

    private lateinit var viewModel: ReatilerDreamGiftViewModel

    var lstDreamGift: List<LstDreamGift>? = null

    lateinit var dreamGift: LstDreamGift
    lateinit var selectedCustomerLoyaltyId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ReatilerDreamGiftViewModel::class.java)
        return inflater.inflate(R.layout.retailer_dream_gift_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            dreamGift = arguments?.getSerializable("SelectedDreamGift") as LstDreamGift
            selectedCustomerLoyaltyId = arguments?.getString("SelectedCustomerLoayltyId")!!
        }


        DreamGiftDetails()
        removeDreamGift()


    }

    private fun removeDreamGift() {


        remove_btn.setOnClickListener {

            if (BlockMultipleClick.click()) {
                return@setOnClickListener
            }

            DeactivateDialogue.showPopUpDialog(
                requireContext(),
                true,
                "No",
                "Yes",
                "Are you sure you want to cancel\n the dream gift?",
                object : DialogueCallBack {
                    override fun onResponse(response: kotlin.String) {

                    }

                    override fun onAgain() {

                        LoadingDialogue.showDialog(requireContext())

                        viewModel.getDreamGiftRemoveList(
                            DreamGiftRemoveRequest(
                                ActionType = "4",
                                ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                                DreamGiftId = dreamGift.DreamGiftId.toString(),
                                GiftStatusId = dreamGift.GiftStatusId.toString()
                            )
                        )


                    }
                })
        }

        redem_btn.setOnClickListener {

            val dreamGiftList = mutableListOf<ObjCatalogueList>()
            val objCatalogueLists = ObjCatalogueList(
                DreamGiftId = dreamGift.DreamGiftId,
                LoyaltyId =  dreamGift.LoyaltyID,
                NoOfPointsDebit =  dreamGift.PointsRequired!!.toInt(),
                NoOfQuantity = 1, // Default Quantity = 1
                PointBalance =  lstDreamGift!![0].PointsBalance,
                PointsRequired =  lstDreamGift!![0].PointsRequired,
                ProductName =  lstDreamGift!![0].DreamGiftName,
                RedemptionTypeId = 3
//                CatalogueId =  lstDreamGift!![0].DreamGiftId
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
            findNavController().navigate(
                R.id.action_retailerDreamGiftDetailFragment_to_shippingAddressFragment,
                bundle
            )
        }


    }

    private fun DreamGiftDetails() {

        viewModel.getDreamGiftList(
            DreamGiftRequest(
                ActionType = "243",
                ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                LoyaltyId = selectedCustomerLoyaltyId,
                DreamGiftId = dreamGift.DreamGiftId.toString()
            )
        )


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        /* Get Dream Gift Details By Dream Gift Id*/
        viewModel.getDreamGiftLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()

            if (!it.lstDreamGift.isNullOrEmpty()) {
                lstDreamGift = it.lstDreamGift
                dreamGiftName_tv.text = lstDreamGift!![0].DreamGiftName
                dreamGiftType.text = "Gift Type : " + lstDreamGift!![0].GiftType
                hint_txt.text =
                    "Congratulations! You are almost near \n to win this exciting Dream Gift."
                pointBalance.text =
                    "Redeemable points as on today " + lstDreamGift!![0].PointsBalance as Int + " points"
                pointsRequired.text = "Points Required : " + lstDreamGift!![0].PointsRequired as Int

                if (dreamGift.Status.equals("Rejected")) {
                    gift_status.text = "Status : " + lstDreamGift!![0].Status
                    gift_remark.text = "Remark : " + lstDreamGift!![0].Remark
                    gift_status.visibility = View.VISIBLE
                    gift_remark.visibility = View.VISIBLE
                } else {
                    gift_status.visibility = View.GONE
                    gift_remark.visibility = View.GONE
                }

                avergaePointOne.text = lstDreamGift!![0].EarlyExpectedPoints.toString()
                avergaePointTwo.text = lstDreamGift!![0].AvgEarningPoints.toString()
                avergaePointThree.text = lstDreamGift!![0].LateExpectedPoints.toString()
                PossibelDateOne.text = java.lang.String.valueOf(lstDreamGift!![0].EarlyExpectedDate)
                PossibelDateTwo.text = java.lang.String.valueOf(lstDreamGift!![0].ExpectedDate)
                PossibelDateThree.text = java.lang.String.valueOf(lstDreamGift!![0].LateExpectedDate)

                if (lstDreamGift!![0].Status.equals("Pending") || lstDreamGift!![0].Status.equals("Rejected")) {
                    redem_btn.visibility = View.GONE
                } else if (lstDreamGift!![0].PointsBalance!! >= lstDreamGift!![0].PointsRequired!! && lstDreamGift!![0].Status.equals("Approved")) {
                    redem_btn.visibility = View.VISIBLE
                    redem_btn.isEnabled = true
                    redem_btn.setBackgroundResource(R.drawable.bottomred_bg)
                } else {
                    redem_btn.visibility = View.GONE
                    redem_btn.isEnabled = false
                    redem_btn.setBackgroundResource(R.drawable.button_style)
                }

            } else {
                Toast.makeText(requireContext(), "Dream Gift detail not found!", Toast.LENGTH_SHORT).show()
            }

        })


        /* Remove Dream Gift Response*/
        viewModel.getDreamGiftRemoveLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null && it.ReturnValue == 1) {
                findNavController().popBackStack()
                Toast.makeText(
                    requireContext(),
                    "Dream gift removed successfully",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(requireContext(), "Dream Gift Remove failed", Toast.LENGTH_SHORT)
                    .show()
            }

        })


    }

}