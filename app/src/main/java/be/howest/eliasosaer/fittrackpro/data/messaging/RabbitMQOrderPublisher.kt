package be.howest.eliasosaer.fittrackpro.data.messaging

import android.util.Log
import com.rabbitmq.client.ConnectionFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class RabbitMQOrderPublisher @Inject constructor(
    private val hostName: String,
    private val queueName: String
): OrderPublisher {
    private val factory = ConnectionFactory().apply {
        setUri(hostName)
    }

    override suspend fun publishMessage(message: String) {
        withContext(Dispatchers.IO) {
            try {
                val connection = factory.newConnection()
                val channel = connection.createChannel()

                channel.queueDeclare(queueName, true, false, false, null)
                channel.basicPublish(
                    "",
                    queueName,
                    null,
                    message.toByteArray(StandardCharsets.UTF_8)
                )
                Log.i("Messagebroker", "Message sent to broker: $message")

                channel.close()
                connection.close()

            } catch (e: Exception) {
                Log.e("Messagebroker", "Error publishing to Messagebroker! - ${e.message}")
            }
        }
    }
}