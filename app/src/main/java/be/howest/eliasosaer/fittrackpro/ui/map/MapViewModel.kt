package be.howest.eliasosaer.fittrackpro.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutZone
import be.howest.eliasosaer.fittrackpro.domain.repository.LocationRepository
import com.mapbox.geojson.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        setDefaultZones()
        observeLocation()
    }

    private fun setDefaultZones() {
        _uiState.update {
            it.copy(
                gymZone = WorkoutZone(
                    id = "gym_zone",
                    title = "Gym zone",
                    location = Point.fromLngLat(4.4044, 50.8476)
                ),
                runZone = WorkoutZone(
                    id = "run_zone",
                    title = "Run zone",
                    location = Point.fromLngLat(4.3921, 50.8460)
                )
            )
        }
    }

    private fun observeLocation() {
        viewModelScope.launch {
            locationRepository.location.collect { point ->
                _uiState.update {
                    it.copy(userLocation = point)
                }
            }
        }
    }

    fun attachMapView(mapView: com.mapbox.maps.MapView) {
        locationRepository.attachMapView(mapView)
    }

    fun detachMapView() {
        locationRepository.detachMapView()
    }
}