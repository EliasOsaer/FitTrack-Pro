package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.GearItem
import be.howest.eliasosaer.fittrackpro.domain.repository.GearRepository
import javax.inject.Inject

class GetGearItemsUseCase @Inject constructor(
    private val gearRepository: GearRepository
) {
    suspend operator fun invoke(): List<GearItem> {
        return gearRepository.getGear()
    }
}