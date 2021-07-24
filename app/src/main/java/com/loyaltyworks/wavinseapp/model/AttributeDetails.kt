package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttributeRequest(
    val ActionType: String? = null,
    val ActorId: String? = null
)

@JsonClass(generateAdapter = true)
data class AttributeResponse(
    val lstAttributesDetails: List<LstAttributesDetails>? = null
)

@JsonClass(generateAdapter = true)
data class LstAttributesDetails(
    val AttributeContents: Any? = null,
    val AttributeId: Int? = null,
    val AttributeType: String? = null,
    val AttributeValue: String? = null
)



/***  data class request and response of check UserName Exist or Not ***/
/*

*/
/*  Request*//*

@JsonClass(generateAdapter = true)
data class AttributeRequestCheckUserName(
    val ActionType: Int,
    val Location: Locations
)

@JsonClass(generateAdapter = true)
data class Locations(
    val UserName: String
)

*/
/* Response  *//*


data class listCheckUserNameExistResult (
   val CheckUserNameExistsResult : CheckUserNameExistsResult
)

data class CheckUserNameExistsResult (
  val ReturnMessage : String,
  val ReturnValue : Int,
  val TotalRecords : Int
)
*/
