package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.selectRedemption

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.LstCustomerJsons
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.ProductActivity
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import kotlinx.android.synthetic.main.fragment_selecr_redemption.*
import kotlinx.android.synthetic.main.general_fragment.*

class SelectRedemptionFragment : Fragment() {

    private var lstCustomerJsons: ArrayList<LstCustomerJsons>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selecr_redemption, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            if(bundle.getSerializable("SelectedCustomerDetail") as ArrayList<LstCustomerJsons>!=null) {
                lstCustomerJsons = bundle.getSerializable("SelectedCustomerDetail") as ArrayList<LstCustomerJsons>
            }
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        product_catalogue.setOnClickListener {
           if(BlockMultipleClick.click()) return@setOnClickListener
            val bundle = Bundle()
            bundle.putSerializable("SELECT_MEMBER", lstCustomerJsons)
            val intent = Intent(requireActivity(), ProductActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent);

//            startActivity(Intent(requireActivity(), ProductActivity::class.java).putExtra("SELECT_MEMBER", (activity as EnrollmentActivity).customerList))
        }

        voucher.setOnClickListener {
            if(BlockMultipleClick.click()) return@setOnClickListener
            view.findNavController().navigate(R.id.action_selectRedemptionFragment_to_voucherFragment)
        }

    /*    bank_transfer.setOnClickListener {
            if(BlockMultipleClick.click()) return@setOnClickListener
            val bundle = Bundle()
            bundle.putInt("AccountType", 1) //  1 -> Bank Transfer
            view.findNavController().navigate(R.id.action_selectRedemptionFragment2_to_bankTransferFragment, bundle)
        }

        wallet_transfer.setOnClickListener {
            if(BlockMultipleClick.click()) return@setOnClickListener
            val bundle = Bundle()
            bundle.putInt("AccountType", 2) //  2 -> Paytm Transfer
            view.findNavController().navigate(R.id.action_selectRedemptionFragment2_to_bankTransferFragment, bundle)
        }*/

    }

}