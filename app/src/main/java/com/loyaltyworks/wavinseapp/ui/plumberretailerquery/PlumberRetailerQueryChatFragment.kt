package com.loyaltyworks.wavinseapp.ui.plumberretailerquery

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.DashboardActivity
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.adapter.CommonAdapter
import com.loyaltyworks.wavinseapp.model.CommonSpinner
import com.loyaltyworks.wavinseapp.model.ObjQueryCenterAPIInfo
import com.loyaltyworks.wavinseapp.model.SEQueryChatReplyRequest
import com.loyaltyworks.wavinseapp.model.SEQueryChatRequest
import com.loyaltyworks.wavinseapp.ui.plumberretailerquery.adapter.PlumberRetailerQueryChatAdapter
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.utils.dialogBox.ProgressDialogue
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_plumber_retailer_query_chat.*
import java.util.*


class PlumberRetailerQueryChatFragment : Fragment(),
    PlumberRetailerQueryChatAdapter.ChatImageDisplay {

    var plumberRetailerQueryAdapter: PlumberRetailerQueryChatAdapter? = null
    private lateinit var viewModel: PlumberRetailerQueryViewModel
    private var mProfileImagePath = ""
    var objQueryCenterAPIInfo = ObjQueryCenterAPIInfo()

    var statusSpinner = 0

    var seQueryChatReplyRequest = SEQueryChatReplyRequest()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(PlumberRetailerQueryViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plumber_retailer_query_chat, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bundle = arguments
        if (bundle != null) {
            objQueryCenterAPIInfo =
                bundle!!.getSerializable("ObjQueryCenterAPIInfo") as ObjQueryCenterAPIInfo
            getQueryList()

        }

        val statusSpinnerList: ArrayList<CommonSpinner> = ArrayList<CommonSpinner>()
        statusSpinnerList.add(CommonSpinner("Select Status", -1))
        statusSpinnerList.add(CommonSpinner("Pending", 1))
        statusSpinnerList.add(CommonSpinner("Re-Open", 2))
        statusSpinnerList.add(CommonSpinner("Resolved", 3))
        statusSpinnerList.add(CommonSpinner("Closed", 4))
        statusSpinnerList.add(CommonSpinner("Resolved-Follow Up", 5))

        status_spinner.adapter = CommonAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statusSpinnerList
        )


        status_spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                statusSpinner = (parent.getItemAtPosition(position) as CommonSpinner).id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        // set status spinner
        when (objQueryCenterAPIInfo.QueryStatus) {
            "Pending" -> {
                status_spinner.setSelection(1)
            }
            "Re-Open" -> {
                status_spinner.setSelection(2)
            }
            "Resolved" -> {
                status_spinner.setSelection(3)
            }
            "Closed" -> {
                status_spinner.setSelection(4)
            }
            "Resolved-Follow Up" -> {
                status_spinner.setSelection(5)
            }
            else -> {
                status_spinner.setSelection(0)
            }
        }

        viewModel.seQueryChatReplyResponse.observe(viewLifecycleOwner, Observer {
            if (it?.ReturnMessage != null && it.ReturnMessage.split("~")[0].toInt() > 0) {
                val msg = it.ReturnMessage.split("~")[1]
                getQueryList()
                (activity as DashboardActivity).snackBar(
                    msg,
                    R.color.primary_dark_blue
                )
                if (query_details_fld.text.isNotEmpty()) {
                    query_details_fld.text.clear()
                }
                imageAdd.setImageDrawable(resources.getDrawable(R.drawable.ic_attach))
            } else {
                (activity as DashboardActivity).snackBar("Query post failed.", R.color.red)
            }
//            findNavController().popBackStack()
            LoadingDialogue.dismissDialog()
        })

    }

    private fun getQueryList() {
        LoadingDialogue.showDialog(requireContext())
        (activity as DashboardActivity).toolbar.title = objQueryCenterAPIInfo.HelpTopic
        viewModel.setSEQueryDetailByIDRequest(
            SEQueryChatRequest(
                ActionType = "171", ActorId = PreferenceHelper.getLoginDetails(
                    requireContext()
                )!!.UserList!![0].UserId.toString(),
                CustomerTicketID = objQueryCenterAPIInfo.CustomerTicketID.toString()
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.seQueryChatRequest.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.objQueryResponseJsonList.isNullOrEmpty()) {
                plumberRetailerQueryAdapter = PlumberRetailerQueryChatAdapter(
                    it.objQueryResponseJsonList,
                    this
                )
                query_chat_recycler.adapter = plumberRetailerQueryAdapter
            }
            LoadingDialogue.dismissDialog()
        })

        send_query_btn.setOnClickListener {
            if (BlockMultipleClick.click()) return@setOnClickListener
            setPostReply("PostReply")
        }

        internalQuery_btn.setOnClickListener {
            if (BlockMultipleClick.click()) return@setOnClickListener
            setPostReply("PostInternalNotes")
        }

        imageAdd.setOnClickListener {
            ProgressDialogue.dismissDialog()
            // Browse Image or Files
            FileSelector.requiredFileTypes(FileType.IMAGES).open(requireActivity(), object :
                FileSelectorCallBack {
                override fun onResponse(fileSelectorData: FileSelectorData) {
                    mProfileImagePath = fileSelectorData.responseInBase64!!
                    imageAdd.setImageBitmap(fileSelectorData.thumbnail)
                }
            })

            if (mProfileImagePath.isNullOrEmpty()) {
                ProgressDialogue.dismissDialog()
            }
        }

        closeImage.setOnClickListener {
            ChatImageOpen.visibility = View.GONE
        }
    }


    private fun setPostReply(replyType: String) {

        if (query_details_fld.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter query details", Toast.LENGTH_SHORT)
                .show()
            return
        }

        LoadingDialogue.showDialog(requireContext())

        seQueryChatReplyRequest.ActionType = "0"
        seQueryChatReplyRequest.ActorId =
            PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString()
        seQueryChatReplyRequest.CustomerTicketID = objQueryCenterAPIInfo.CustomerTicketID.toString()
        seQueryChatReplyRequest.HelpTopicID = objQueryCenterAPIInfo.HelpTopicId.toString()
        if (!mProfileImagePath.isNullOrEmpty()) seQueryChatReplyRequest.ImageUrl = mProfileImagePath
        seQueryChatReplyRequest.IsQueryFromMobile = "1"

//        if(query_details_fld.text.toString().isNotEmpty())
        seQueryChatReplyRequest.QueryDetails = query_details_fld.text.toString()

        seQueryChatReplyRequest.QueryReplyComments = query_details_fld.text.toString()
        seQueryChatReplyRequest.QueryStatus = statusSpinner.toString()
        seQueryChatReplyRequest.QuerySummary = objQueryCenterAPIInfo.QuerySummary
        seQueryChatReplyRequest.ReplyType = replyType
        seQueryChatReplyRequest.SourceType = "1"

        viewModel.setPostReplyRequest(seQueryChatReplyRequest)

    }

    override fun onClickChatImage(Url: String?) {
        ChatImageOpen.visibility = View.VISIBLE
//        chat_list_ll.visibility = View.GONE
//        mChatImageOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        Glide.with(this)
            .load(Url)
            .placeholder(R.drawable.ic_default_img)
            .error(R.drawable.ic_error)
            .into(chatImges)
    }

}