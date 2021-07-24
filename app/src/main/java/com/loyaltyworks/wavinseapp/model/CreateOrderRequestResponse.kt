package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

// Category Ordre Lising Request
@JsonClass(generateAdapter = true)
data class CategoryOrderListingRequest(
    var ProductId: String? = null,
    var ActionType: String? = null,
    var ActorId: String? = null,
    var ProductDetails: productDetails? = null
)

@JsonClass(generateAdapter = true)
data class productDetails(
    var BrandId: String? = null,
    var Cat1: String? = null,
    var Cat2: String? = null,
    var ProductId: String? = null,
    var SkuMaxPrice: String? = null,
    var SkuMinPrice: String? = null
)


// Category order Listing Response
@JsonClass(generateAdapter = true)
data class CategoryOrderListingResponse(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lsrProductDetails: List<LsrProductDetail>? = null
)


@JsonClass(generateAdapter = true)
data class LsrProductDetail(
    val BrandId: Int? = null,
    val BrandName: String? = null,
    val Cat_Id1: Int? = null,
    val Cat_Id2: Int? = null,
    val Cat_Name1: String? = null,
    val Cat_Name2: String? = null,
    val Color: String? = null,
    val CustomerCartCount: Int? = null,
    val CustomerCartId: Int? = null,
    val Discount: Int? = null,
    val GSTValue: Any? = null,
    val Payment: Any? = null,
    val ProductCode: Any? = null,
    val ProductDesc: Any? = null,
    val ProductId: Int? = null,
    val ProductImg: Any? = null,
    val ProductName: String? = null,
    val ProductShortDesc: Any? = null,
    var Quantity: String? = null,
    val Rate: Int? = null,
    val SKUDesc: Any? = null,
    val SKUPrice: String? = null,
    val SalePrice: Int? = null,
    val SkuId: Int? = null,
    val SkuName: String? = null,
    val SumQuantity: Int? = null,
    val UOM: String? = null,
    val lsrCartProductDetails: List<Any>? = null
) : Serializable


// add to cart request response

@JsonClass(generateAdapter = true)
data class AddToCartRequest(
    var ActorId: String? = null,
    var LoyaltyID: String? = null,
    var MerchantId: String? = null,
    var ProductSaveDetailList: List<ProductSaveDetail>? = null
)

@JsonClass(generateAdapter = true)
data class ProductSaveDetail(
    var ProductId: String? = null,
    var Quantity: String? = null,
    var SkuId: String? = null,
    var TransactionType: String? = null
)

@JsonClass(generateAdapter = true)
data class AddToCartResponse(
    val ProductSaveDetailList: Any? = null,
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null
)


//   cart listing request
@JsonClass(generateAdapter = true)
data class CartListingRequest(
    val ActionType: String? = null,
    val LoyaltyId: String? = null
)

@JsonClass(generateAdapter = true)
data class CartListingResponse(
    val TotalCartProducts: Any? = null,
    val lstCustomerCart: List<LstCustomerCart>? = null
)

@JsonClass(generateAdapter = true)
data class LstCustomerCart(
    val Address: Any? = null,
    val AllowDemoCharge: Boolean? = null,
    val CancelledOrder: Int? = null,
    val CityName: Any? = null,
    val Comment: Any? = null,
    val CompanyName: Any? = null,
    val CompletedOrder: Int? = null,
    val CountryName: Any? = null,
    val CustomerCartId: Int? = null,
    val DemoCharge: Double? = null,
    val DiscountCriteria: Int? = null,
    val DiscountValue: Int? = null,
    val Email: Any? = null,
    val FinalAmount: Int? = null,
    val FirmTypeName: Any? = null,
    val FullName: Any? = null,
    val GST: Int? = null,
    val GstPercentage: Any? = null,
    val InvoiceNo: Any? = null,
    val ItemType: String? = null,
    val JCartDate: Any? = null,
    val LandingDiscount: Int? = null,
    val LandingPrice: Int? = null,
    val LandingQuantityPrice: Int? = null,
    val LandingTotalGST: Int? = null,
    val LandingTotalPrice: Double? = null,
    val LocationName: Any? = null,
    val LoyaltyId: Any? = null,
    val Mobile: Any? = null,
    val NetAmount: Int? = null,
    val OrderDate: Any? = null,
    val OrderDetailsId: Int? = null,
    val OrderId: Int? = null,
    val OrderNumber: Any? = null,
    val OrderStatus: Any? = null,
    val OrderStatusId: Int? = null,
    val PaymentMode: Any? = null,
    val PaymentStatus: Any? = null,
    val PaymentTranID: Any? = null,
    val PendingOrder: Int? = null,
    val ProdAvailabilityStatus: String? = null,
    val ProdCode: String? = null,
    val ProdDescription: String? = null,
    val ProdImg: String? = null,
    val UOM: String? = null,
    val Color: String? = null,
    val Rate: String? = null,
    val ProdMiscellaneousCode: Any? = null,
    val ProdPrice: Double? = null,
    val ProductGroupInfoId: Int? = null,
    val ProductId: Int? = null,
    val ProductImg: Any? = null,
    val ProductName: String? = null,
    val ProductStockQuantity: Int? = null,
    val PromoCode: Any? = null,
    val PromoCodeId: Int? = null,
    val Quantity: Int? = null,
    val RowNo: Int? = null,
    val RowTotalPrice: Int? = null,
    val SKU: Any? = null,
    val SapCode: Any? = null,
    val ShippedOrder: Int? = null,
    val ShippingName: Any? = null,
    val SkuId: Int? = null,
    val StateName: Any? = null,
    val StatusName: Any? = null,
    val SumLandingQuantityPrice: Int? = null,
    val ToPassStatus: Int? = null,
    val TotalRows: Int? = null,
    val TotalTransaction: Int? = null,
    val UserID: Int? = null,
    val Zip: Any? = null,
    val jEnrolledDate: Any? = null,
    val lsrCartProductDetails: Any? = null
)



// remove product  Request
@JsonClass(generateAdapter = true)
data class RemoveCartProductRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val CustomerCartId: String? = null
)

@JsonClass(generateAdapter = true)
data class RemoveCartProductResponse(
    val ReturnMessage: String? = null,
    val ReturnValue: String? = null,
    val TotalRecords: String? = null
)


// Cart Submit Order
@JsonClass(generateAdapter = true)
data class CartOrderSubmitRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var LoyaltyId: String? = null,
    var OrderStatus: String? = null,
    var SourceModeId: String? = null,
    var CartDetailsList: MutableList<CartDetails>? = null
)


@JsonClass(generateAdapter = true)
data class CartDetails(
    var CustomerCartId: String? = null,
    var ProdPrice: String? = null,
    var ProductId: String? = null,
    var Quantity: String? = null,
    var RowTotalPrice: String? = null,
    var SkuId: String? = null
)

@JsonClass(generateAdapter = true)
data class CartOrderSubmitResponse(
    val ReturnMessage: String? = null,
    val ReturnValue: String? = null,
    val TotalRecords: String? = null
)


// search and order request

@JsonClass(generateAdapter = true)
data class SearchAndOrderRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val ProductId: String? = null
)



// Your Smar Basket and Top Selling SKU Request
@JsonClass(generateAdapter = true)
data class SmartBasketTopSellingSkuRequest(
    val ActionType: String? = null,
    val ActorId: String? = null
)





