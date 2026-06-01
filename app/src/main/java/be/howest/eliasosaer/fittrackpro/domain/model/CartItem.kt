package be.howest.eliasosaer.fittrackpro.domain.model

data class CartItem(
    val id: String,
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String?,
    val qty: Int
)
