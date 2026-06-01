package be.howest.eliasosaer.fittrackpro.data.repository

import be.howest.eliasosaer.fittrackpro.data.messaging.OrderPublisher
import be.howest.eliasosaer.fittrackpro.domain.model.Order
import be.howest.eliasosaer.fittrackpro.domain.repository.OrderRepository
import com.squareup.moshi.Moshi
import javax.inject.Inject
import kotlin.jvm.java

class OrderRepositoryImpl @Inject constructor(
    private val publisher: OrderPublisher,
): OrderRepository {
    @Inject
    lateinit var moshi: Moshi

    override suspend fun placeOrder(order: Order) {
        val adapter = moshi.adapter(Order::class.java)
        val orderJson = adapter.toJson(order)
        publisher.publishMessage(orderJson)
    }
}