package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift.adapter.DreamGfitAdapter
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.reatiler_dream_gift_fragment.*

class ReatilerDreamGiftFragment : Fragment(), DreamGfitAdapter.ClickOnItemList {

    private lateinit var viewModel: ReatilerDreamGiftViewModel

    var dreamGiftAdapter: DreamGfitAdapter? = null

    lateinit var lstCustomerJsons: CustomerDetailsResponseJson


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.reatiler_dream_gift_fragment, container, false)

        viewModel = ViewModelProvider(this).get(ReatilerDreamGiftViewModel::class.java)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* dreamGiftAdapter = DreamGfitAdapter(null, this)
         dreamGift_rv.adapter = dreamGiftAdapter*/

        val bundle = this.arguments
        if (bundle != null) {
            lstCustomerJsons =
                arguments?.getSerializable("SelectedReatilerDetail") as CustomerDetailsResponseJson
        }

        DreamGiftListing()

    }

    private fun DreamGiftListing() {

        LoadingDialogue.showDialog(requireContext())

        viewModel.getDreamGiftList(
            DreamGiftRequest(
                ActionType = "1",
                ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                LoyaltyId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].LoyaltyId

            )
        )

    }


    override fun onClickDreamGiftDetail(position: Int, dreamGift: List<LstDreamGift?>?) {

        val bundle = Bundle()
        bundle.putSerializable("SelectedDreamGift", dreamGift!![position])
        bundle.putString("SelectedCustomerLoayltyId", lstCustomerJsons.LoyaltyId)
        findNavController().navigate(R.id.action_reatilerDreamGiftFragment_to_retailerDreamGiftDetailFragment, bundle)
    }

    override fun onClickDreamGiftRemove(
        position: Int,
        dreamGiftArrayList: List<LstDreamGift?>?,
        dreamGiftID: String?,
        dreamGiftStatusID: String?
    ) {

        LoadingDialogue.showDialog(requireContext())

        viewModel.getDreamGiftRemoveList(
            DreamGiftRemoveRequest(
                ActionType = "4",
                ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                DreamGiftId = dreamGiftID,
                GiftStatusId = "4"
            )
        )


    }

    override fun onClickDreamGiftRedeem(
        position: Int,
        dreamGift: List<LstDreamGift?>?,
        dreamGiftID: String?
    ) {
        val dreamGiftList = mutableListOf<ObjCatalogueList>()
        val objCatalogueLists = ObjCatalogueList(
            DreamGiftId = dreamGift!![position]!!.DreamGiftId,
            LoyaltyId =  dreamGift!![position]!!.LoyaltyID,
            NoOfPointsDebit =  dreamGift!![position]!!.PointsRequired!!.toInt(),
            NoOfQuantity = 1, // Default Quantity = 1
            PointBalance =  dreamGift!![position]!!.PointsBalance,
            PointsRequired =  dreamGift!![position]!!.PointsRequired,
            ProductName =  dreamGift!![position]!!.DreamGiftName,
            RedemptionTypeId =  3
//            CatalogueId =  3
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

//        val bundle = Bundle()
//        bundle.putSerializable("LstDreamGift", dreamGift!![position])
        findNavController().navigate(
            R.id.action_reatilerDreamGiftFragment_to_shippingAddressFragment,
            bundle
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dreamGiftlist = ArrayList<LstDreamGift>()



        viewModel.getDreamGiftLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (!it.lstDreamGift.isNullOrEmpty()) {

                dreamGiftlist.clear()
                error_txt.visibility = View.GONE
                dreamGift_rv.visibility = View.VISIBLE

                it.lstDreamGift.forEachIndexed { index, lstDreamGift ->

//                    if (lstDreamGift.GiftStatusId != 4) {

                        dreamGiftlist.add(lstDreamGift)
//                    }

                }

                val gson = Gson()

                Log.d("fjhdksf",gson.toJson(dreamGiftlist))

                dreamGiftAdapter = DreamGfitAdapter(dreamGiftlist.toList() , this)
                dreamGift_rv.adapter = dreamGiftAdapter

            } else {

                error_txt.visibility = View.VISIBLE
                dreamGift_rv.visibility = View.GONE
            }

        })


        viewModel.getDreamGiftRemoveLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null && it.ReturnValue == 1) {
                DreamGiftListing()
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.add_dreamgift -> {
                val bundle = Bundle()
                bundle.putSerializable("SelectedCustomer", lstCustomerJsons)
                findNavController().navigate(
                    R.id.action_reatilerDreamGiftFragment_to_addDreamGiftFragment,
                    bundle
                )
            }

            R.id.ic_pop -> {
                popupDialog()
            }

        }

        return super.onOptionsItemSelected(item)
    }


    private fun popupDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.popup_dialog, null)
        dialogBuilder.setView(dialogView)
        val okText = dialogView.findViewById<TextView>(R.id.ok_text)
        val msgText = dialogView.findViewById<TextView>(R.id.dialog_msg)
        msgText.text = resources.getText(R.string.popupString)
        val infoDialog = dialogBuilder.create()
        infoDialog.show()
        infoDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = infoDialog.window
        window!!.setGravity(Gravity.BOTTOM)
        //                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        val layoutParams = infoDialog.window!!.attributes
        layoutParams.y = 30 // bottom margin
        infoDialog.window!!.attributes = layoutParams
        okText.setOnClickListener { infoDialog.dismiss() }
    }
}