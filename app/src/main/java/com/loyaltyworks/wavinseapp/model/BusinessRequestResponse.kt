package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveUpdateBusinessRequest(
    val ActionType: String,
    val ActorId: String,
    val ObjCustomer: ObjBusinessCustomer,
    val ObjCustomerDetails: ObjBusinessCustomerDetails,
    val ObjCustomerOfficalInfo: ObjBusinesCustomerOfficalInfo
)

@JsonClass(generateAdapter = true)
data class ObjBusinessCustomer(
    val CustomerId: String,
    val LoyaltyId: String,
    val MerchantId: String
)

@JsonClass(generateAdapter = true)
data class ObjBusinessCustomerDetails(
    val CustomerDetailId: String
)

@JsonClass(generateAdapter = true)
data class ObjBusinesCustomerOfficalInfo(
    val Address: String,
    val CityId: String,
    val CompanyName: String,
    val CountryId: String,
    val CustomerZip: String,
    val FirmMobile: String,
    val FirmSize: String,
    val FirmTypeID: String,
    val JobTypeID: String,
    val OfficalEmail: String,
    val PhoneOffice: String,
    val StateId: String,
    val StdCode: String
)

@JsonClass(generateAdapter = true)
data class SaveUpdateBusinessResponse(
    val ReturnMessage: String,
    val ReturnValue: Int,
    val TotalRecords: Int
)