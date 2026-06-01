package be.howest.eliasosaer.fittrackpro.domain.model

import com.mapbox.geojson.Point

data class WorkoutZone(
    val id: String,
    val title: String,
    val location: Point
)