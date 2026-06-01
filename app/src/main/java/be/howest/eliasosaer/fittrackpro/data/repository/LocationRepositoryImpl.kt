package be.howest.eliasosaer.fittrackpro.data.repository

import android.content.Context
import be.howest.eliasosaer.fittrackpro.domain.repository.LocationRepository
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationRepository {

    private val _location = MutableStateFlow<Point?>(null)
    override val location: Flow<Point> = _location.filterNotNull()

    private var listener: OnIndicatorPositionChangedListener? = null
    private var mapView: MapView? = null

    override fun attachMapView(mapView: MapView) {
        this.mapView = mapView

        mapView.location.updateSettings {
            enabled = true
            locationPuck = createDefault2DPuck()
        }

        listener = OnIndicatorPositionChangedListener { point ->
            _location.value = point
        }

        listener?.let {
            mapView.location.addOnIndicatorPositionChangedListener(it)
        }
    }

    override fun detachMapView() {
        mapView?.let { currentMapView ->
            listener?.let { currentListener ->
                currentMapView.location.removeOnIndicatorPositionChangedListener(currentListener)
            }

            currentMapView.location.updateSettings {
                enabled = false
            }
        }

        listener = null
        mapView = null
    }
}