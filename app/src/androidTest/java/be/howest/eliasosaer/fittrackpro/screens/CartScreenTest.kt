package be.howest.eliasosaer.fittrackpro.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.howest.eliasosaer.fittrackpro.domain.model.CartItem
import be.howest.eliasosaer.fittrackpro.ui.cart.CartScreen
import be.howest.eliasosaer.fittrackpro.ui.cart.CartUiState
import org.junit.Rule
import org.junit.Test

class CartScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeItem = CartItem(
        id = "1",
        productId = "p1",
        name = "Protein Bar",
        price = 2.50,
        imageUrl = null,
        qty = 2
    )

    @Test
    fun cartScreen_emptyState_isDisplayed() {

        val emptyState = CartUiState(
            cartItems = emptyList(),
            totalPrice = 0.0
        )

        composeTestRule.setContent {
            CartScreen(
                uiState = emptyState,
                onBackClick = {},
                onCheckoutClick = {},
                onIncreaseQuantity = {},
                onDecreaseQuantity = {}
            )
        }

        composeTestRule
            .onNodeWithText("Your cart is empty")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Add some gear from the shop first.")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Back to shop")
            .assertIsDisplayed()
    }

    @Test
    fun cartScreen_filledState_showsItemsAndTotal() {

        val state = CartUiState(
            cartItems = listOf(fakeItem),
            totalPrice = 5.0
        )

        composeTestRule.setContent {
            CartScreen(
                uiState = state,
                onBackClick = {},
                onCheckoutClick = {},
                onIncreaseQuantity = {},
                onDecreaseQuantity = {}
            )
        }

        composeTestRule
            .onNodeWithText("Protein Bar")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Checkout")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Subtotal:")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Delivery Fee:")
            .assertIsDisplayed()
    }

    @Test
    fun cartScreen_checkout_button_clicks() {

        var checkoutClicked = false

        val state = CartUiState(
            cartItems = listOf(fakeItem),
            totalPrice = 5.0
        )

        composeTestRule.setContent {
            CartScreen(
                uiState = state,
                onBackClick = {},
                onCheckoutClick = { checkoutClicked = true },
                onIncreaseQuantity = {},
                onDecreaseQuantity = {}
            )
        }

        composeTestRule
            .onNodeWithText("Checkout")
            .performClick()

        assert(checkoutClicked)
    }
}