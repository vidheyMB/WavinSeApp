package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable


/*My Profile Request*/
@JsonClass(generateAdapter = true)
data class MyProfileRequest(
    var ActionType: String? = null,
    var CustomerId: String? = null,
)


/*My Profile Response*/
@JsonClass(generateAdapter = true)
data class MyProfileResponse(
    var GetCustomerDetailsMobileAppResult: GetCustomerDetailsMobileAppResults? = null,
)

@JsonClass(generateAdapter = true)
data class GetCustomerDetailsMobileAppResults(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var lstCustomerIdentityInfo: List<LstCustomerIdentityInfos>? = null,
    var lstCustomerJson: List<LstCustomerJsons>? = null,
    var lstCustomerOfficalInfoJson: List<LstCustomerOfficalInfoJsons>? = null,
    var lstVehicleJson: List<Any>? = null,
) : Serializable


@JsonClass(generateAdapter = true)
data class LstCustomerIdentityInfos(
    var IdentityDocument: String? = null,
    var IdentityID: Int? = null,
    var IdentityNo: String? = null,
    var IdentityType: String? = null,
    var IdentityTypeID: Int? = null,
    var IsNewIdentity: Boolean? = null
) : Serializable


@JsonClass(generateAdapter = true)
data class LstCustomerOfficalInfoJsons(
    var CityId: Int? = null,
    var CountryId: Int? = null,
    var IndustryID: Int? = null,
    var TargetValue: Int? = null,
    var JobTypeID: Int? = null,
    var TargetCreditPeriod: Int? = null,
    var StateId: Int? = null,
    var FirmTypeID: Int? = null,
    var DesignationIdOfficial: Int? = null,
    var DepartmentIdOfficial: Int? = null,
    var CityName: String? = null,
    var CompanyName: String? = null,
    var CountryName: String? = null,
    var Designation: String? = null,
    var FirmAddress: String? = null,
    var FirmMobile: String? = null,
    var FirmSize: String? = null,
    var FirmTypeName: String? = null,
    var GSTNumber: String? = null,
    var IncorporationDate: String? = null,
    var JobTypeName: String? = null,
    var OfficalEmail: String? = null,
    var OwnerName: String? = null,
    var PhoneOffice: String? = null,
    var PhoneResidence: String? = null,
    var SAPCode: String? = null,
    var StateName: String? = null,
    var StdCode: String? = null,
    var StoreName: String? = null,
    var VenderCode: String? = null,
    var Zip: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class LstCustomerJson(
    var AccountComStatus: Int? = null,
    var AccountNumber: Any? = null,
    var AccountStatus: String? = null,
    var AcountHolderName: Any? = null,
    var Address1: String? = null,
    var Address2: String? = null,
    var AddressId: Int? = null,
    var AgeGroupId: Int? = null,
    var Anniversary: String? = null,
    var BankAddress: Any? = null,
    var BankBranch: Any? = null,
    var BankCountryId: Int? = null,
    var BankCountryName: Any? = null,
    var BankName: Any? = null,
    var BicSwiftCode: Any? = null,
    var BsbAbaRoutingNumber: Any? = null,
    var ChassisNumber: String? = null,
    var CityId: Int? = null,
    var CityName: String? = null,
    var ContractFileName: Any? = null,
    var ContractId: Int? = null,
    var ContractName: Any? = null,
    var CountryId: Int? = null,
    var CountryName: String? = null,
    var Currency: Any? = null,
    var CurrencyId: Int? = null,
    var CustFamilyId: Int? = null,
    var CustomerCategoryId: Int? = null,
    var CustomerDetailId: Int? = null,
    var CustomerId: Int? = null,
    var CustomerRelationshipId: Int? = null,
    var CustomerRemarks: Any? = null,
    var CustomerType: String? = null,
    var CustomerTypeID: Int? = null,
    var Customer_Grade_ID: Int? = null,
    var District: String? = null,
    var DistrictId: Int? = null,
    var DistrictName: String? = null,
    var Email: String? = null,
    var EmailStatus: Int? = null,
    var EnrollmentReferenceNumber: String? = null,
    var ExecutiveName: Any? = null,
    var FamilyMemberBirthday: Any? = null,
    var FamilyMemberName: Any? = null,
    var FirstName: String? = null,
    var Gender: Any? = null,
    var IFSCCode: Any? = null,
    var IbanNumber: Any? = null,
    var IdentificationNo: String? = null,
    var IdentificationOthers: Any? = null,
    var IncomeRangeId: Int? = null,
    var InsuranceRenewalAmount: Int? = null,
    var IsActive: Int? = null,
    var IsBlackListed: Boolean? = null,
    var IsGradeVerified: Boolean? = null,
    var IsSmartphoneUser: Boolean? = null,
    var IsVerified: Int? = null,
    var IsWhatsappUser: Boolean? = null,
    var JCreatedDate: String? = null,
    var JDOB: String? = null,
    var JD_InvoiceNo: Any? = null,
    var JEnrollmentReferenceDate: Any? = null,
    var JInsuranceExpDate: Any? = null,
    var JJD_InvoiceDate: Any? = null,
    var JPolicyDate: Any? = null,
    var LIdentificationType: Int? = null,
    var LaborAmount: Int? = null,
    var Landmark: String? = null,
    var LanguageId: Int? = null,
    var LanguageName: Any? = null,
    var LastName: String? = null,
    var Locality: String? = null,
    var LocationCode: String? = null,
    var LocationId: Int? = null,
    var LocationName: String? = null,
    var LoyaltyId: String? = null,
    var MaritalStatus: Any? = null,
    var MerchantId: Int? = null,
    var Mobile: String? = null,
    var MobileNumberLimitation: Int? = null,
    var MobilePrefix: String? = null,
    var Mobile_Two: String? = null,
    var ModelNumber: String? = null,
    var NativeCountryId: Int? = null,
    var NativeStateId: Int? = null,
    var NativeStateName: String? = null,
    var Nominee: Any? = null,
    var NomineeDOB: Any? = null,
    var PolicyNumber: String? = null,
    var ProfessionId: Int? = null,
    var ProfilePicture: Any? = null,
    var ReferedBy: Any? = null,
    var RegStatus: Int? = null,
    var RegType: String? = null,
    var Relationship: Any? = null,
    var ReligionID: Int? = null,
    var Remarks: Any? = null,
    var StateId: Int? = null,
    var StateName: String? = null,
    var TalukId: Int? = null,
    var TalukName: Any? = null,
    var TehsilBlockMandal: String? = null,
    var Title: String? = null,
    var UserId: Int? = null,
    var VehicleBrand: Any? = null,
    var VehicleID: Int? = null,
    var VehicleNumber: String? = null,
    var Village: String? = null,
    var WalletNumber: Any? = null,
    var WhatsAppNumber: Any? = null,
    var Zip: String? = null,
    var bankAccountVerifiedStatus: Boolean? = null,
) : Serializable


/*City Listing Request */
@JsonClass(generateAdapter = true)
data class GetCityDetailsRequest(
    var ActionType: String? = null,
    var IsActive: String? = null,
    var SortColumn: String? = null,
    var SortOrder: String? = null,
    var StateId: String? = null,

    )

@JsonClass(generateAdapter = true)
data class GetCityDetailsResponse(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var CityList: List<CityList>? = null,

    )

@JsonClass(generateAdapter = true)
data class CityList(
    var Row: Int? = null,
    var CountryCode: Any? = null,
    var CountryId: Int? = null,
    var CountryName: Any? = null,
    var CountryType: Any? = null,
    var IsActive: Boolean? = null,
    var MobilePrefix: Any? = null,
    var StateCode: Any? = null,
    var StateId: Int? = null,
    var StateName: String? = null,
    var CityCode: String? = null,
    var CityId: Int? = null,
    var CityName: String? = null
)

/*State Request*/
@JsonClass(generateAdapter = true)
data class GetStateRequest(
    var ActionType: String? = null,
    var IsActive: String? = null,
    var SortColumn: String? = null,
    var SortOrder: String? = null,
    var StartIndex: String? = null,
    var CountryID: Int? = null,

    )

/*State Response */
@JsonClass(generateAdapter = true)
data class GetStateResponse(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var StateList: List<StateList>? = null,
)

@JsonClass(generateAdapter = true)
data class StateList(
    var Row: Int? = null,
    var CountryCode: Any? = null,
    var CountryId: Int? = null,
    var CountryName: String? = null,
    var CountryType: Any? = null,
    var IsActive: Boolean? = null,
    var MobilePrefix: Any? = null,
    var StateCode: String? = null,
    var StateId: Int? = null,
    var StateName: String? = null,
)