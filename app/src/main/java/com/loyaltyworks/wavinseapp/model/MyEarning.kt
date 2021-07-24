package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass


 /*  My Earning Request   */

@JsonClass(generateAdapter = true)
data class MyEarningRequest(
    var ActorId: String? = null,
    var IsActive: String? = null,
    var MerchantId: String? = null,
    var JFromDate: String? = null,
    var JToDate: String? = null
)


@JsonClass(generateAdapter = true)
data class MyEarningResponse(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var PDF: Int? = null,
    var lstRewardTransDetails: Any? = null,
    var lstRewardTransJsonDetails: List<LstrewardTransjsonDetails>? = null
)


@JsonClass(generateAdapter = true)
data class LstrewardTransjsonDetails(
    var Amount: String? = null,
    var BehaviorId: Int? = null,
    var BonusName: String? = null,
    var BrandName: String? = null,
    var CashierName: String? = null,
    var CategoryName: String? = null,
    var CreatedBy: String? = null,
    var CurrencyName: String? = null,
    var ExpairyOnDate: String? = null,
    var InvoiceNo: String? = null,
    var IsNotionalId: String? = null,
    var JTranDate: String? = null,
    var LocationID: String? = null,
    var LocationName: String? = null,
    var LoyaltyId: String? = null,
    var LtyBehaviourId: String? = null,
    var MemberName: String? = null,
    var MerchantID: String? = null,
    var MerchantName: String? = null,
    var PartyName: String? = null,
    var PointBalance: String? = null,
    var PointsGiftedBy: String? = null,
    var ProcessDateTime: String? = null,
    var ProdCode: String? = null,
    var ProdName: String? = null,
    var Quantity: String? = null,
    var Remarks: String? = null,
    var RewardPoints: String? = null,
    var SerialNumber: String? = null,
    var SkuName: String? = null,
    var TotalRows: String? = null,
    var TranDate: String? = null,
    var TransactionType: String? = null,
    var VendorName: String? = null

)
