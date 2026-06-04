package be.howest.eliasosaer.fittrackpro.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.howest.eliasosaer.fittrackpro.data.local.room.AppDatabase
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.CartDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.CartItemEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var cartDao: CartDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        cartDao = database.cartDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCartItem_itemIsStoredInDatabase() = runTest {

        val item = CartItemEntity(
            id = "1",
            productId = "p1",
            name = "Protein Bar",
            price = 2.50,
            imageUrl = null,
            qty = 2
        )

        cartDao.insert(item)

        val items = cartDao.getCartItems().first()

        assertEquals(1, items.size)
        assertEquals("Protein Bar", items.first().name)
    }

    @Test
    fun deleteCartItem_itemIsRemoved() = runTest {

        val item = CartItemEntity(
            id = "1",
            productId = "p1",
            name = "Protein Bar",
            price = 2.50,
            imageUrl = null,
            qty = 2
        )

        cartDao.insert(item)
        cartDao.delete("1")

        val items = cartDao.getCartItems().first()

        assertEquals(0, items.size)
    }

    @Test
    fun updateCartItem_quantityIsUpdated() = runTest {

        val item = CartItemEntity(
            id = "1",
            productId = "p1",
            name = "Protein Bar",
            price = 2.50,
            imageUrl = null,
            qty = 2
        )

        cartDao.insert(item)

        val updated = item.copy(qty = 10)
        cartDao.update(updated)

        val result = cartDao.getCartItemById("1").first()

        assertEquals(10, result.qty)
    }

    @Test
    fun clearCart_removesAllItems() = runTest {

        cartDao.insert(
            CartItemEntity(
                id = "1",
                productId = "p1",
                name = "Protein Bar",
                price = 2.50,
                imageUrl = null,
                qty = 2
            )
        )

        cartDao.clear()

        val items = cartDao.getCartItems().first()

        assertEquals(0, items.size)
    }
}