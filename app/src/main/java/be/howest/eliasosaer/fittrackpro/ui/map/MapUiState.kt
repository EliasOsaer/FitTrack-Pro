package be.howest.eliasosaer.fittrackpro.ui.map

import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutZone
import com.mapbox.geojson.Point

data class MapUiState(
    val userLocation: Point? = null,
    val gymZone: WorkoutZone? = null,
    val runZone: WorkoutZone? = null
)