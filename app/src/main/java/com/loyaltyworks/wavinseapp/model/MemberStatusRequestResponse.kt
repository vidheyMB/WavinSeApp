package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MemberStatusRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val CustomerOrderStatus: Int? = null,
    val ObjCustomer: ObjCustomerr? = null,
    val ObjCustomerDetails: ObjCustomerDetai? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerr(
    val LoyaltyId: String? = null
)

@JsonClass(generateAdapter = true)
class ObjCustomerDetai(
)



@JsonClass(generateAdapter = true)
data class MemberStatusResponse(
    val ReturnMessage: String? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)