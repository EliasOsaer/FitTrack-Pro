package be.howest.eliasosaer.fittrackpro.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderStatusMessage(
    val orderId: String,
    val status: String,
    val message: String
)