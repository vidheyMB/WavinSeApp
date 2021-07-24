package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

/*My Redemption Request */

@JsonClass(generateAdapter = true)
data class MyRedemptionRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
//    var CardTypeId: String? = null,
    var JFromDate: String? = null,
    var JToDate: String? = null,
//    var StatusId: String? = null
    var RedemptionTypeId:Int? = 0
)


/*MyRedemption Response*/
@JsonClass(generateAdapter = true)
data class MyRedemptionResponse(
    var ReturnMessage: String? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var LstGiftCardIssueDetailsJson: List<LstGiftCardIssueDetailsJson>? = null
)

@JsonClass(generateAdapter = true)
data class LstGiftCardIssueDetailsJson(

    var Amount: Int? = null,
    var BehaviourName: String? = null,
    var ProductName: String? = null,
    var CardIssueDate: String? = null,
    var CardNumber: String? = null,
    var CardUsedOrNot: String? = null,
    var CreatedDate: String? = null,
    var CurrencyName: String? = null,
    var CustomerType: String? = null,
    var FbAmount: Int? = null,
    var FbPoints: Int? = null,
    var FileStatus: Boolean? = null,
    var GiftCardIssueId: Int? = null,
    var GiftCardTypeId: Int? = null,
    var InvoiceNo: String? = null,
    var IsUsingLoyaltyPoints: Boolean,
    var Is_Active: Boolean? = null,
    var IssuedEncashBalance: Boolean? = null,
    var JTransactiondate: String? = null,
    var LocationID: Int? = null,
    var LocationName: String? = null,
    var LoyaltyId: String? = null,
    var MerchantName: String? = null,
    var MobileNo: String? = null,
    var NGO_Id: Int? = null,
    var Name: String? = null,
    var Password: String? = null,
    var PlanePassword: String? = null,
    var PointsAwarded: Int? = null,
    var PointsRedeemed: Int? = null,
    var ProgramStartDate: String? = null,
    var Reference: String? = null,
    var Remarks: String? = null,
    var RoomAmount: Int? = null,
    var RoomPoints: Int? = null,
    var RowNo: Int? = null,
    var Status: String? = null,
    var TopUpAmount: Int? = null,
    var TotalPoints: Int? = null,
    var TotalRows: Int? = null,
    var TransactionBalance: Int? = null,
    var TransactionMode: String? = null,
    var TransactionNo: String? = null,
    var TransactionType: String? = null,
    var Transactiondate: String? = null,
    var Validity: String? = null
)

