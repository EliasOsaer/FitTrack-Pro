package be.howest.eliasosaer.fittrackpro.domain.repository

import be.howest.eliasosaer.fittrackpro.domain.model.GearItem

interface GearRepository {
    suspend fun getGear(): List<GearItem>
}