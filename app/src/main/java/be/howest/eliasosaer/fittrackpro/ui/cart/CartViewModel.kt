package be.howest.eliasosaer.fittrackpro.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.usecase.ClearCartUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.ObserveCartUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.PlaceOrderUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.RemoveFromCartUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val observeCart: ObserveCartUseCase,
    private val removeFromCart: RemoveFromCartUseCase,
    private val updateCartQuantity: UpdateCartQuantityUseCase,
    private val placeOrder: PlaceOrderUseCase,
    private val clearCart: ClearCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCart().collectLatest { items ->
                _uiState.update {
                    it.copy(
                        cartItems = items,
                        totalPrice = items.sumOf { cartItem ->
                            cartItem.price * cartItem.qty
                        }
                    )
                }
            }
        }
    }

    fun onRemove(itemId: String) {
        viewModelScope.launch {
            removeFromCart(itemId)
        }
    }

    fun onUpdateQuantity(itemId: String, qty: Int) {
        viewModelScope.launch {
            if (qty < 1) {
                removeFromCart(itemId)
            } else {
                updateCartQuantity(itemId, qty)
            }
        }
    }

    fun onCheckout() {
        viewModelScope.launch {
            val currentState = _uiState.value
            placeOrder(
                items = currentState.cartItems,
                totalPrice = currentState.totalPrice
            )
            clearCart()
        }
    }
}