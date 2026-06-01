package be.howest.eliasosaer.fittrackpro.data.repository

import be.howest.eliasosaer.fittrackpro.data.remote.GearApiClient
import be.howest.eliasosaer.fittrackpro.domain.model.GearItem
import be.howest.eliasosaer.fittrackpro.domain.repository.GearRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GearRepositoryImpl @Inject constructor(
    private val gearApiClient: GearApiClient
) : GearRepository {
    override suspend fun getGear(): List<GearItem> {
        return gearApiClient.getGear().map { dto ->
            GearItem(
                id = dto.id,
                name = dto.name,
                price = dto.price,
                imageUrl = dto.imageUrl
            )
        }
    }
}
