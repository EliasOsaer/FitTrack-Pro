package be.howest.eliasosaer.fittrackpro.fake

class FakeDeliveryConsumer {

    var callback: ((String) -> Unit)? = null

    fun startConsuming(onMessageReceived: (String) -> Unit) {
        callback = onMessageReceived
    }

    fun simulateMessage(message: String) {
        callback?.invoke(message)
    }

    fun stopConsuming() {
        callback = null
    }
}