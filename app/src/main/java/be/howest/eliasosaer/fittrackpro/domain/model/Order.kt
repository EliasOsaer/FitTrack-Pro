package be.howest.eliasosaer.fittrackpro.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalPrice: Double
)