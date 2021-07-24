package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CityRequest (
    var StateId: String? = null
)


@JsonClass(generateAdapter = true)
data class CityResponse(
    var ReturnMessage: Any? = null,
    var ReturnValue: Int? = null,
    var TotalRecords: Int? = null,
    var CityList: List<CityLists>? = null
)


@JsonClass(generateAdapter = true)
data class CityLists(
    var Row: Int? = null,
    var CountryCode: Any? = null,
    var CountryId: Int? = null,
    var CountryName: Any? = null,
    var CountryType: Any? = null,
    var IsActive: Boolean? = null,
    var MobilePrefix: Any? = null,
    var StateCode: Any? = null,
    var StateId: Int? = null,
    var StateName: Any? = null,
    var CityCode: String? = null,
    var CityId: Int? = null,
    var CityName: String? = null
)