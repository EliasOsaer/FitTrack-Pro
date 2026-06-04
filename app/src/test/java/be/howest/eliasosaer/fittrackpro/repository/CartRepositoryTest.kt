package be.howest.eliasosaer.fittrackpro.repository

import be.howest.eliasosaer.fittrackpro.data.repository.CartRepositoryImpl
import be.howest.eliasosaer.fittrackpro.domain.model.CartItem
import be.howest.eliasosaer.fittrackpro.domain.repository.CartRepository
import be.howest.eliasosaer.fittrackpro.fake.FakeCartDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartRepositoryTest {

    private lateinit var repository: CartRepository

    @Before
    fun setUp() {
        repository = CartRepositoryImpl(FakeCartDao())
    }

    @Test
    fun add_itemAppearsInCart() = runTest {
        val item = CartItem(
            id = "1",
            productId = "p1",
            name = "Protein Shake",
            price = 9.99,
            imageUrl = null,
            qty = 2
        )

        repository.add(item)

        val cartItems = repository.observeCart().first()

        assertEquals(1, cartItems.size)
        assertEquals("Protein Shake", cartItems.first().name)
    }

    @Test
    fun remove_itemIsRemovedFromCart() = runTest {
        val item = CartItem(
            id = "1",
            productId = "p1",
            name = "Protein Shake",
            price = 9.99,
            imageUrl = null,
            qty = 2
        )

        repository.add(item)
        repository.remove("1")

        val cartItems = repository.observeCart().first()

        assertEquals(0, cartItems.size)
    }

    @Test
    fun updateQuantity_quantityChanges() = runTest {
        val item = CartItem(
            id = "1",
            productId = "p1",
            name = "Protein Shake",
            price = 9.99,
            imageUrl = null,
            qty = 2
        )

        repository.add(item)
        repository.updateQuantity("1", 5)

        val updatedItem = repository.observeCart().first().first()

        assertEquals(5, updatedItem.qty)
    }

    @Test
    fun clear_cartBecomesEmpty() = runTest {
        repository.add(
            CartItem(
                id = "1",
                productId = "p1",
                name = "Protein Shake",
                price = 9.99,
                imageUrl = null,
                qty = 2
            )
        )

        repository.clear()

        val cartItems = repository.observeCart().first()

        assertEquals(0, cartItems.size)
    }
}