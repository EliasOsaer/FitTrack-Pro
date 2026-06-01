package be.howest.eliasosaer.fittrackpro.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.model.GearItem
import be.howest.eliasosaer.fittrackpro.domain.model.PriceSortOrder
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetGearItemsUseCase
import be.howest.eliasosaer.fittrackpro.ui.gearshop.GearShopUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GearShopViewModel @Inject constructor(
    private val getGearItems: GetGearItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GearShopUiState())
    val uiState: StateFlow<GearShopUiState> = _uiState

    private var allGearItems: List<GearItem> = emptyList()

    init {
        viewModelScope.launch {
            val gear = getGearItems()
            allGearItems = gear
            _uiState.update {
                it.copy(
                    gearItems = getVisibleGearItems(
                        items = allGearItems,
                        query = it.searchQuery,
                        sortOrder = it.sortOrder
                    )
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                gearItems = getVisibleGearItems(
                    items = allGearItems,
                    query = query,
                    sortOrder = currentState.sortOrder
                )
            )
        }
    }

    fun onToggleSortOrder() {
        _uiState.update { currentState ->
            val newSortOrder = when (currentState.sortOrder) {
                PriceSortOrder.ASCENDING -> PriceSortOrder.DESCENDING
                PriceSortOrder.DESCENDING -> PriceSortOrder.ASCENDING
            }

            currentState.copy(
                sortOrder = newSortOrder,
                gearItems = getVisibleGearItems(
                    items = allGearItems,
                    query = currentState.searchQuery,
                    sortOrder = newSortOrder
                )
            )
        }
    }

    fun onAddToCart(item: GearItem) {
        _uiState.update { currentState ->
            currentState.copy(
                cartItemCount = currentState.cartItemCount + 1
            )
        }
    }

    private fun getVisibleGearItems(
        items: List<GearItem>,
        query: String,
        sortOrder: PriceSortOrder
    ): List<GearItem> {
        val filteredItems = if (query.isBlank()) {
            items
        } else {
            items.filter { gearItem ->
                gearItem.name.contains(query.trim(), ignoreCase = true)
            }
        }

        return when (sortOrder) {
            PriceSortOrder.ASCENDING -> filteredItems.sortedBy { it.price }
            PriceSortOrder.DESCENDING -> filteredItems.sortedByDescending { it.price }
        }
    }
}
