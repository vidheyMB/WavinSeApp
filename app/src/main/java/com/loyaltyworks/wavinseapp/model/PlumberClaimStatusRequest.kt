package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class RetailerOrderRequest(
    var ActionType: String? = null,
    var ActorId: String? = null,
    var JFromDate: String? = null,
    var JToDate: String? = null,
    var OrderStatusId: String? = null,
    var RetailerLoyaltyId: String? = null,
    var SearchText: String? = null,
    var FilterByID: Int? = null
)

@JsonClass(generateAdapter = true)
data class RetailerOrderResponse(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lstCustOrderDeliveryDetails: List<LstCustOrderDeliveryDetail>? = null,
    val lstTransactionApprovalDetails: List<Any>? = null
)

@JsonClass(generateAdapter = true)
data class LstCustOrderDeliveryDetail(
    val Color: Any? = null,
    val CustomerCode: String? = null,
    val CustomerMobile: String? = null,
    val CustomerName: String? = null,
    val CustomerUserId: Int? = null,
    val DeliveryAddress: Any? = null,
    val DeliveryStatus: Any? = null,
    val DeliveryStatusID: Int? = null,
    val DistributorCode: String? = null,
    val DistributorName: String? = null,
    val DistributorUserId: Int? = null,
    val FinalAmount: String? = null,
    val InvoiceDate: Any? = null,
    val InvoiceNo: Any? = null,
    val MRP: Int? = null,
    val NetItemValue: Int? = null,
    val OrderDate: String? = null,
    val OrderDetailsID: Int? = null,
    val OrderID: Int? = null,
    val OrderNo: String? = null,
    val OrderStatus: String? = null,
    val OrderStatusId: Int? = null,
    val ProdCode: Any? = null,
    val ProductName: Any? = null,
    val Quantity: Int? = null,
    val Rate: Any? = null,
    val SKU: Any? = null,
    val SellingPrice: Int? = null,
    val SkuId: Int? = null,
    val SkuRate: Int? = null,
    val SourceMode: String? = null,
    val SourceModeID: Int? = null,
    val UOM: Any? = null
) : Serializable




/* order details */
@JsonClass(generateAdapter = true)
data class OrderDetailsRequest(
    val ActionType: String? = null,
    val ActorId: String? = null,
    val OrderId: String? = null
)

@JsonClass(generateAdapter = true)
data class OrderDetailsResponse(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lstCustOrderDeliveryDetails: List<LstCustOrderDeliveryDetailss>? = null,
    val lstOrderDetails: Any? = null
)

@JsonClass(generateAdapter = true)
data class LstCustOrderDeliveryDetailss(
    val Color: String? = null,
    val CustomerCode: Any? = null,
    val CustomerMobile: Any? = null,
    val CustomerName: Any? = null,
    val CustomerUserId: Int? = null,
    val DeliveryAddress: Any? = null,
    val DeliveryStatus: Any? = null,
    val DeliveryStatusID: Int? = null,
    val DistributorCode: Any? = null,
    val DistributorName: Any? = null,
    val DistributorUserId: Int? = null,
    val FinalAmount: Any? = null,
    val InvoiceDate: Any? = null,
    val InvoiceNo: Any? = null,
    val MRP: Double? = null,
    val NetItemValue: Double? = null,
    val OrderDate: Any? = null,
    val OrderDetailsID: Int? = null,
    val OrderID: Int? = null,
    val OrderNo: Any? = null,
    val OrderStatus: String? = null,
    val OrderStatusId: Int? = null,
    val ProdCode: String? = null,
    val ProductName: String? = null,
    val Quantity: Int? = null,
    val Rate: String? = null,
    val SKU: String? = null,
    val SellingPrice: Double? = null,
    val SkuId: Int? = null,
    val SkuRate: Int? = null,
    val SourceMode: Any? = null,
    val SourceModeID: Int? = null,
    val UOM: String? = null
)