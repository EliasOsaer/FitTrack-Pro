package be.howest.eliasosaer.fittrackpro.data.messaging

interface OrderPublisher {
    suspend fun publishMessage(message: String)
}