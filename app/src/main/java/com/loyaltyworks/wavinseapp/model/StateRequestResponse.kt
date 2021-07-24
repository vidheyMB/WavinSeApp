package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

/*State Request*/
@JsonClass(generateAdapter = true)
data class StateRequest(
    val ActionType: String,
    val CountryID: Int,
    val IsActive: String
)

/*State Response*/
@JsonClass(generateAdapter = true)
data class StateResponse(
    val ReturnMessage: Any?=null,
    val ReturnValue: Int?=null,
    val StateList: List<State>?=null,
    val TotalRecords: Int?=null
)
@JsonClass(generateAdapter = true)
data class State(
    val CountryCode: Any?=null,
    val CountryId: Int?=null,
    val CountryName: String?=null,
    val CountryType: Any?=null,
    val IsActive: Boolean?=null,
    val MobilePrefix: Any?=null,
    val Row: Int?=null,
    val StateCode: String?=null,
    val StateId: Int?=null,
    val StateName: String?=null
)