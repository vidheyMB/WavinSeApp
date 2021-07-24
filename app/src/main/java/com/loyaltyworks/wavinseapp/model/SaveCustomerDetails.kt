package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaveCustomerRequest(
    var CustomerType: String? = null,
    var DeviceId: String? = null,
    var EmailId: String? = null,
    var Name: String? = null,
    var SourceDevice: String? = null,
    var UserId: Int? = null,
    var UserIp: String? = null,
    var RegStatus: String? = null,
    var objSaveCustomerDetailsOpenIdRequest: ObjSaveCustomerDetailsOpenIdRequest? = null
)

@JsonClass(generateAdapter = true)
data class ObjSaveCustomerDetailsOpenIdRequest(
    var AbaBsbRoutingNumber: String? = null,
    var AccountStatus: String? = null,
    var Address1: String? = null,
    var Address2: String? = null,
    var BICSWIFTCode: String? = null,
    var BankAccountNumber: String? = null,
    var BankAddress: String? = null,
    var BankCountry: String? = null,
    var BankName: String? = null,
    var BankStatement: String? = null,
    var BeneficiaryName: String? = null,
    var BranchName: String? = null,
    var City: String? = null,
    var CompanyLegalName: String? = null,
    var CountryName: String? = null,
    var Currency: String? = null,
    var IBANNumber: String? = null,
    var MobilePhoneNumber: String? = null,
    var OfficialBankletter: String? = null,
    var PanTan: String? = null,
    var PostalCode: String? = null,
    var TelephoneNumber: String? = null,
    var TermsAndConditionsId: String? = null,
    var IsBankNewImg: Int? = null,
    var IsOtherNewImg: Int? = null,
)

@JsonClass(generateAdapter = true)
data class SaveCustomerResponse(
    var SaveCustomerDetailsFromOpenIdResult: String? = null
)


