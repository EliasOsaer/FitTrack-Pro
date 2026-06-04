package be.howest.eliasosaer.fittrackpro.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@Composable
fun MapScreen(
    uiState: MapUiState,
    onRequestLocationPermission: () -> Unit,
    onMapReady: (com.mapbox.maps.MapView) -> Unit,
    onMapDisposed: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onRequestLocationPermission()
    }

    DisposableEffect(Unit) {
        onDispose {
            onMapDisposed()
        }
    }

    val centerPoint = uiState.userLocation ?: Point.fromLngLat(4.4044, 50.8476)

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(14.0)
            center(centerPoint)
            pitch(0.0)
            bearing(0.0)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = mapViewportState
        ) {
            MapEffect(Unit) { mapView ->
                onMapReady(mapView)
            }

            MapEffect(centerPoint) { mapView ->
                mapView.getMapboxMap().setCamera(
                    CameraOptions.Builder()
                        .center(centerPoint)
                        .zoom(14.0)
                        .pitch(0.0)
                        .bearing(0.0)
                        .build()
                )
            }
        }
    }
}