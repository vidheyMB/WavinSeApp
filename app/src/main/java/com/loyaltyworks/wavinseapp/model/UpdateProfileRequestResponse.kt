package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

/*Update Profile Request*/
@JsonClass(generateAdapter = true)
data class ObjCustomerDetail(
    var Gender: String? = null,
    var LoyaltyId: String? = null,
    var LanguageID: String? = null,
    var CustomerDetailId: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerOfficalInfos(
    var CustomerId: String? = null,
    var CompanyName: String? = null,
    var PhoneOffice: String? = null,
    var StoreName: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateProfileRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var IsMobileRequest: String? = null,
    var ObjCustomer: ObjCustomers? = null,
    var ObjCustomerDetails: ObjCustomerDetail? = null,
    var ObjCustomerOfficalInfo: ObjCustomerOfficalInfos? = null
)

/*
@JsonClass(generateAdapter = true)
data class ObjCustomers(
    var Address1: String? = null,
    var AddressId: String? = null,
    var DisplayImage: String? = null,
    var CityId: String? = null,
    var CountryId: String? = null,
    var DistrictId: String? = null,
    var StateId: String? = null,
    var CustomerId: String? = null,
    var JDOB: String? = null,
    var CustomerEmail: String? = null,
    var CustomerMobile: String? = null,
    var FirstName: String? = null,
    var NativeCountryId: String? = null,
    var CustomerZip: String? = null,
    var LoyaltyId: String? = null,
    var NativeStateId: String? = null,
    var WalletNumber: String? = null,
    var AccountNumber: String? = null,
    var AcountHolderName: String? = null,
    var BankName: String? = null,
    var BankBranch: String? = null,
    var IFSCCode: String? = null
)
*/


/*Update Profile Response*/
@JsonClass(generateAdapter = true)
data class UpdateProfileResponse (
    var ReturnMessage: String? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null
)


/*Update Profile Image Request*/
@JsonClass(generateAdapter = true)
data class UpdateProfileImageRequest(
    var ActorId: String? = null,
    var ObjCustomer: ObjCustomers? = null
)

/*Update Profile Image Response*/
@JsonClass(generateAdapter = true)
data class UpdateProfileImageResponse(
    var ReturnMessage: String? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null
)
