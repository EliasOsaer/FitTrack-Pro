package be.howest.eliasosaer.fittrackpro.messagebroker

import be.howest.eliasosaer.fittrackpro.fake.FakeDeliveryConsumer
import org.junit.Assert.assertEquals
import org.junit.Test

class DeliveryConsumerTest {

    @Test
    fun consumer_callsCallback_whenMessageReceived() {

        val consumer = FakeDeliveryConsumer()

        var receivedMessage: String? = null

        consumer.startConsuming {
            receivedMessage = it
        }

        consumer.simulateMessage("hello")

        assertEquals("hello", receivedMessage)
    }
}