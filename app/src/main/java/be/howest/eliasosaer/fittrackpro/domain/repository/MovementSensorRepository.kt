package be.howest.eliasosaer.fittrackpro.domain.repository

import kotlinx.coroutines.flow.Flow

interface MovementSensorRepository {
    val movementLabel: Flow<String>
    fun startListening()
    fun stopListening()
    fun isSensorAvailable(): Boolean
}