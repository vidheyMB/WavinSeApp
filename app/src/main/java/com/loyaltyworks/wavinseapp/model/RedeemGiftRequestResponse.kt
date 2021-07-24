package com.loyaltyworks.wavinseapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable


/*Product Category List*/

@JsonClass(generateAdapter = true)
data class ProductCategoryRequest(
    val ObjCatalogueRetriveRequest: ObjCatalogueRetriveRequest
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueRetriveRequest(
    val ActionType: String?=null,
    val ActorId: String?=null,
    val ObjCatalogueCategoryDetails: ObjCatalogueCategoryDetail?=null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueCategoryDetail(
    val IsActive: String?=null
)

@JsonClass(generateAdapter = true)
data class ProductCategoryResponse(
    val GetCatalogueCategoryDetailsResult: GetCatalogueCategoryDetailsResult
)

@JsonClass(generateAdapter = true)
data class GetCatalogueCategoryDetailsResult(
        val ObjCatalogueCategoryListJson: List<ObjCatalogueCategoryJson>?=null,
        val ReturnMessage: Any?=null,
        val ReturnValue: Int?=null,
        val TotalRecords: Int?=null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueCategoryJson(
    val ActionType: Int?=null,
    val ActorId: Int?=null,
    val ActorRole: Any?=null,
    val CatalogueBrandId: Int?=null,
    val CatalogueBrandName: String?=null,
    val CatogoryId: Int?=null,
    val CatogoryImage: Any?=null,
    val CatogoryName: String?=null,
    val IsActive: Boolean?=null,
    val SubCategoryID: Int?=null,
    val SubCategoryName: Any?=null
)

/*Product Category List*/


/*Voucher and Catalogue Product Request*/
@JsonClass(generateAdapter = true)
data class RedeemGiftRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
//    var NoOfRows: String? = null,
        var ObjCatalogueDetails: ObjCatalogueDetail? = null,
        var StartIndex: Int? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueDetail(
        var CatalogueType: String? = null,
        var CatogoryId: String? = null,
        var CatalogueId: String? = null,
        var MerchantId: String? = null

)

/*Voucher and Product CatalogueResponse*/
@JsonClass(generateAdapter = true)
data class RedeemGiftResponse(
        var ReturnMessage: String? = null,
        var ReturnValue: Int? = null,
        var TotalRecords: Int? = null,
        var CatalogueImageGallery: String? = null,
        var LocationCites: String? = null,
        var ObjCatalogueCategoryList: String? = null,
        var ObjCatalogueFixedPoints: List<ObjCatalogueFixedPoint>? = null,
        var ObjCatalogueList: MutableList<ObjCatalogueList>? = null,
        var ObjCatalogueRedemReqList: String? = null,
        var ObjCustShippingAddressDetails: ObjCustShippingAddressDetail? = null,
        var lstCatalogueProductAvailableCity: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueFixedPoint(
        var FixedPoints: Int? = null,
        var ProductCode: String? = null
):Serializable

@Entity(tableName = "Product_table")
@JsonClass(generateAdapter = true)
data class ObjCatalogueList(
        @PrimaryKey(autoGenerate = true) var id: Int?=null,
        var ActionType: Int? = null,
        var ActorId: Int? = null,
        var ActorRole: String? = null,
        var IsActive: Boolean? = null,
        var BrandTermsAndConditions: String? = null,
        var CatalogueBrandCode: String? = null,
        var CatalogueBrandDesc: String? = null,
        var CatalogueBrandId: Int? = null,
        var CatalogueBrandName: String? = null,
        var CategoryParentID: Int? = null,
        var CatogoryId: Int? = null,
        var CatogoryImage: String? = null,
        @ColumnInfo(name = "CatogoryName") var CatogoryName: String? = null,
        var Color_Code: String? = null,
        var Color_Id: Int? = null,
        var Color_Name: String? = null,
        var FromDate: String? = null,
        var JFromDate: String? = null,
        var JToDate: String? = null,
        @ColumnInfo(name = "MerchantId") var MerchantId: Int? = null,
        @ColumnInfo(name = "MerchantName") var MerchantName: String? = null,
        var ModelId: Int? = null,
        var ModelName: String? = null,
        var Status: Int? = null,
        var SubCategoryID: Int? = null,
        var SubCategoryName: String? = null,
        @ColumnInfo(name = "TermsCondition") var TermsCondition: String? = null,
        var ToDate: String? = null,
        var UserAccess: Int? = null,
        var ActiveStatus: Boolean? = null,
        var ActualRedemptionDate: String? = null,
        var AdditionalRemarks: String? = null,
        var ApproverName: String? = null,
        var AverageEarning: String? = null,
        var AvgExpDate: String? = null,
        var AvgGreaterExpDate: String? = null,
        var AvgLesserExpDate: String? = null,
        var Barcode: String? = null,
        var CashPerUnit: Int? = null,
        var CashValue: Int? = null,
        var CatalogueId: Int? = null,
        var CatalogueType: Int? = null,
        var CatalougeBrandName: String? = null,
        @ColumnInfo(name = "CategoryID")  var CategoryID: Int? = null,
        var CommandName: String? = null,
        var CountryCurrencyCode: String? = null,
        var CountryID: Int? = null,
        var CreatedBy: String? = null,
        var CreatedDate: String? = null,
        var DailyAvgCash: String? = null,
        @ColumnInfo(name = "DeliveryType") var DeliveryType: String? = null,
        var DreamGiftId: Int? = null,
        var ExpectedDelivery: String? = null,
        var ExpiryDate: String? = null,
        var ExpiryOn: Int? = null,
        var GreaterAvgCash: String? = null,
        var HasPartialPayment: Boolean? = null,
        var HasWishListAdded: Boolean? = null,
        var IsApproved: Boolean? = null,
        @ColumnInfo(name = "IsCash") var IsCash: Boolean? = null,
        var IsPlanner: Boolean? = null,
        var IsPopularCount: Int? = null,
        var JRedemptionDate: String? = null,
        var LesserAvgCash: String? = null,
        var LocationId: Int? = null,
        var LoyaltyId: String? = null,
        var MSQA: Int? = null,
        var MemberName: String? = null,
        var MinimumStockQunty: Int? = null,
        var Mobile: String? = null,
        var MultipleRedIds: String? = null,
        @ColumnInfo(name = "Net_Points") var NoOfPointsDebit: Int? = null, // Total number of points need to redeem product
        @ColumnInfo(name = "NoOfQuantity") var NoOfQuantity: Int? = null,
        var PartialPaymentCash: Int? = null,
        var PlannerStatus: String? = null,
        var PointBalance: Int? = null,
        var PointRedem: Int? = null,
        var PointReqToAcheiveProduct: Int? = null,
        var PointsPerUnit: Int? = null,
        @ColumnInfo(name = "PointsRequired") var PointsRequired: Int? = null,
        @ColumnInfo(name = "ProductCode") var ProductCode: String? = null,
        @ColumnInfo(name = "ProductDesc") var ProductDesc: String? = null,
        @ColumnInfo(name = "ProductImage") var ProductImage: String? = null,
        var ProductImageServerPath: String? = null,
        @ColumnInfo(name = "ProductName") var ProductName: String? = null,
        var Product_type: Int? = null,
        var RedeemableAverageEarning: String? = null,
        var RedeemableAverageEarning12: Int? = null,
        var RedeemableAverageEarning6: Int? = null,
        var RedeemableEncashBalance: Int? = null,
        var RedeemablePointBalance: Int? = null,
        var RedemptionDate: String? = null,
        var RedemptionId: Int? = null,
        var RedemptionPlannerId: Int? = null,
        var RedemptionRefno: String? = null,
        var RedemptionStatus: String? = null,
        var RedemptionTypeId: Int? = null,
        var SegmentDetails: String? = null,
        var SelectedStatus: Int? = null,
        @ColumnInfo(name = "TotalCash") var TotalCash: Int? = null,
        var Total_Row: Int? = null,
        @ColumnInfo(name = "VendorId")  var VendorId: Int? = null,
        @ColumnInfo(name = "VendorName") var VendorName: String? = null,
        var max_points: String? = null,
        var min_points: String? = null,
        @Ignore var ObjCatalogueFixedPoints: List<ObjCatalogueFixedPoint>? = null // Added for hold the respective points range for Voucher list.
):Serializable


/*RedeemGiftVoucher Request*/

@JsonClass(generateAdapter = true)
data class RedeemGiftVoucherRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
        var CountryCode: String? = null,
        var CountryID: String? = null,
        var MerchantId: String? = null,
        var ObjCatalogueList: List<ObjCatalogueListss>? = null,
        var ReceiverEmail: String? = null,
        var ReceiverName: String? = null,
        var ReceiverMobile: String? = null,
        var SourceMode: String? = null
)
@JsonClass(generateAdapter = true)
data class ObjCatalogueListss(
        var CatalogueId: Int? = null,
        var CountryCurrencyCode: String? = null,
        var Address1: String? = null,
        var DeliveryType: String? = null,
        var HasPartialPayment: Boolean? = null,
        var NoOfPointsDebit: Int? = null,
        var PointsRequired: Float? = null,
        var ProductCode: String? = null,
        var ProductImage: String? = null,
        var ProductName: String? = null,
        var JRedemptionDate: String? = null,
        var RedemptionId: Int? = null,
        var RedemptionTypeId: Int? = null,
        var NoOfQuantity: Int? = null,
        var Status: Int? = null,
        var VendorId: Int? = null,
        var VendorName: String? = null
)

/*RedeemGiftVoucherResponse*/
@JsonClass(generateAdapter = true)
data class RedeemGiftVoucherResponse(
        var ReturnMessage: String? = null,
        var ReturnValue: Int? = null,
        var TotalRecords: Int? = null
)


/*RedeemGift Catalogue Request */

/*Send Customer Shipping Address Details Request*/
@JsonClass(generateAdapter = true)
data class RedeemGiftCatalogueRequest(
        var ActionType: String? = null,
        var ActorId: String? = null,
        var MemberName: String? = null,
        var ObjCatalogueList: List<ObjCatalogueList>? = null,
        var ObjCustShippingAddressDetails: ObjCustShippingAddressDetail? = null,
        var SourceMode: String? = null
):Serializable

@JsonClass(generateAdapter = true)
data class ObjCustShippingAddressDetail(
        var Address1: String? = null,
        var CityId: String? = null,
        var CityName: String? = null,
        var CountryId: String? = null,
        var StateId: String? = null,
        var StateName: String? = null,
        var Zip: String? = null,
        var Email: String? = null,
        var FullName: String? = null,
        var Mobile: String? = null
):Serializable

/*Response of Catalogue Save Request for Shipping Address*/
@JsonClass(generateAdapter = true)
data class RedeemGiftCatalogueResponse(
         var ReturnMessage: String? = null,
         var ReturnValue: Int? = null,
         var TotalRecords: Int? = null
)


/*Call Alert Mobile App Request After Success of Customer Shipping Address Details Request*/

@JsonClass(generateAdapter = true)
data class SendCatalogueRedemptionAlertMobileAppRequest(
        var MemberName: String? = null,
        var MerchantEmailID: String? = null,
        var MerchantID: String? = null,
        var MerchantMobileNo: String? = null,
        var ObjCatalogueList: List<ObjCatalogueList>? = null,
        var ObjCustShippingAddressDetails: ObjCustShippingAddressDetail? = null,
        var TotalPointsRedeemed: String? = null,
        var UserName: String? = null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogueTypeList(
        var Catalogue: ObjCatalogueList? = null
)

/*Alert Mobile Request Respone */
@JsonClass(generateAdapter = true)
data class SendCatalogueRedemptionAlertMobileAppResponse(
         var ReturnMessage: String? = null,
         var ReturnValue: Int? = null,
         var TotalRecords: Int? = null

        )


/*After Success response of Alert Mobile App Request Call Send SMS For Success Redemption Request*/
@JsonClass(generateAdapter = true)
data class SendSMSForSuccessfulRedemptionMobileAppRequest(
         var CustomerName: String? = null,
         var EmailID: String? = null,
         var LoyaltyID: String? = null,
         var Mobile: String? = null,
         var PointBalance: String? = null,
         var RedeemedPoint: String? = null

        )


/*Success Response Of Send SMS For Success Redemption*/
@JsonClass(generateAdapter = true)
data class SendSMSForSuccessfulRedemptionMobileAppResponse (
     var SendSMSForSuccessfulRedemptionMobileAppResult: Boolean? = null
)


