package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable


@JsonClass(generateAdapter = true)
data class MyDreamGiftRequest(
    var ActionType: Int? = null,
    var ActorId: Int? = null,
    var LoyaltyId: String? = null,
    var Status: Int? = null,
    var DreamGiftId: Int? = null
)


@JsonClass(generateAdapter = true)
data class MyDreamGiftResponse(
    val lstDreamGift: List<LstDreamGift>? = null
)

@JsonClass(generateAdapter = true)
data class LstDreamGift(
    val ActionType: Int? = null,
    val ActorId: Int? = null,
    val ActorRole: Any? = null,
    val Address: String? = null,
    val AvgEarningPoints: Int? = null,
    val ContractorName: String? = null,
    val JCreatedDate: String? = null,
    val JDesiredDate: String? = null,
    val DreamGiftDescription: Any? = null,
    val DreamGiftId: Int? = null,
    val DreamGiftName: String? = null,
    val EarlyExpectedDate: Any? = null,
    val EarlyExpectedPoints: Int? = null,
    val ExpectedDate: Any? = null,
    val GiftImage: Any? = null,
    val GiftStatusId: Int? = null,
    val GiftType: String? = null,
    val IsActive: Boolean? = null,
    val LateExpectedDate: Any? = null,
    val LateExpectedPoints: Int? = null,
    val LoyaltyID: String? = null,
    val Mobile: String? = null,
    val PointsBalance: Int? = null,
    val PointsRequired: Int? = null,
    val Remark: String? = null,
    val Role: String? = null,
    val Status: String? = null,
    val TotRow: Int? = null
) : Serializable


@JsonClass(generateAdapter = true)
data class RemoveDreamGiftRequest(
    var ActionType: Int? = null,
    var ActorId: Int? = null,
    var DreamGiftId: Int? = null,
    var GiftStatusId: Int? = null
)

@JsonClass(generateAdapter = true)
data class RemoveDreamGiftResponse(
    val DreamGiftName: String? = null,
    val MemberName: String? = null,
    val Mobile: String? = null,
    val Points: String? = null,
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)

