package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IdentificationSaveUpdateRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var ObjCustomer: ObjCustomersss? = null,
    var ObjCustomerDetails: ObjCustomerDetailsss? = null,
    var lstIdentityInfo: List<LstIdentityInfo>
)

@JsonClass(generateAdapter = true)
data class LstIdentityInfo(
    var IdentityDocument: String? = null,
    var IdentityID: String? = null,
    var IdentityNo: String? = null,
    var IdentityType: String? = null,
    var IsNewIdentity: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomersss(
    var CustomerId: String? = null,
    var LoyaltyId: String? = null,
    var MerchantId: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerDetailsss(
    var CustomerDetailId: String? = null
)


@JsonClass(generateAdapter = true)
data class SaveUpdateIdentificationDetailResponse (
    var ReturnMessage: String? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null
)


/*Identification Number Existency Check Request*/

@JsonClass(generateAdapter = true)
data class IdentificationNumberExistencyCheckRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var Location: Locations? = null
)

@JsonClass(generateAdapter = true)
data class Locations(
    var UserName: String? = null,
    var BillingTypeID: Int? = null,
    var ProductID: Int? = null
)

/*Identification Number Existency Check Response*/

@JsonClass(generateAdapter = true)
data class CheckUserNameExistsResults(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null
)

@JsonClass(generateAdapter = true)
data class IdentificationNumberExistencyCheckResponse(
    var CheckUserNameExistsResult: CheckUserNameExistsResults? = null
)