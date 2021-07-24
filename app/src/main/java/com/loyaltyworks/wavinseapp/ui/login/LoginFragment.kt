package com.loyaltyworks.wavinseapp.ui.login

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.model.LoginRequest
import com.loyaltyworks.wavinseapp.model.LoginResponse
import com.loyaltyworks.wavinseapp.DashboardActivity
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity.Companion.TAG
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    var loginResponse: LoginResponse = LoginResponse()

    var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        forgot_pwd.setOnClickListener {
            if (BlockMultipleClick.click()) return@setOnClickListener
            if (code.text.isNotEmpty()) {
                code.setText("")
            }
            if (pwd.text.isNotEmpty()) {
                pwd.setText("")
            }

            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }


        viewModel.loginLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()

            /**
             *  Result = 1 -> Successful
             *           -1 -> Invalid password
             *           -6 -> Invalid membership Id
             *           other -> Invalid User name and password.
             */


            if (it != null && !it.UserList.isNullOrEmpty()) {
                if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    loginResponse = it
                    val result: String = when (it.UserList?.get(0)?.Result) {

                        1 -> {

                            // set Login successful
                            PreferenceHelper.setBooleanValue(
                                requireContext(),
                                BuildConfig.IsLoggedIn,
                                true
                            )

                            // save login details
                            PreferenceHelper.setLoginDetails(requireContext(), loginResponse)
                            LoadingDialogue.dismissDialog()
                            // Go to dashboard
                            val intent = Intent(requireContext(), DashboardActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            // login message display
                            "Login successful "
                        }
                        -1 -> {
                            "Invalid password"
                        }
                        -6 -> {
                            "Invalid retailer code / mobile number"
                        }
                        else -> {
                            "Invalid Username and password"
                        }
                    }

                    if (loginResponse.UserList!![0].Result != 1) {
                        // display snack bar
                        (activity as LoginActivity).snackBar(result, R.color.red)
                    }
                }

            }
        })

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        login.setOnClickListener {

            if (code.text.isEmpty()) {
                (activity as LoginActivity).snackBar("Enter your code / mobile number", R.color.red)
                return@setOnClickListener
            }

            if (pwd.text.toString().isNotEmpty()) {

                getLogin()

            } else {
                (activity as LoginActivity).snackBar("Enter password", R.color.red)
                return@setOnClickListener
            }

        }
    }

    private fun getLogin() {

        LoadingDialogue.showDialog(requireContext())

        //Push token :
        token = PreferenceHelper.getStringValue(context = requireContext(), BuildConfig.PUSH_TOKEN)
        Log.d(TAG, "token : $token")

        /*  if (token!!.isEmpty()) token = FirebaseInstanceId.getInstance().token
          print("onCreate: TOKEN $token")

          token = FirebaseInstanceId.getInstance().token
  */

        // login api call
        viewModel.getLoginData(
            LoginRequest(
                Browser = "Android",
                LoggedDeviceName = "Android",
                UserName = code.text.toString(),
                Password = pwd.text.toString(),
                PushID = "",
                UserId = "-1",
                UserActionType = "GetPasswordDetails",
                UserType = "User"
            )
        )

    }

}