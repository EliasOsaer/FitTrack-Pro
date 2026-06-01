package be.howest.eliasosaer.fittrackpro.domain.repository

import be.howest.eliasosaer.fittrackpro.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun add(item: CartItem)
    suspend fun remove(itemId: String)
    suspend fun updateQuantity(itemId: String, qty: Int)
    fun observeCart(): Flow<List<CartItem>>
}
