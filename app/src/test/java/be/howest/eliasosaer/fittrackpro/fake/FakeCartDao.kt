package be.howest.eliasosaer.fittrackpro.fake

import be.howest.eliasosaer.fittrackpro.data.local.room.dao.CartDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCartDao : CartDao {

    private val items = mutableListOf<CartItemEntity>()

    override fun getCartItems(): Flow<List<CartItemEntity>> {
        return flowOf(items.toList())
    }

    override fun getCartItemById(id: String): Flow<CartItemEntity> {
        return flowOf(items.first { it.id == id })
    }

    override suspend fun insert(item: CartItemEntity) {
        items.removeAll { it.id == item.id }
        items.add(item)
    }

    override suspend fun update(item: CartItemEntity) {
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            items[index] = item
        }
    }

    override suspend fun delete(id: String) {
        items.removeAll { it.id == id }
    }

    override suspend fun clear() {
        items.clear()
    }
}