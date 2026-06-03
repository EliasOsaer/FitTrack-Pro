package be.howest.eliasosaer.fittrackpro.data.repository

import be.howest.eliasosaer.fittrackpro.data.local.room.dao.CartDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.CartItemEntity
import be.howest.eliasosaer.fittrackpro.domain.model.CartItem
import be.howest.eliasosaer.fittrackpro.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun add(item: CartItem) {
        cartDao.insert(CartItemEntity(
            id = item.id,
            productId = item.productId,
            name = item.name,
            price = item.price,
            imageUrl = item.imageUrl,
            qty = item.qty
        ))
    }

    override suspend fun remove(itemId: String) {
        cartDao.delete(itemId)
    }

    override suspend fun updateQuantity(itemId: String, qty: Int) {
        val entity = cartDao.getCartItemById(itemId).first()
        cartDao.update(entity.copy(qty = qty))
    }

    override suspend fun clear() {
        cartDao.clear()
    }

    override fun observeCart(): Flow<List<CartItem>> {
        return cartDao.getCartItems().map { entities ->
            entities.map { e ->
                CartItem(
                    id = e.id,
                    productId = e.productId,
                    name = e.name,
                    price = e.price,
                    imageUrl = e.imageUrl,
                    qty = e.qty
                )
            }
        }
    }
}
