package be.howest.eliasosaer.fittrackpro.domain.repository

import be.howest.eliasosaer.fittrackpro.domain.model.Order

interface OrderRepository {
    suspend fun placeOrder(order: Order)
}