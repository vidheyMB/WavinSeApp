package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DistrictRequest(
    val StateId: String? = null
)

@JsonClass(generateAdapter = true)
data class DistrictResponse(
    val ReturnMessage: Any? = null,
    val ReturnValue: Int? = null,
    val TotalRecords: Int? = null,
    val lstDistrict: List<LstDistrict>? = null
)

@JsonClass(generateAdapter = true)
data class LstDistrict(
    val DistrictCode: String? = null,
    var DistrictId: Int? = null,
    var DistrictName: String? = null,
    val IsActive: Boolean? = null,
    val Row: Int? = null,
    val StateId: Int? = null
)