package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.CartItem
import be.howest.eliasosaer.fittrackpro.domain.model.Order
import be.howest.eliasosaer.fittrackpro.domain.repository.OrderRepository
import java.util.UUID
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
){
    suspend operator fun invoke(items: List<CartItem>, totalPrice: Double){
        val order = Order(
            id = UUID.randomUUID().toString(),
            items = items,
            totalPrice = totalPrice
        )
        orderRepository.placeOrder(order)
    }
}