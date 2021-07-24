package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class GetSelectedRetailerDetailByCustomerIDRequest(
    val ActionType: Int,
    val CustomerId: String
)

@JsonClass(generateAdapter = true)
data class GetSelectedRetailerDetailByIDResponse(
    val GetCustomerDetailsMobileAppResult: GetCustomerDetailsMobileAppResult
)

@JsonClass(generateAdapter = true)
data class GetCustomerDetailsMobileAppResult(
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null,
    val lstCustomerIdentityInfo: List<LstCustomerIdentityInfo>?=null,
    val lstCustomerJson: List<LstCustomerJsons>?=null,
    val lstCustomerOfficalInfoJson: List<LstCustomerOfficalInfoJson>?=null,
    val lstVehicleJson: List<Any>?=null
)

@JsonClass(generateAdapter = true)
data class LstCustomerIdentityInfo(
    val IdentityDocument: String?=null,
    val IdentityID: Int?=null,
    val IdentityNo: String?=null,
    val IdentityType: String?=null,
    val IdentityTypeID: Int?=null,
    val IsNewIdentity: Boolean
):Serializable

@JsonClass(generateAdapter = true)
data class LstCustomerJsons(
    val AccountComStatus: Int?=null,
    val AccountNumber: Any?=null,
    val AccountStatus: String?=null,
    val AccountType: Any?=null,
    val AccountTypeID: Int?=null,
    val AcountHolderName: Any?=null,
    var Address1: String?=null,
    val Address2: String?=null,
    val AddressId: Int?=null,
    val AgeGroupId: Int?=null,
    var Anniversary: String?=null,
    val BankAddress: Any?=null,
    val BankBranch: Any?=null,
    val BankCountryId: Int?=null,
    val BankCountryName: Any?=null,
    val BankName: Any?=null,
    val BicSwiftCode: Any?=null,
    val BsbAbaRoutingNumber: Any?=null,
    val ChassisNumber: String?=null,
    var CityId: Int?=null,
    val CityName: String?=null,
    val ContractFileName: Any?=null,
    val ContractId: Int?=null,
    val ContractName: Any?=null,
    val CountryId: Int?=null,
    val CountryName: String?=null,
    val Currency: Any?=null,
    val CurrencyId: Int?=null,
    val CustFamilyId: Int?=null,
    val CustomerCategoryId: Int?=null,
    val CustomerDetailId: Int?=null,
    val CustomerGrade: String?=null,
    val CustomerId: Int?=null,
    val CustomerRelationshipId: Int?=null,
    val CustomerRemarks: Any?=null,
    val CustomerType: String?=null,
    val CustomerTypeID: Int?=null,
    val Customer_Grade_ID: Int?=null,
    val District: String?=null,
    val DistrictId: Int?=null,
    val DistrictName: Any?=null,
    val Email: String?=null,
    val EmailStatus: Int?=null,
    val EnrollmentReferenceNumber: String?=null,
    val ExecutiveName: Any?=null,
    val FamilyMemberBirthday: Any?=null,
    val FamilyMemberName: Any?=null,
    val FirstName: String?=null,
    val Gender: String?=null,
    val IFSCCode: Any?=null,
    val IbanNumber: Any?=null,
    val IdentificationNo: String?=null,
    val IdentificationOthers: Any?=null,
    val IncomeRangeId: Int?=null,
    val InsuranceRenewalAmount: Int?=null,
    val IsActive: Int?=null,
    val IsBlackListed: Boolean?=null,
    val IsGradeVerified: Boolean?=null,
    val IsSmartphoneUser: Boolean?=null,
    val IsVerified: Int?=null,
    val IsWhatsappUser: Boolean?=null,
    val JCreatedDate: String?=null,
    var JDOB: String?=null,
    val JD_InvoiceNo: Any?=null,
    val JEnrollmentReferenceDate: Any?=null,
    val JInsuranceExpDate: Any?=null,
    val JJD_InvoiceDate: Any?=null,
    val JPolicyDate: Any?=null,
    val LIdentificationType: Int?=null,
    val LaborAmount: Int?=null,
    val Landmark: String?=null,
    val LanguageId: Int?=null,
    val LanguageName: String?=null,
    val LastName: String?=null,
    val Locality: String?=null,
    val LocationCode: String?=null,
    val LocationId: Int?=null,
    val LocationName: String?=null,
    val LoyaltyId: String?=null,
    val MaritalStatus: String?=null,
    val MerchantId: Int?=null,
    var Mobile: String?=null,
    val MobileNumberLimitation: Int?=null,
    val MobilePrefix: String?=null,
    val Mobile_Two: String?=null,
    val ModelNumber: String?=null,
    val NativeCountryId: Int?=null,
    val NativeStateId: Int?=null,
    val NativeStateName: String?=null,
    val Nominee: String?=null,
    val NomineeDOB: String?=null,
    val PolicyNumber: String?=null,
    val ProfessionId: Int?=null,
    val ProfilePicture: String?=null,
    val ReferedBy: Any?=null,
    val RegStatus: Int?=null,
    val RegType: String?=null,
    val Relationship: Any?=null,
    val ReligionID: Int?=null,
    val Remarks: Any?=null,
    var StateId: Int?=null,
    var StateName: String?=null,
    val TalukId: Int?=null,
    val TalukName: Any?=null,
    val TehsilBlockMandal: String?=null,
    val Title: String?=null,
    val UserId: Int?=null,
    val VehicleBrand: Any?=null,
    val VehicleID: Int?=null,
    val VehicleNumber: String?=null,
    val Village: String?=null,
    val WalletNumber: Any?=null,
    val WhatsAppNumber: Any?=null,
    var Zip: String?=null,
    val bankAccountVerifiedStatus: Boolean
):Serializable

@JsonClass(generateAdapter = true)
data class LstCustomerOfficalInfoJson(
    val CityId: Int?=null,
    val CityName: String?=null,
    val CompanyName: String?=null,
    val CountryId: Int?=null,
    val CountryName: String?=null,
    val DepartmentIdOfficial: Int?=null,
    val Designation: Any?=null,
    val DesignationIdOfficial: Int?=null,
    val FirmAddress: String?=null,
    val FirmMobile: String?=null,
    val FirmSize: String?=null,
    val FirmTypeID: Int?=null,
    val FirmTypeName: String?=null,
    val GSTNumber: Any?=null,
    val IncorporationDate: Any?=null,
    val IndustryID: Int?=null,
    val JobTypeID: Int?=null,
    val JobTypeName: String?=null,
    val OfficalEmail: String?=null,
    val OwnerName: Any?=null,
    val PhoneOffice: String?=null,
    val PhoneResidence: String?=null,
    val SAPCode: String?=null,
    val StateId: Int?=null,
    val StateName: String?=null,
    val StdCode: String?=null,
    val StoreName: Any?=null,
    val TargetCreditPeriod: Int?=null,
    val TargetValue: Int?=null,
    val VenderCode: Any?=null,
    val Zip: String?=null
):Serializable



/*Update and Register General Request*/
@JsonClass(generateAdapter = true)
data class GeneralRequest(
    val ActionType: String,
    val ActorId: String,
    val IsActive: String,
    val ObjCustomer: ObjCustomers,
    val ObjCustomerDetails: ObjCustomerDetails
)

@JsonClass(generateAdapter = true)
data class ObjCustomers(
    val CustomerCategoryId: String?=null,
    val CustomerEmail: String?=null,
    val CustomerId: String?=null,
    val CustomerMobile: String?=null,
    val CustomerRelationshipId: String?=null,
    val CustomerTypeId: String?=null,
    val Customer_Grade_ID: String?=null,
    val FirstName: String?=null,
    val JNomineeDOB: String?=null,
    val LocationId: String?=null,
    val LoyaltyId: String?=null,
    val LoyaltyIdAutoGen: String?=null,
    val MerchantId: Int?=null,
    val MobilePrefix: String?=null,
    val Mobile_Two: String?=null,
    val Nominee: String?=null,
    val RegistrationSource: String?=null,
    val Title: List<Any>?=null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerDetails(
    val CustomerDetailId: String?=null,
    val IsNewProfilePicture: String?=null,
    val LanguageID: String?=null,
    val ProfilePicture: String?=null
)

/*General Register and Update Response*/
@JsonClass(generateAdapter = true)
data class GeneralResponse(
    val ReturnMessage: String?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null
)

@JsonClass(generateAdapter = true)
data class PersonalSaveUpdateRequest(
    val ActionType: String,
    val ActorId: String,
    val ObjCustomer: ObjCustomerss,
    val ObjCustomerDetails: ObjCustomerDetailss
)

@JsonClass(generateAdapter = true)
data class ObjCustomerss(
    val Address1: String?=null,
    val AddressId: String?=null,
    val CityId: String?=null,
    val CountryId: String?=null,
    val CustomerId: String?=null,
    val CustomerZip: String?=null,
    val DistrictId: String?=null,
    val JDOB: String?=null,
    val LoyaltyId: String?=null,
    val MerchantId: String?=null,
    val NativeStateId: String?=null,
    val StateId: String?=null
)

@JsonClass(generateAdapter = true)
data class ObjCustomerDetailss(
    val CustomerDetailId: String?=null,
    val Gender: String?=null,
    val IsNewProfilePicture: String?=null,
    val IsSmartphoneUser: String?=null,
    val IsWhatsappUser: String?=null,
    val JAnniversary: String?=null
)
