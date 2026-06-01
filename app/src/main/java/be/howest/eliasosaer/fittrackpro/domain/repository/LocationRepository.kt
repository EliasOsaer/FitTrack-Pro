package be.howest.eliasosaer.fittrackpro.domain.repository

import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val location: Flow<Point>
    fun attachMapView(mapView: MapView)
    fun detachMapView()
}