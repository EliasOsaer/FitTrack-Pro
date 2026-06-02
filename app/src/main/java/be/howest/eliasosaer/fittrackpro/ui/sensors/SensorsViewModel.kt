package be.howest.eliasosaer.fittrackpro.ui.sensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.repository.LightSensorRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.MovementSensorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SensorsViewModel @Inject constructor(
    private val lightSensorRepository: LightSensorRepository,
    private val movementSensorRepository: MovementSensorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SensorsUiState(
            isLightSensorAvailable = lightSensorRepository.isSensorAvailable(),
            isMovementSensorAvailable = movementSensorRepository.isSensorAvailable()
        )
    )
    val uiState: StateFlow<SensorsUiState> = _uiState.asStateFlow()

    init {
        observeLightIntensity()
        observeMovement()
    }

    private fun observeLightIntensity() {
        viewModelScope.launch {
            lightSensorRepository.lightIntensity.collect { intensity ->
                _uiState.update { currentState ->
                    currentState.copy(lightIntensity = intensity)
                }
            }
        }
    }

    private fun observeMovement() {
        viewModelScope.launch {
            movementSensorRepository.movementLabel.collect { movement ->
                _uiState.update { currentState ->
                    currentState.copy(movementLabel = movement)
                }
            }
        }
    }

    fun onScreenVisible() {
        lightSensorRepository.startListening()
        movementSensorRepository.startListening()
    }

    fun onScreenHidden() {
        lightSensorRepository.stopListening()
        movementSensorRepository.stopListening()
    }
}