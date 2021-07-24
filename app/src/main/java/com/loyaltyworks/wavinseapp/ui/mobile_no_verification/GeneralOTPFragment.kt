package com.loyaltyworks.wavinseapp.ui.mobile_no_verification

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import kotlinx.android.synthetic.main.general_o_t_fragment.*

class GeneralOTPFragment : Fragment() {

    private lateinit var viewModel: GeneralOTViewModel

    var OTP : String=""
    var MobileNumber : String=""
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GeneralOTViewModel::class.java)
        return inflater.inflate(R.layout.general_o_t_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val b = arguments
        OTP = b!!.getString("OTP")!!
        MobileNumber = b.getString("MOBILENUMBER")!!

        verify.setOnClickListener {
            if(BlockMultipleClick.click()) return@setOnClickListener
            if (otp_view.otp.toString() == "") {
                Toast.makeText(context, "Please enter One-Time Password (OTP)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (otp_view.otp.toString() != "" && otp_view.otp.toString().length < 6 ){
                Toast.makeText(
                    context,
                    "Error! Please enter a valid One-Time Password (OTP)",
                    Toast.LENGTH_SHORT
                ).show();
                return@setOnClickListener
            }else if (otp_view.otp != OTP) {
                (activity as EnrollmentActivity).snackBar("Error! Please enter valid One-Time Password (OTP)", R.color.red)
            }else{
                findNavController().navigate(R.id.action_generalOTPFragment_to_generalFragment,bundleOf(Pair("MOBILENUMBER", MobileNumber)))
            }

        }
        
    }
}