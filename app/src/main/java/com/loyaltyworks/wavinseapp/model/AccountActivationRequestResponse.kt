package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

/*Retailer code and mobile number existency check Respose*/
@JsonClass(generateAdapter = true)
data class EmailCheckResponse(
    val CheckUserNameExistsResult: CheckUserNameExistsResult
)

@JsonClass(generateAdapter = true)
data class CheckUserNameExistsResult(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)

/*CheckCustomerExistencyVerificationRequest*/
@JsonClass(generateAdapter = true)
data class CheckCustomerExistancyAndVerificationRequest(
    val ActionType: String,
    val Location: Location
)

@JsonClass(generateAdapter = true)
data class Location(
    val UserName: String
)

/*CheckCustomerExistancyAndVerification Response*/
@JsonClass(generateAdapter = true)
data class CheckCustomerExistancyAndVerificationResponse(
    val CheckCustomerExistancyAndVerificationResult: Int
)


/*SaveAndGetOTPDetails Request*/
@JsonClass(generateAdapter = true)
data class SaveAndGetOTPDetailsRequest(
    val MerchantUserName: String,
    val MobileNo: String,
    val OTPType: String,
    val UserId: Int,
    val UserName: String
)

/*SaveAndGetOTPDetails Response*/
@JsonClass(generateAdapter = true)
data class SaveAndGetOTPDetailsResponse(
    val AdminList: Any?=null,
    val MerchantEmailSMSDetails: Any?=null,
    val MerchantEmailSMSParameterDetails: Any?=null,
    val ReturnMessage: String?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null
)


/*SaveCustomerRegistrationDetailsMobileApp Request*/
@JsonClass(generateAdapter = true)
data class SaveCustomerRegistrationDetailsMobileAppRequest(
    val ActionType: Int,
    val ObjCustomer: ObjCustomer
)

@JsonClass(generateAdapter = true)
data class ObjCustomer(
    val CustomerMobile: String?=null,
    val LoyaltyId: String?=null
)

@JsonClass(generateAdapter = true)
data class SaveCustomerRegistertaionDetailResponse(
    val ReturnMessage: String?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null
)

/*SendLoginCredentialsToCustomer Request*/
@JsonClass(generateAdapter = true)
data class SendLoginCredentialsToCustomerRequest(
    val CustomerName: String?=null,
    val MobileNumber: String?=null,
    val UserName: String?=null
)

/*SendLoginCredentialsToCustomer Response*/
@JsonClass(generateAdapter = true)
data class SendLoginCredentialsToCustomerResponse(
    val SendLoginCredentialsToCustResult: Boolean?=null
)
