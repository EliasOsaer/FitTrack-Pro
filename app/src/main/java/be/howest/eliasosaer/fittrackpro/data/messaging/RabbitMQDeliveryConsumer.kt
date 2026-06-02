package be.howest.eliasosaer.fittrackpro.data.messaging

import android.util.Log
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

class RabbitMQDeliveryConsumer (
    private val host: String,
    private val queueName: String
): DeliveryConsumer {
    private val factory = ConnectionFactory()
    private var consumerJob: Job? = null

    init {
        if (host.isBlank()) {
            Log.e("Messagebroker", "Invalid RabbitMQ host URI: host is blank or null")
        } else {
            try {
                factory.setUri(host)
            } catch (e: Exception) {
                Log.e("Messagebroker", "Invalid RabbitMQ URI: $host", e)
            }
        }
    }

    override var onMessageReceived: (String) -> Unit = {Log.w("Messagebroker", "onMessageReceived not set")}

    override fun startConsuming() {
        if (host.isBlank()) {
            Log.e("Messagebroker", "Cannot start consumer: invalid RabbitMQ host URI")
            return
        }

        consumerJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val connection = factory.newConnection()
                val channel = connection.createChannel()

                channel.queueDeclare(queueName, true, false, false, null)

                val deliverCallback = DeliverCallback { _, delivery ->
                    val message = String(delivery.body, StandardCharsets.UTF_8)
                    onMessageReceived(message)
                }

                channel.basicConsume(queueName, true, deliverCallback, CancelCallback {
                    Log.w("Messagebroker", "Consumer cancelled")
                })

                while (isActive) delay(1000)

            } catch (e: Exception) {
                Log.e("Messagebroker", "Error consuming from Messagebroker! - ${e.message}")
            }
        }
    }

    override fun stopConsuming() {
        consumerJob?.cancel()
    }
}