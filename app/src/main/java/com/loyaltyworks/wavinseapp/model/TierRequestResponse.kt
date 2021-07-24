package com.loyaltyworks.wavinseapp.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class TierRequest(
    val ActionType: String? = null
)

@JsonClass(generateAdapter = true)
data class TierResponse(
    val lstAttributesDetails: List<LstAttributesDetail>? = null
)

@JsonClass(generateAdapter = true)
data class LstAttributesDetail(
    val AttributeContents: Any? = null,
    val AttributeId: Int? = null,
    val AttributeType: String? = null,
    val AttributeValue: String? = null
)