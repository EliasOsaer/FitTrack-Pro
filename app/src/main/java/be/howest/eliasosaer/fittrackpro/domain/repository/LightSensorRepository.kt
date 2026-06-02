package be.howest.eliasosaer.fittrackpro.domain.repository

import kotlinx.coroutines.flow.Flow

interface LightSensorRepository {
    val lightIntensity: Flow<Float>
    fun startListening()
    fun stopListening()
    fun isSensorAvailable(): Boolean
}