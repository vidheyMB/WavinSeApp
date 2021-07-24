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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.CheckCustomerExistancyAndVerificationRequest
import com.loyaltyworks.wavinseapp.model.Location
import com.loyaltyworks.wavinseapp.model.SaveAndGetOTPDetailsRequest
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.mobile_number_verification_fragment.*

class MobileNumberVerificationFragment : Fragment() {


    private lateinit var viewModel: MobileNumberVerificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MobileNumberVerificationViewModel::class.java)
        return inflater.inflate(R.layout.mobile_number_verification_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.mobileNumberExists.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && it.CheckUserNameExistsResult.ReturnValue != null) {
                    if (it.CheckUserNameExistsResult.ReturnValue > 0) {
                        general_mobile2.setText("")
                        general_mobile2.requestFocus()
                        Toast.makeText(
                            requireContext(),
                            "This number already exist",
                            Toast.LENGTH_SHORT
                        ).show()
                        LoadingDialogue.dismissDialog()
                    } else {
                        viewModel.setOTPRequest(
                            SaveAndGetOTPDetailsRequest(
                                MerchantUserName = BuildConfig.MerchantUserName,
                                UserName = general_mobile2.text.toString(),
                                UserId = -1,
                                MobileNo = general_mobile2.text.toString(),
                                OTPType = "Enrollment"
                            )
                        )
                    }

                }
            }
        })

        viewModel.saveAndGetOTPDetailsResponse.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it != null && it.ReturnValue!! > 0) {
                    var bundle = Bundle()
                    bundle.putString("OTP", it.ReturnMessage)
                    bundle.putString("MOBILENUMBER", general_mobile2.text.toString())

                    findNavController().navigate(
                        R.id.action_mobileNumberVerificationFragment_to_generalOTPFragment,
                        bundle
                    )

                } else {
                    (activity as EnrollmentActivity).snackBar(
                        "Something went wrong, please try again later",
                        R.color.red
                    )
                }
                LoadingDialogue.dismissDialog()
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submit.setOnClickListener {
            if(general_mobile2.text.toString().isNotEmpty()){
                LoadingDialogue.showDialog(requireContext())
                viewModel.getMobileEmailExistenceCheck(
                    CheckCustomerExistancyAndVerificationRequest(
                        ActionType = "57", (Location(
                            UserName = general_mobile2.text.toString()
                        ))
                    )
                )
            }else if(general_mobile2.text.toString().isNotEmpty() && general_mobile2.text.toString().length>10){
                (activity as EnrollmentActivity).snackBar("Enter valid mobile number", R.color.red)
            } else{
                (activity as EnrollmentActivity).snackBar("Enter mobile number", R.color.red)
            }
        }
    }

}