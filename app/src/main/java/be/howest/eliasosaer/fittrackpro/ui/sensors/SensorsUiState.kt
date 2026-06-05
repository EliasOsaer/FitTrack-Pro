package be.howest.eliasosaer.fittrackpro.ui.sensors

data class SensorsUiState(
    val lightIntensity: Float = 0f,
    val movementLabel: String = "Still",
    val isLightSensorAvailable: Boolean = true,
    val isMovementSensorAvailable: Boolean = true,
    val isFlashlightOn: Boolean = false
)