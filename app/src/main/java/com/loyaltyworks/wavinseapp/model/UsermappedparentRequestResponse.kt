package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UserMappedParentRequest(
    var ActionType: String? = null,
    var JDateFrom: String? = null,
    var JDateTo: String? = null,
    var UserID: String? = null
)

@JsonClass(generateAdapter = true)
data class UserMappedParentResponse(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lstCustParentChildMapping: List<LstCustParentChildMapping>? = null
)

@JsonClass(generateAdapter = true)
data class LstCustParentChildMapping(
    val ChildCustomerUserId: Int? = null,
    val ChildFirstName: Any? = null,
    val ChildLoyaltyId: Any? = null,
    val ChildMobileNumber: Any? = null,
    val CustomerGrade: Any? = null,
    val CustomerImage: Any? = null,
    val EnrollmentDate: Any? = null,
    val FirstName: String? = null,
    val IsActive: Int? = null,
    val LoyaltyID: String? = null,
    val MasterCustomerUser: Any? = null,
    val MasterCustomerUserId: Int? = null,
    val Mobile: String? = null,
    val ParentFirstName: Any? = null,
    val ParentLoyaltyId: Any? = null,
    val ParentMobileNumber: Any? = null,
    val ParentUserId: Int? = null,
    val Password: Any? = null,
    val SeFirstName: Any? = null,
    val SeLoyaltyId: Any? = null,
    val SeUserId: Int? = null,
    val TalukName: Any? = null,
    val TotalEnrollCount: Int? = null,
    val TotalPointsBalance: Int? = null,
    val TotalPointsEarned: Any? = null,
    val TotalPointsRedeemed: Any? = null,
    val TotalRedeemedCount: Int? = null,
    val TotalTransactionCount: Int? = null,
    val UserID: Int? = null
) : Serializable