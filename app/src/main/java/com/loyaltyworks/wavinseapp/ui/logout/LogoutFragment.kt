package com.loyaltyworks.wavinseapp.ui.logout

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loyaltyworks.wavinseapp.DashboardActivity
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.login.LoginActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper

class LogoutFragment : Fragment() {

    private lateinit var viewModel: LogoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Logout
        (activity as DashboardActivity).productViewModel.deleteAllProduct()
        PreferenceHelper.clear(requireContext())
        requireContext().startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finish()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.logout_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogoutViewModel::class.java)
        // TODO: Use the ViewModel
    }

}