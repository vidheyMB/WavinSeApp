package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query

import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.EnrollmentActivity
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.Keyboard
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.ui.enrollment.retailer_query.adapter.QueryChatAdapter
import com.loyaltyworks.wavinseapp.model.ObjCustomerAllQueryList
import com.loyaltyworks.wavinseapp.model.PostChatStatusRequest
import com.loyaltyworks.wavinseapp.model.QueryChatElementRequest
import com.vmb.fileSelect.FileSelector
import com.vmb.fileSelect.FileSelectorCallBack
import com.vmb.fileSelect.FileSelectorData
import com.vmb.fileSelect.FileType
import kotlinx.android.synthetic.main.query_chat_fragment.*
import kotlinx.android.synthetic.main.query_chat_fragment.view.*

class QueryChatFragment : Fragment() , View.OnClickListener, QueryChatAdapter.ChatImageDisplay {

    private var fileExtenstion = ""
    private var mProfileImagePath = ""
    var ticketId: Int = -1
    var customerTicketId: Int = -1
    var actionID: Int = 0
    var actionType: Int = 0
    var QueryStatus: String? = null
    var helpTopic: String? = null
    var helpTopicID: String? = null

    private val PICKFILE_REQUEST_CODE: Int = 24
    private var outputFileUri: Uri? = null


    private lateinit var viewModel: QueryChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(QueryChatViewModel::class.java)
        val root = inflater.inflate(R.layout.query_chat_fragment, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            val dataholder: ObjCustomerAllQueryList? =
                arguments?.getSerializable("SUPPORT_DATA") as ObjCustomerAllQueryList?
            actionID = arguments?.getInt("ActionId")!!

            if (dataholder != null) {
//                ticketId = dataholder.TicketStatus?.toInt()!!
                customerTicketId = dataholder.CustomerTicketID!!
                QueryStatus = dataholder.TicketStatus!!
                helpTopic = dataholder.HelpTopic!!
                helpTopicID = dataholder.HelpTopicID.toString()!!
                root.query_summary.text = "Ticket Details " + " : " + dataholder.QuerySummary!!

            }
        }


        root.imageAdd.setOnClickListener(this)
        root.send_query_btn.setOnClickListener(this)



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (QueryStatus.equals("Closed", true) || actionID == 1) {
            edittext_block_linear.visibility = View.GONE

            if (actionID == 0)
                chatlist_closet_text.visibility = View.VISIBLE


        } else {
            edittext_block_linear.visibility = View.VISIBLE
            chatlist_closet_text.visibility = View.GONE
        }


        closeImage.setOnClickListener {
            if (BlockMultipleClick.click()) return@setOnClickListener
            ChatImageOpen.visibility = View.GONE
            chat_list_ll.visibility = View.VISIBLE
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        loadChats()
        observers()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observers() {
        viewModel.queryChatLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.objQueryResponseJsonList.isNullOrEmpty()) {
                query_chat_recycler.adapter = QueryChatAdapter(it, this)
                imageAdd.isEnabled = true
                send_query_btn.isEnabled = true
                query_details_fld.isEnabled = true
                query_details_fld.text.clear()
                mProfileImagePath = ""
                query_chat_recycler.scrollToPosition(it.objQueryResponseJsonList!!.size - 1)

            }

//            LoaderDialogue.dismissDialog()
        })

        viewModel.postChatStatusResponseLiveData.observe(
            viewLifecycleOwner,
            Observer {
//                LoaderDialogue.dismissDialog()
                mProfileImagePath = ""
                if (it != null && !it.ReturnMessage.isNullOrEmpty() && it.ReturnMessage!!.split("~")[0] == "1") {
                    loadChats()
                } else {
                    (activity as EnrollmentActivity).snackBar(
                        "Something went wrong try later",
                        R.color.red
                    )
                }
            })
    }

    override fun onClick(v: View) {

        if (BlockMultipleClick.click()) return

        when (v.id) {
            R.id.imageAdd ->
                // Browse Image or File
                FileSelector.requiredFileTypes(FileType.ALL)
                    .open(requireActivity(), object : FileSelectorCallBack {
                        override fun onResponse(fileSelectorData: FileSelectorData) {
                            mProfileImagePath = fileSelectorData.responseInBase64!!
                            fileExtenstion = fileSelectorData.extension!!
                            // post the image
                            getPostReplyTextImage()
                        }
                    })

            R.id.send_query_btn -> {

                // hide keyboard
                Keyboard.hideKeyboard(requireContext(), v)
                getPostReplyTextImage()
            }
        }
    }

    private fun getPostReplyTextImage() {
        if (!TextUtils.isEmpty(
                query_details_fld.text.toString().trim()
            ) || !mProfileImagePath.isNullOrEmpty()
        ) {
//            LoaderDialogue.showDialog(requireContext())

            when (QueryStatus) {
                "Resolved",
                "Reopen" -> ticketId = 2
                "Resolved-Follow up" -> ticketId = 5
                "Closed" -> ticketId = 4
                "Pending" -> ticketId = 1
            }

            val postChatStatusRequest = PostChatStatusRequest()

            postChatStatusRequest.ActionType = "4"

            if (!mProfileImagePath.isNullOrEmpty()) {
                postChatStatusRequest.ImageUrl = mProfileImagePath
                postChatStatusRequest.FileType = fileExtenstion
                postChatStatusRequest.IsQueryFromMobile = "true"
            } else {
                postChatStatusRequest.IsQueryFromMobile = "false"
            }
            postChatStatusRequest.ActorId =
                PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString()

            if (!TextUtils.isEmpty(query_details_fld.text))
                postChatStatusRequest.QueryDetails = query_details_fld.text.toString()

            postChatStatusRequest.HelpTopicID = helpTopicID
            postChatStatusRequest.CustomerTicketID = customerTicketId.toString()
            postChatStatusRequest.QueryStatus = ticketId.toString()
            postChatStatusRequest.HelpTopic = helpTopic

            viewModel.getPostReply(postChatStatusRequest, requireContext())

        } else {
            query_details_fld.text.clear()
            (activity as EnrollmentActivity).snackBar(
                "Enter query details",
                R.color.red
            )
        }
    }


    private fun loadChats() {
//        LoaderDialogue.showDialog(requireContext())
        viewModel.getQueryChat(
            QueryChatElementRequest(
                "171", PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(), customerTicketId.toString()
            )
        )

    }

    override fun onClickChatImage(Url: String?) {
        ChatImageOpen.visibility = View.VISIBLE
        chat_list_ll.visibility = View.GONE
//        mChatImageOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        Glide.with(this)
            .load(Url)
            .placeholder(R.drawable.ic_default_img)
            .error(R.drawable.ic_error)
            .into(chatImges)
    }


}