package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.shippingAddress

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.model.LstCustomerJsons
import com.loyaltyworks.wavinseapp.model.MyProfileRequest
import com.loyaltyworks.wavinseapp.model.ObjCustShippingAddressDetail
import com.loyaltyworks.wavinseapp.model.RedeemGiftCatalogueRequest
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.shipping_address_fragment.*

class ShippingAddressFragment : Fragment() {

    var savedCustomerDetails: LstCustomerJsons? = null
    var profiledata: List<LstCustomerJsons>? = null

    // DreamGiftData to redeem dreamGift
    lateinit var dreamGiftData: RedeemGiftCatalogueRequest

    val shippingAddressDetails = ObjCustShippingAddressDetail()

    private lateinit var viewModel: ShippingAddressViewModel

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
        return inflater.inflate(R.layout.shipping_address_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.myProfileResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson.isNullOrEmpty()) {

                it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!!.forEach { lstCustomerDetail ->
                    savedCustomerDetails = lstCustomerDetail
                }

                profiledata = it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!!

                name.text =
                    it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].FirstName + " " + it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].LastName

                address.text =
                    it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].Address1 + " \n" +
                            it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].CityName + " , " +
                            it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].StateName + " \n" +
                            it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].CountryName + " - " + it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].Zip + "\n" +
                            "Mobile - " + it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].Mobile + " \n"
                "Email - " + it.GetCustomerDetailsMobileAppResult!!.lstCustomerJson!![0].Email


                // set data to send address
                shippingAddressDetails.Mobile = savedCustomerDetails!!.Mobile
                shippingAddressDetails.Zip = savedCustomerDetails!!.Zip
                shippingAddressDetails.Address1 = savedCustomerDetails!!.Address1
                shippingAddressDetails.StateId = savedCustomerDetails!!.StateId.toString()
                shippingAddressDetails.CityId = savedCustomerDetails!!.CityId.toString()
                shippingAddressDetails.CountryId = savedCustomerDetails!!.CountryId.toString()
                shippingAddressDetails.FullName = savedCustomerDetails!!.FirstName.toString()
                shippingAddressDetails.Email = savedCustomerDetails!!.Email.toString()

            }

//            LoaderDialogue.dismissDialog()
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        points.text =
            "Points " + PreferenceHelper.getCustomerDashboard(requireContext())!!.ObjCustomerDashboardList!![0].RedeemablePointsBalance!!


        viewModel = ViewModelProvider(this).get(ShippingAddressViewModel::class.java)

        if (arguments != null) {
            // If this data not null, than redemption will happen for dreamGift
            try {
                dreamGiftData =
                    requireArguments().getSerializable("DreamGiftData") as RedeemGiftCatalogueRequest
            } catch (e: Exception) {
            }
        }


        LoadingDialogue.showDialog(requireContext())
        viewModel.setUserDetailRequest(
            MyProfileRequest(
                ActionType = "6",
                CustomerId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString()
            )
        )

        proceed.setOnClickListener {
            if (BlockMultipleClick.click()) {
                return@setOnClickListener
            }
            if (shippingAddressDetails.CityId!!.toInt() < 1 || shippingAddressDetails.StateId!!.toInt() < 1 || shippingAddressDetails.Address1.isNullOrEmpty() || shippingAddressDetails.Zip.isNullOrEmpty() || shippingAddressDetails.Mobile.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please provide valid shipping address",
                    Toast.LENGTH_SHORT
                ).show();
//                (activity as DashboardActivity).snackBar("Please provide valid shipping address", R.color.red)
                return@setOnClickListener
            } else {
                val bundle = Bundle()
                bundle.putSerializable("Address", shippingAddressDetails)

                if (this::dreamGiftData.isInitialized) {
                    dreamGiftData.ObjCustShippingAddressDetails = shippingAddressDetails
                    bundle.putSerializable("DreamGiftData", dreamGiftData)
                }

                findNavController().navigate(
                    R.id.action_shippingAddressFragment_to_OTPFragment,
                    bundle
                )
            }

        }

        edit.setOnClickListener {

            if (BlockMultipleClick.click()) {
                return@setOnClickListener
            }

            val bundle = Bundle()
//            bundle.putSerializable("Address", shippingAddressDetails)

            if (this::dreamGiftData.isInitialized) {
                dreamGiftData.ObjCustShippingAddressDetails = shippingAddressDetails
                bundle.putSerializable("DreamGiftData", dreamGiftData)
            }

            findNavController().navigate(
                R.id.action_shippingAddressFragment_to_editAddressFragment,
                bundle
            )
        }
    }

}