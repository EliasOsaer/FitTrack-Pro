package be.howest.eliasosaer.fittrackpro.ui.cart

import be.howest.eliasosaer.fittrackpro.domain.model.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
)
