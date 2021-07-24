package com.loyaltyworks.wavinseapp.ui.login.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.login.LoginActivity
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.model.ForgotPasswordRequest
import com.loyaltyworks.wavinseapp.utils.dialogBox.DeactivateDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.DialogueCallBack
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.forgot_password_fragment.*

class ForgotPasswordFragment : Fragment() {

    private lateinit var viewModel: ForgotPasswordViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =  ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        return inflater.inflate(R.layout.forgot_password_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        /***   forgot password observe ***/

        viewModel.forgotPasswordLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()

            if (it != null && it.forgotPasswordMobileAppResult == true) {
                DeactivateDialogue.showPopUpDialog(
                    requireContext(),
                    false,
                    "",
                    "OK",
                    "We have sent the new password to your \nregistered Mobile Number!",
                    object : DialogueCallBack {
                        override fun onResponse(response: String) {
                            findNavController().popBackStack(R.id.loginFragment, false)
                        }

                        override fun onAgain() {
                            findNavController().popBackStack()
                        }

                    })

            } else {
                membershipid.setText("")
                (activity as LoginActivity).snackBar("Please enter valid membership ID / Mobile number !", R.color.red)
                LoadingDialogue.dismissDialog()
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit.setOnClickListener {
            if(BlockMultipleClick.click()) return@setOnClickListener
            if(membershipid.text.isNotEmpty()) {
                LoadingDialogue.showDialog(requireContext())
                viewModel.getForgotPwd(ForgotPasswordRequest(membershipid.text.toString()))
            }else{
                (activity as LoginActivity).snackBar("Enter Membership ID / Mobile number", R.color.red)
            }

        }

        back.setOnClickListener {
            findNavController().popBackStack(R.id.loginFragment, false)
        }

    }

}