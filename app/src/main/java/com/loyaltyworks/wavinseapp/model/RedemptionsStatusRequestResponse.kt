package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedemptionsStatusRequest(
    var ActionType: String?=null,
    var ActorId: String?=null,
//    val NoOfRows: String?=null,
    var ObjCatalogueDetails: ObjCatalogueDetailss?=null,
    var StartIndex: String?=null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetailss(
    var JFromDate: String?=null,
    var JToDate: String?=null,
    var MerchantId: String?=null,
    var RedemptionTypeId: String?=null,
    var SelectedStatus: String?=null
)

@JsonClass(generateAdapter = true)
data class RedemptionsStatusResponse(
    val CatalogueImageGallery: Any?=null,
    val LocationCites: Any?=null,
    val ObjCatalogueCategoryList: Any?=null,
    val ObjCatalogueFixedPoints: Any?=null,
    val ObjCatalogueList: Any?=null,
    val ObjCatalogueRedemReqList: List<ObjCatalogueRedemReq>?=null,
    val ObjCustShippingAddressDetails: Any?=null,
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null,
    val lstCatalogueProductAvailableCity: Any?=null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueRedemReq(
    val ASM: String?=null,
    val ActionType: Int?=null,
    val ActorId: Int?=null,
    val ActorRole: Any?=null,
    val Address1: String?=null,
    val Address2: Any?=null,
    val AddressId: Int?=null,
    val AddressType: String?=null,
    val Balance: Any?=null,
    val Barcode: Any?=null,
    val CashPerUnit: Int?=null,
    val CashValue: Int?=null,
    val CatalogueId: Int?=null,
    val CatalogueType: String?=null,
    val CategoryName: Any?=null,
    val CityId: Int?=null,
    val CityName: String?=null,
    val CountryId: Int?=null,
    val CountryName: String?=null,
    val CreatedBy: String?=null,
    val CustMobile: String?=null,
    val DeliveryType: Int?=null,
    val Email: String?=null,
    val ExpiryDate: Any?=null,
    val FullName: String?=null,
    val IsActive: Boolean?=null,
    val JRedemptionDate: String?=null,
    val Landmark: Any?=null,
    val LocationName: String?=null,
    val LoyaltyId: String?=null,
    val MerchantEmail: Any?=null,
    val MerchantName: String?=null,
    val Mobile: String?=null,
    val Name: Any?=null,
    val PDFLink: Any?=null,
    val PartialPaymentCash: Int?=null,
    val PendingVoucherBalance: Double?=null,
    val PointsPerUnit: Int?=null,
    val PointsRequired: Int?=null,
    val ProcessedBy: String?=null,
    val ProductCode: String?=null,
    val ProductDesc: String?=null,
    val ProductImage: String?=null,
    val ProductName: String?=null,
    val Quantity: Int?=null,
    val RedeemedPoints: Int?=null,
    val RedemptionDate: String?=null,
    val RedemptionId: Int?=null,
    val RedemptionPoints: Int?=null,
    val RedemptionRefno: String?=null,
    val RedemptionStatus: Int?=null,
    val RedemptionType: Int?=null,
    val ReferrenceCustName: Any?=null,
    val SE: String?=null,
    val SapCode: Any?=null,
    val Sku: Any?=null,
    val StateId: Int?=null,
    val StateName: String?=null,
    val Status: Int?=null,
    val TermsCondition: String?=null,
    val TotRowCount: Int?=null,
    val TransferMode: String?=null,
    val VendorCode: Any?=null,
    val VendorId: Int?=null,
    val VendorName: String?=null,
    val WalletNumber: Any?=null,
    val Zip: String?=null,
    val beneficiaryAccount: Any?=null,
    val beneficiaryIFSC: Any?=null,
    val beneficiaryName: Any?=null
)