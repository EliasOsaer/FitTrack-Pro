package be.howest.eliasosaer.fittrackpro.data.messaging

interface DeliveryConsumer {
    fun startConsuming()
    fun stopConsuming()
    var onMessageReceived: (String) -> Unit
}