package be.howest.eliasosaer.fittrackpro.data.remote

import be.howest.eliasosaer.fittrackpro.data.remote.api.GearApi
import be.howest.eliasosaer.fittrackpro.data.remote.dto.GearDto
import jakarta.inject.Inject

class GearApiClient @Inject constructor(
    private val gearApi: GearApi
){
    suspend fun getGear(): List<GearDto> {
        return gearApi.getGear()
    }
}
