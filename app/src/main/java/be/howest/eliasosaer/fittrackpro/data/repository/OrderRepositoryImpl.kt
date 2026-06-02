package be.howest.eliasosaer.fittrackpro.data.repository

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import be.howest.eliasosaer.fittrackpro.data.messaging.DeliveryConsumer
import be.howest.eliasosaer.fittrackpro.data.messaging.OrderPublisher
import be.howest.eliasosaer.fittrackpro.data.worker.NotificationWorker
import be.howest.eliasosaer.fittrackpro.domain.model.Order
import be.howest.eliasosaer.fittrackpro.domain.model.OrderStatusMessage
import be.howest.eliasosaer.fittrackpro.domain.repository.OrderRepository
import com.squareup.moshi.Moshi
import javax.inject.Inject
import kotlin.jvm.java

class OrderRepositoryImpl @Inject constructor(
    private val publisher: OrderPublisher,
    private val consumer: DeliveryConsumer,
    private val workManager: WorkManager
): OrderRepository {
    @Inject
    lateinit var moshi: Moshi

    override suspend fun placeOrder(order: Order) {
        val adapter = moshi.adapter(Order::class.java)
        val orderJson = adapter.toJson(order)
        publisher.publishMessage(orderJson)
    }

    init {
        consumer.onMessageReceived = { json ->
            Log.d("OrderRepositoryImpl", "onMessageReceived: $json")
            val adapter = moshi.adapter(OrderStatusMessage::class.java)
            val order: OrderStatusMessage? = adapter.fromJson(json)
            processOrderDelivery(order!!.orderId)
        }
        consumer.startConsuming()
    }

    fun stop() {
        consumer.stopConsuming()
    }

    override fun processOrderDelivery(message: String){
        val data = workDataOf("ORDER_ID" to message)
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(data)
            .build()
        workManager.enqueue(workRequest)
    }
}