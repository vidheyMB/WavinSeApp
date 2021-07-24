package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SEQueryListingRequest(
    var ActionType: Int?=null,
    var ActorId: Int?=null,
    var DateFrom: String?=null,
    var DateTo: String?=null,
    var HelpTopicSearchID: Int?=null,
    var PageSize: Int?=null,
    var QueryStatusSearchID: Int?=null,
    var SearchText: String?=null,
    var StartIndex: Int?=null
)
@JsonClass(generateAdapter = true)
data class SEQueryListingResponse(
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null,
    val objQueryCenterAPIInfoList: List<ObjQueryCenterAPIInfo>?=null,
    val objQueryCenterList: Any?=null,
    val objQueryResponseList: Any?=null
)

@JsonClass(generateAdapter = true)
data class ObjQueryCenterAPIInfo(
    val AreaName: String?=null,
    val AssignedTo: String?=null,
    val BranchName: String?=null,
    val CreatedDate: String?=null,
    val CustomerGrade: String?=null,
    val CustomerId: Int?=null,
    val CustomerTicketID: Int?=null,
    val CustomerType: Any?=null,
    val Email: Any?=null,
    val FirstAttemptedBy: String?=null,
    val FirstAttemptedOn: String?=null,
    val HelpTopic: String?=null,
    val HelpTopicId: Int?=null,
    val LastDate: String?=null,
    val MemberName: String?=null,
    val MembershipID: String?=null,
    val Mobile: Any?=null,
    val MobileNo: String?=null,
    val ModifiedBy: String?=null,
    val PointBal: Int?=null,
    val PushID: String?=null,
    val QueryAlert: Int?=null,
    val QueryDetails: Any?=null,
    val QueryGeneratedDate: Any?=null,
    val QueryID: String?=null,
    val QueryStatus: String?=null,
    val QuerySummary: String?=null,
    val Remarks: String?=null,
    val ReportedDate: String?=null,
    val ResolveDate: String?=null,
    val Source: String?=null,
    val SourceOfLogin: String?=null,
    val SubHelpTopic: String?=null,
    val SubHelpTopicID: Int?=null,
    val TotalRows: Int?=null,
    val UserId: Int?=null
):Serializable

/*SE Query Chat Request*/
@JsonClass(generateAdapter = true)
data class SEQueryChatRequest(
    val ActionType: String,
    val ActorId: String,
    val CustomerTicketID: String
)

/*SE Query Chat Response*/
@JsonClass(generateAdapter = true)
data class SEQueryChatResponse(
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null,
    val objQueryCenterList: Any?=null,
    val objQueryResponseJsonList: List<ObjQueryResponse>?=null
)


@JsonClass(generateAdapter = true)
data class ObjQueryResponse(
    val Color: String?=null,
    val CreatedDate: String?=null,
    val ImageUrl: String?=null,
    val JCreatedDate: String?=null,
    val QueryResponseInfo: String?=null,
    val QueryStatus: String?=null,
    val RepliedBy: String?=null,
    val UserType: String?=null
)

@JsonClass(generateAdapter = true)
data class SEQueryChatReplyRequest(
    var ActionType: String?=null,
    var ActorId: String?=null,
    var CustomerTicketID: String?=null,
    var HelpTopicID: String?=null,
    var ImageUrl: String?=null,
    var IsQueryFromMobile: String?=null,
    var QueryDetails: String?=null,
    var QueryReplyComments: String?=null,
    var QueryStatus: String?=null,
    var QuerySummary: String?=null,
    var ReplyType: String?=null,
    var SourceType: String?=null
)

@JsonClass(generateAdapter = true)
data class SEQueryChatReplyResponse(
    val ReturnMessage: String?=null,
    val ReturnValue: Int?=null,
    val TotalRecords: Int?=null,
    val objCustomerAllQueryJsonList: Any?=null,
    val objCustomerAllQueryList: Any?=null
)