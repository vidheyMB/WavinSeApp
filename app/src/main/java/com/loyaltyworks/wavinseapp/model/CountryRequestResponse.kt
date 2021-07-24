package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

/*Country Request*/
@JsonClass(generateAdapter = true)
data class CountryRequest(
    var SortColumn: String,
    var SortOrder: String,
    var StartIndex: Int
)

/*Country Response*/
@JsonClass(generateAdapter = true)
data class CountryResponse(
    var ReturnMessage: Any?=null,
    var Returnvarue: Int?=null,
    var TotalRecords: Int?=null,
    var CountryList: List<CountryList>?=null
)

@JsonClass(generateAdapter = true)
data class CountryList(
    var CountryCode: String?=null,
    var CountryId: Int?=null,
    var CountryName: String?=null,
    var CountryType: Any?=null,
    var IsActive: Boolean?=null,
    var MobilePrefix: String?=null,
    var Row: Int?=null
)