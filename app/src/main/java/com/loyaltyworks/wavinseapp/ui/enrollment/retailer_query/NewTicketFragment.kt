package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query

import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter.HelpTopicSpinnersAdapter
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.Keyboard
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.ProgressDialogue
import com.loyaltyworks.wavinseapp.model.GetHelpTopicRetrieveRequest
import com.loyaltyworks.wavinseapp.model.HelpTopicRetrieveRequest
import com.loyaltyworks.wavinseapp.model.ObjHelpTopicList
import com.loyaltyworks.wavinseapp.model.SaveNewTicketQueryRequest
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.member_list_row.*
import kotlinx.android.synthetic.main.new_ticket_fragment.*

class NewTicketFragment : Fragment() ,View.OnClickListener, AdapterView.OnItemSelectedListener{

    private var helpTopicId: Int = -1
    private var helpTopicName: String = ""


    private val PICKFILE_REQUEST_CODE: Int = 24
    private var outputFileUri: Uri? = null

    private  var fileExtenstion:String = ""
    private var mProfileImagePath = ""

    private lateinit var viewModel: NewTicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewTicketViewModel::class.java)
        return inflater.inflate(R.layout.new_ticket_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.saveNewTicketQueryLiveData.observe(viewLifecycleOwner, Observer {
            Keyboard.hideKeyboard(requireContext(),requireView())

            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                val message: String = java.lang.String.valueOf(it.ReturnMessage)
                if (!TextUtils.isEmpty(message) && message.split("~".toRegex()).toTypedArray()[0].toInt() > 0
                ) {
                    (activity as EnrollmentActivity).snackBar(
                        "Support Ticket has been submitted successfully",
                        R.color.primary_dark_blue
                    )
                    view?.findNavController()?.popBackStack()
                } else {
                    (activity as EnrollmentActivity).snackBar(
                        "Failed to submit new ticket",
                        R.color.red
                    )
                    view?.findNavController()?.popBackStack()
                }
                LoadingDialogue.dismissDialog()
            } else {
                (activity as EnrollmentActivity).snackBar(
                    "Something went wrong try later",
                    R.color.primary_dark_blue
                )

                LoadingDialogue.dismissDialog()
            }
        })
        browseImg.setOnClickListener(this)
        query_submit.setOnClickListener(this)
        help_topic_spinner.onItemSelectedListener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoadingDialogue.showDialog(requireContext())

        if (!(activity as EnrollmentActivity).customerList!!.VerifiedName.isNullOrEmpty())
            mem_list_approve.text =
                (activity as EnrollmentActivity).customerList!!.VerifiedName
        else mem_list_approve.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.FullName.isNullOrEmpty())
            mem_list_username.text =
                (activity as EnrollmentActivity).customerList!!.FullName
        else mem_list_username.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.LoyaltyId.isNullOrEmpty())
            mem_list_membershipId.text =
                (activity as EnrollmentActivity).customerList!!.LoyaltyId
        else mem_list_membershipId.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.UserOneHeader.isNullOrEmpty())
            ASMHeader.text =
                (activity as EnrollmentActivity).customerList!!.UserOneHeader
        else ASMHeader.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.AsmName.isNullOrEmpty())
            mem_list_asm.text = (activity as EnrollmentActivity).customerList!!.AsmName
        else mem_list_asm.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.UserTwoHeader.isNullOrEmpty())
            SEHeader.text =
                (activity as EnrollmentActivity).customerList!!.UserTwoHeader
        else SEHeader.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.SeName.isNullOrEmpty())
            mem_list_se.text = (activity as EnrollmentActivity).customerList!!.SeName
        else mem_list_se.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.CreatedByName.isNullOrEmpty())
            mem_list_createdby.text =
                (activity as EnrollmentActivity).customerList!!.CreatedByName
        else mem_list_createdby.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.LocationName.isNullOrEmpty())
            mem_list_branch.text =
                (activity as EnrollmentActivity).customerList!!.LocationName
        else mem_list_branch.text = "-"

        if (!(activity as EnrollmentActivity).customerList!!.CreatedDate.toString().isNullOrEmpty()) {
            mem_list_date.text = (activity as EnrollmentActivity).customerList!!.CreatedDate
        }else{
            mem_list_date.text = "-"
        }


        if (!(activity as EnrollmentActivity).customerList!!.PointsBalance.toString().isNullOrEmpty()) {
            mem_list_point.text = (activity as EnrollmentActivity).customerList!!.PointsBalance.toString()
        }
        else{
            mem_list_point.text = "0"
        }

        if (!(activity as EnrollmentActivity).customerList!!.CustomerGrade.isNullOrEmpty()) {
            mem_list_tier.text =
                (activity as EnrollmentActivity).customerList!!.CustomerGrade
        }
        else{
            mem_list_tier.text = "-"
        }


        if ((activity as EnrollmentActivity).customerList!!.IsActive!!) {
            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
            mem_list_status.text = "Active"
        } else {
            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
            mem_list_status.text = "Inactive"
        }

        if ((activity as EnrollmentActivity).customerList!!.VerifiedTypeId == 1) {
            mem_list_verifyImg.setImageDrawable(
                requireContext().resources.getDrawable(R.drawable.ic_active)
            )
            mem_list_approve.text = (activity as EnrollmentActivity).customerList!!.VerifiedName
        } else {
            mem_list_verifyImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
            mem_list_approve.text = (activity as EnrollmentActivity).customerList!!.VerifiedName
        }

        if((activity as EnrollmentActivity).customerList!!.VerifiedName.equals("Verified", true)){
            mem_list_verifyImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
        }else{
            mem_list_verifyImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
        }

        if(mem_list_status.text.toString().equals("Active", true)){
            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
        }else{
            mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
        }

        viewModel.getHelpTopicListLiveData(
            HelpTopicRetrieveRequest(
                GetHelpTopicRetrieveRequest(
                    "4", PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(), "true"
                )
            )
        )

        getObserver()

    }


    private fun getObserver() {
        viewModel.topicListLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.GetHelpTopicsResult?.objHelpTopicList != null) {
//                val helpTypeList: MutableList<ObjHelpTopicList> = it.getHelpTopicsResult?.objHelpTopicList as MutableList<ObjHelpTopicList>
                var helpTypeList = mutableListOf<ObjHelpTopicList>()
                helpTypeList.addAll(it.GetHelpTopicsResult.objHelpTopicList)
                helpTypeList.add(
                    0, ObjHelpTopicList(
                        HelpTopicName = "Select Query Topic",
                        HelpTopicId = -1
                    )
                )
                help_topic_spinner.adapter = HelpTopicSpinnersAdapter(
                    requireContext(),
                    helpTypeList
                )

                LoadingDialogue.dismissDialog()
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        helpTopicId = (parent!!.getItemAtPosition(position) as ObjHelpTopicList).HelpTopicId!!
        helpTopicName = (parent.getItemAtPosition(position) as ObjHelpTopicList).HelpTopicName!!
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onClick(v: View) {

        if(BlockMultipleClick.click()) return

        when (v.id) {
            R.id.browseImg -> activity?.let {
                ProgressDialogue.dismissDialog()
                // Browse Image or Files
                FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                    FileSelectorCallBack {
                    override fun onResponse(fileSelectorData: FileSelectorData) {
                        mProfileImagePath = fileSelectorData.responseInBase64!!
                        fileExtenstion = fileSelectorData.extension!!

                        new_ticket_img.visibility = View.VISIBLE
                        new_ticket_img.setImageBitmap(fileSelectorData.thumbnail)

                    }
                })

                if(mProfileImagePath.isNullOrEmpty()){
                    ProgressDialogue.dismissDialog()
                }

            }

            R.id.query_submit -> {
                // hide keyboard
                Keyboard.hideKeyboard(requireContext(), requireView())

                //Validate before submit query :
                if (helpTopicId === -1) {
                    (activity as EnrollmentActivity).snackBar("Select Query Topic", R.color.red)
                    return
                } /*else if (TextUtils.isEmpty(query_summary.text.toString())) {
                    query_summary.error = "Enter query summary"
                    query_summary.requestFocus()
                    return
                }*/
              /*  else if (query_summary.text.toString().startsWith(" ")) {
                    query_summary.text.clear()
                    query_summary.error = "Enter query summary"
                    query_summary.requestFocus()
                    return
                }*/
                else if (TextUtils.isEmpty(query_details.text.toString())) {
                    query_details.error = "Enter query details"
                    query_details.requestFocus()
                    return
                } else if (query_details.text.toString().startsWith(" ")) {
                    query_details.text.clear()
                    query_details.error = "Enter query details"
                    query_details.requestFocus()
                    return
                } else {

                    try {
                        LoadingDialogue.showDialog(requireContext())
                        viewModel.saveNewTicketQuery(
                            SaveNewTicketQueryRequest(
                                ActionType = "0",
                                ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                                CustomerName = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].FirstName.toString(),
                                Email = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].Email.toString(),
                                HelpTopic = helpTopicName,
                                HelpTopicID = helpTopicId.toString(),
                                IsQueryFromMobile = "true",
                                LoyaltyID = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].LoyaltyId.toString(),
                                Mobile = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].Mobile.toString(),
                                QueryDetails = query_details.text.toString(),
                                QuerySummary = query_details.text.toString(),
                                SourceType = "1",
                                FileType = fileExtenstion,
                                ImageUrl = mProfileImagePath
                            )
                        )
//
                    } catch (e: Exception) {
                        Log.d("TAG", "onClick: $e")
                    }
                }
            }

        }
    }

}