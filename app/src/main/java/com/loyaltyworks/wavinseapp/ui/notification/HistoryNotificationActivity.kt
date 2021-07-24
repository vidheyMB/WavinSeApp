package com.loyaltyworks.wavinseapp.ui.notification

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.BaseActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.SwipeToDeleteCallback
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.HistoryNotificationDetailsRequest
import com.loyaltyworks.wavinseapp.model.HistoryNotificationRequest
import com.loyaltyworks.wavinseapp.model.LstPushHistory
import com.loyaltyworks.wavinseapp.ui.notification.adapter.HistoryNotificationAdapter
import kotlinx.android.synthetic.main.activity_history_notification.*


class HistoryNotificationActivity : BaseActivity() {
    private lateinit var viewModel: HistoryNotificationViewModel
    private lateinit var historyNotificationAdapter: HistoryNotificationAdapter

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun callInitialServices() {

//        LoadingDialogue.showDialog(this)


    }

    private fun setReadMoreHistoryNotificationObserver() {
        viewModel.historyNotificationtDetailByIDLiveData.observe(
            this,
            androidx.lifecycle.Observer { })

    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item: LstPushHistory = historyNotificationAdapter.getData()?.get(position)!!
                historyNotificationAdapter.removeItem(position)
                viewModel.getDeleteHistoryNotificationResponse(
                    HistoryNotificationDetailsRequest(
                        ActionType = "2",
                        ActorId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserId.toString(),
                        LoyaltyId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserName,
                        PushHistoryIds = item.PushHistoryId.toString()
                    )
                )
            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(history_rv)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun callObservers() {

        viewModel.historyNotificationtLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && !it.lstPushHistoryJson.isNullOrEmpty()) {
                historyNotificationAdapter = HistoryNotificationAdapter(
                    it, object : HistoryNotificationAdapter.ItemClicked {
                        override fun itemclicks(notificationHistory: LstPushHistory?) {}
                    })

                history_rv.adapter = historyNotificationAdapter
                no_history_notification_tv.visibility = View.GONE
                history_rv.visibility = View.VISIBLE
                LoadingDialogue.dismissDialog()
            } else {
                no_history_notification_tv.visibility = View.VISIBLE
                history_rv.visibility = View.GONE
                LoadingDialogue.dismissDialog()
            }
        })

        viewModel.historyNotificationtDetailByIDLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && !it.lstPushHistoryJson.isNullOrEmpty()) {
                setReadMoreHistoryNotificationObserver()
            }
        })

        viewModel.historyNotificationtDeleteByIDLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null && it.ReturnValue!! > 0) {
                snackBar(
                    "Notification was removed from the list",
                    R.color.green
                )
            } else {
                snackBar(
                    "Notification failed to remove from the list",
                    R.color.red
                )
            }
        })

        enableSwipeToDeleteAndUndo()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HistoryNotificationViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_notification)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set context
        context = this

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

//        historyNotificationAdapter = HistoryNotificationAdapter(null, this)
//        history_rv.adapter = historyNotificationAdapter
        no_history_notification_tv.visibility = View.GONE
        history_rv.visibility = View.VISIBLE

        viewModel.getNotificationHistoryResponse(
            HistoryNotificationRequest(
                ActionType = "0",
                ActorId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserId.toString(),
                LoyaltyId = PreferenceHelper.getLoginDetails(applicationContext)?.UserList!![0].UserName
            )
        )


    }

}