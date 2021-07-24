package com.loyaltyworks.wavinseapp.ui.notification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.DashboardActivity
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.splashscreen.SplashScreenActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
class NotificationActivity : AppCompatActivity() {
    /* ELEMENTS NEEDY TO PUSH NOTIFICATIONS */

    /* ELEMENTS NEEDY TO PUSH NOTIFICATIONS */
    var PUSH_TOKEN = "PUSH_TOKEN"
    var SCREENING = "ActionType"
    var IMAGE_URL = "image_url"
    var PUSH_USER_ACTION_ID = "PushUserActionID"
    var ID = "id"
    var TITLE = "title"
    var BIG_TEXT = "big_text"
    var PRODUCT_ID = "PRODUCT_ID"
    var PROMOTION_ID = "PROMOTION_ID"

    // intent for dashboard
    val DashBoardFragment = "DashBoardFragment"
    val DashBoard = "DashBoard"
    val Support = "Support"
    val ChatPage = "ChatPage"
    val ChatID = "ChatID"

//    private var SCREENING = "ActionType"
//    private var IMAGE_URL = "image_url"
//    private var PUSH_USER_ACTION_ID = "PushUserActionID"
//    private var ID = "id"
//    private var TITLE = "title"
//    private var BIG_TEXT = "big_text"

    var OPEN_APP = 1
    var OPEN_LIST = 2
    var OPEN_DETAILS = 3

    var page = 0
    var mUserActionId = 0
    var mPushCatId = 0

    var context: Context? = null

    val PROMOTION_ACTION_TYPE = 101
    val CATALOGUE_ACTION_TYPE = 100
    val Query_TYPE = 103
    val HistoryNotification_TYPE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val iin = intent
        context = this
        if (iin != null) {
            page = iin.getStringExtra(SCREENING)!!.toInt()
            mUserActionId = iin.getStringExtra(PUSH_USER_ACTION_ID)!!.toInt()
            mPushCatId = iin.getStringExtra(ID)!!.toInt()
        }

        if (PreferenceHelper.getBooleanValue(this, BuildConfig.IsLoggedIn)) {
            when (page) {
                Query_TYPE -> openQueryChat()
//                PROMOTION_ACTION_TYPE -> openPromotionModel()
//                CATALOGUE_ACTION_TYPE -> openProductCatalogueModel()
//                HistoryNotification_TYPE -> openHistoryNotification()
                else -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }
        } else {
            startActivity(Intent(this, SplashScreenActivity::class.java))
            finish()
        }
    }


    private fun openQueryChat() {
        when (mUserActionId) {
            OPEN_APP -> {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra(DashBoardFragment, DashBoard)
                startActivity(intent)
                finish()
            }
            OPEN_LIST -> {
                val intent = Intent(this, HistoryNotificationActivity::class.java)
                intent.putExtra(DashBoardFragment, Support)
                startActivity(intent)
                finish()
            }
            OPEN_DETAILS -> {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra(DashBoardFragment, ChatPage)
                intent.putExtra(ChatID, mPushCatId)
                startActivity (intent)
                finish ()
            }
        }
    }
}