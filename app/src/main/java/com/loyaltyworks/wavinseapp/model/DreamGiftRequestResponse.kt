package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable


/* Dream Gift Listing */
@JsonClass(generateAdapter = true)
data class DreamGiftRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val DreamGiftId: String? = null,
    val LoyaltyId: String? = null
)

@JsonClass(generateAdapter = true)
data class DreamGiftResponse(
    val lstDreamGift: List<LstDreamGift>? = null
)



/*  Dream Gift Response*//*

@JsonClass(generateAdapter = true)
data class LstDreamGift(
    val ActionType: Int? = null,
    val ActorId: Int? = null,
    val ActorRole: Any? = null,
    val Address: String? = null,
    val AvgEarningPoints: Int? = null,
    val ContractorName: String? = null,
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
    val JCreatedDate: String? = null,
    val JDesiredDate: String? = null,
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

*/


/*  Dream Gift Remove Request Response */
@JsonClass(generateAdapter = true)
data class DreamGiftRemoveRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val DreamGiftId: String? = null,
    val GiftStatusId: String? = null
)

@JsonClass(generateAdapter = true)
data class DreamGiftRemoveResponse(
    val DreamGiftName: Any? = null,
    val MemberName: Any? = null,
    val Mobile: Any? = null,
    val Points: Any? = null,
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)


/*  Add Dream Gift  */

@JsonClass(generateAdapter = true)
data class AddDreamGiftRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val DreamGiftName: String? = null,
    val JDesiredDate: String? = null,
    val LoyaltyID: String? = null,
    val PointsRequired: String? = null
)

@JsonClass(generateAdapter = true)
data class AddDreamGiftResponse(
    val DreamGiftName: Any? = null,
    val MemberName: Any? = null,
    val Mobile: Any? = null,
    val Points: Any? = null,
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)