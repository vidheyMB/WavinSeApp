package com.loyaltyworks.wavinseapp.ui.enrollment.member_status

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.member_status_fragment.*

class MemberStatusFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = MemberStatusFragment()
    }

    private var lstCustomerJsons: ArrayList<LstCustomerJsons>? = null

    var StatusUpdate: Int? = null


    private lateinit var viewModel: MemberStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MemberStatusViewModel::class.java)

        return inflater.inflate(R.layout.member_status_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = arguments
        lstCustomerJsons = bundle?.getSerializable("MEMBER_STATUS") as ArrayList<LstCustomerJsons>

        m_status_spinner.onItemSelectedListener = this

        setStatusSpinner()
        updateStatus()
    }

    private fun updateStatus() {

        Update_status.setOnClickListener {
            LoadingDialogue.showDialog(requireContext())
            viewModel.getMemberStatusUpdate(
                MemberStatusRequest(
                    ActionType = "8",
                    ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                    CustomerOrderStatus = StatusUpdate,
                    ObjCustomerr(
                        LoyaltyId = lstCustomerJsons!![0].LoyaltyId
                    ),
                    ObjCustomerDetai()
                )
            )
        }


        viewModel.getMemberStatusLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && it.ReturnMessage!!.split(":")[0].toInt() > 0) {
                Toast.makeText(
                    requireContext(),
                    "Member status updated successfully ",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Failed to update member status",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })

    }

    private fun setStatusSpinner() {


        val mStatusList = java.util.ArrayList<CommonSpinner>()

        val status1 = CommonSpinner()
        status1.id = 1
        status1.name = "Auto Approve"

        val status2 = CommonSpinner()
        status2.id = -1
        status2.name = "Auto Reject"

        val status3 = CommonSpinner()
        status3.id = 0
        status3.name = "Pending"


        mStatusList.add(status1)
        mStatusList.add(status2)
        mStatusList.add(status3)

        m_status_spinner.adapter = CommonAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mStatusList
        )

        val CustomerStatusId =
            PreferenceHelper.getCustomerDashboard(requireContext())!!.ObjCustomerDashboardList!![0].CustomerOrderStatusId

        if (CustomerStatusId == 1) {
            m_status_spinner.setSelection(0)
        } else if (CustomerStatusId == -1) {
            m_status_spinner.setSelection(1)
        } else {
            m_status_spinner.setSelection(2)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val id = (parent!!.getItemAtPosition(position) as CommonSpinner).id!!

        when (parent.id) {
            R.id.m_status_spinner -> {
                StatusUpdate = id
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}