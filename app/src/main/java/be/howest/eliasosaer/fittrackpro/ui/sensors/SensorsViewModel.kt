package be.howest.eliasosaer.fittrackpro.ui.sensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.repository.FlashlightRepository
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
    private val movementSensorRepository: MovementSensorRepository,
    private val flashlightRepository: FlashlightRepository
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
                updateFlashlightState()
            }
        }
    }

    private fun observeMovement() {
        viewModelScope.launch {
            movementSensorRepository.movementLabel.collect { movement ->
                _uiState.update { currentState ->
                    currentState.copy(movementLabel = movement)
                }
                updateFlashlightState()
            }
        }
    }

    fun onScreenVisible() {
        lightSensorRepository.startListening()
        movementSensorRepository.startListening()
    }

    fun onScreenHidden() {
        flashlightRepository.setFlashlight(false)
        lightSensorRepository.stopListening()
        movementSensorRepository.stopListening()

        _uiState.update {
            it.copy(isFlashlightOn = false)
        }
    }

    private fun updateFlashlightState() {
        val state = _uiState.value

        val shouldEnable =
            state.lightIntensity < 50f && state.movementLabel.equals("Running", ignoreCase = true)

        flashlightRepository.setFlashlight(shouldEnable)

        _uiState.update {
            it.copy(isFlashlightOn = shouldEnable)
        }
    }
}