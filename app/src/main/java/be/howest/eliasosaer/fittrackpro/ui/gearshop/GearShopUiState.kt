package be.howest.eliasosaer.fittrackpro.ui.gearshop

import be.howest.eliasosaer.fittrackpro.domain.model.GearItem
import be.howest.eliasosaer.fittrackpro.domain.model.PriceSortOrder

data class GearShopUiState(
    val searchQuery: String = "",
    val sortOrder: PriceSortOrder = PriceSortOrder.ASCENDING,
    val gearItems: List<GearItem> = emptyList(),
    val cartItemCount: Int = 0
)
