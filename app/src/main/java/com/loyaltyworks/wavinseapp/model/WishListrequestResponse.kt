package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

/*WishList Listing Request*/
@JsonClass(generateAdapter = true)
data class WishListRequest(
    val ActionType: Int,
    val ActorId: String
)

/*WishList Listing Response*/
@JsonClass(generateAdapter = true)
data class WishListResponse(
    val ObjCatalogueList: List<ObjCatalogue>?=null,
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null
)

@JsonClass(generateAdapter = true)
data class ObjCatalogue(
    val ActionType: Int?=null,
    val ActiveStatus: Boolean?=null,
    val ActorId: Int?=null,
    val ActorRole: Any?=null,
    val ActualRedemptionDate: String?=null,
    val AdditionalRemarks: Any?=null,
    val ApproverName: Any?=null,
    val AverageEarning: String?=null,
    val AvgExpDate: String?=null,
    val AvgGreaterExpDate: String?=null,
    val AvgLesserExpDate: String?=null,
    val Barcode: Any?=null,
    val BrandTermsAndConditions: Any?=null,
    val CashPerUnit: Int?=null,
    val CashValue: Int?=null,
    val CatalogueBrandCode: Any?=null,
    val CatalogueBrandDesc: Any?=null,
    val CatalogueBrandId: Int?=null,
    val CatalogueBrandName: Any?=null,
    val CatalogueId: Int?=null,
    val CatalogueType: Int?=null,
    val CatalougeBrandName: Any?=null,
    val CategoryID: Int?=null,
    val CategoryParentID: Int?=null,
    val CatogoryId: Int?=null,
    val CatogoryImage: Any?=null,
    val CatogoryName: Any?=null,
    val Color_Code: Any?=null,
    val Color_Id: Int?=null,
    val Color_Name: Any?=null,
    val CommandName: Any?=null,
    val CountryCurrencyCode: String?=null,
    val CountryID: Int?=null,
    val CreatedBy: Any?=null,
    val CreatedDate: Any?=null,
    val DailyAvgCash: String?=null,
    val DeliveryType: String?=null,
    val DreamGiftId: Int?=null,
    val ExpectedDelivery: Any?=null,
    val ExpiryDate: Any?=null,
    val ExpiryOn: Int?=null,
    val FromDate: Any?=null,
    val GreaterAvgCash: String?=null,
    val HasPartialPayment: Boolean?=null,
    val IsActive: Boolean?=null,
    val IsApproved: Boolean?=null,
    val IsCash: Boolean?=null,
    val IsPlanner: Boolean?=null,
    val IsPopularCount: Int?=null,
    val JFromDate: Any?=null,
    val JRedemptionDate: Any?=null,
    val JToDate: Any?=null,
    val LesserAvgCash: String?=null,
    val LocationId: Int?=null,
    val LoyaltyId: String?=null,
    val MSQA: Int?=null,
    val MemberName: Any?=null,
    val MerchantId: Int?=null,
    val MerchantName: Any?=null,
    val MinimumStockQunty: Int?=null,
    val Mobile: Any?=null,
    val ModelId: Int?=null,
    val ModelName: Any?=null,
    val MultipleRedIds: Any?=null,
    val NoOfPointsDebit: Int?=null,
    val NoOfQuantity: Int?=null,
    val PartialPaymentCash: Int?=null,
    val PlannerStatus: Any?=null,
    val PointBalance: Int?=null,
    val PointRedem: Int?=null,
    val PointReqToAcheiveProduct: Int?=null,
    val PointsPerUnit: Int?=null,
    val PointsRequired: Int?=null,
    val ProductCode: String?=null,
    val ProductDesc: String?=null,
    val ProductImage: String?=null,
    val ProductImageServerPath: Any?=null,
    val ProductName: String?=null,
    val Product_type: Int?=null,
    val RedeemableAverageEarning: String?=null,
    val RedeemableAverageEarning12: Int?=null,
    val RedeemableAverageEarning6: Int?=null,
    val RedeemableEncashBalance: Int?=null,
    val RedeemablePointBalance: Int?=null,
    val RedemptionDate: Any?=null,
    val RedemptionId: Int?=null,
    val RedemptionPlannerId: Int?=null,
    val RedemptionRefno: Any?=null,
    val RedemptionStatus: Any?=null,
    val RedemptionTypeId: Int?=null,
    val SegmentDetails: Any?=null,
    val SelectedStatus: Int?=null,
    val Status: Int?=null,
    val SubCategoryID: Int?=null,
    val SubCategoryName: Any?=null,
    val TermsCondition: Any?=null,
    val ToDate: Any?=null,
    val TotalCash: Int?=null,
    val Total_Row: Int?=null,
    val UserAccess: Int?=null,
    val VendorId: Int?=null,
    val VendorName: String?=null,
    val max_points: Any?=null,
    val min_points: Any?=null
):Serializable

/*Remove WishList By ID Request*/
@JsonClass(generateAdapter = true)
data class RemoveWishListRequest(
    val ActionType: Int,
    val ActorId: String,
    val RedemptionPlannerId: String
)

/*Remove WishList By ID Response*/
@JsonClass(generateAdapter = true)
data class RemoveWishListResponse(
    val ObjCatalogueList: Any?=null,
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null
)

/*WishList Added or Not in WishList by Id Request*/
@JsonClass(generateAdapter = true)
data class AddOrNotWishListRequest(
    val ActionType: Int,
    val ActorId: String,
    val ObjCatalogueDetails: ObjCatalogueDetails
)

/*WishList Added or Not in WishList by Id Response*/
@JsonClass(generateAdapter = true)
data class ObjCatalogueDetails(
    val CashValue: String,
    val CatalogueId: String
)

@JsonClass(generateAdapter = true)
data class AddOrNotWishListResponse(
    val CatalogueImageGallery: Any?=null,
    val LocationCites: Any?=null,
    val ObjCatalogueCategoryList: Any?=null,
    val ObjCatalogueFixedPoints: Any?=null,
    val ObjCatalogueList: Any?=null,
    val ObjCatalogueRedemReqList: Any?=null,
    val ObjCustShippingAddressDetails: Any?=null,
    val ReturnMessage: String?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null,
    val lstCatalogueProductAvailableCity: Any?=null
)
/*WishListAdded request*/
@JsonClass(generateAdapter = true)
data class WishListAddedRequest(
    val ActionType: Int,
    val ActorId: String
)

/*WishList Added response*/
@JsonClass(generateAdapter = true)
data class WishListAddedResponse(
    val ObjCatalogueList: List<ObjCatalogue>?=null,
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null
)
