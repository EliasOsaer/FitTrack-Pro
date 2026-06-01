package be.howest.eliasosaer.fittrackpro.data.remote.api

import be.howest.eliasosaer.fittrackpro.data.remote.dto.GearDto
import retrofit2.http.GET

interface GearApi {
    @GET("gear")
    suspend fun getGear(): List<GearDto>
}