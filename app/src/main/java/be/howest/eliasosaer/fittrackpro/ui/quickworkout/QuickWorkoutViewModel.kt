package be.howest.eliasosaer.fittrackpro.ui.quickworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType
import be.howest.eliasosaer.fittrackpro.domain.usecase.SaveWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class QuickWorkoutViewModel @Inject constructor(
    private val saveWorkoutUseCase: SaveWorkoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuickWorkoutUiState(
            startTimeMillis = System.currentTimeMillis()
        )
    )
    val uiState: StateFlow<QuickWorkoutUiState> = _uiState

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun onWorkoutTypeSelected(type: WorkoutType) {
        _uiState.update { it.copy(selectedWorkoutType = type) }
    }

    fun onWorkoutStarted() {
        _uiState.update {
            it.copy(startTimeMillis = System.currentTimeMillis())
        }
    }

    fun onEndWorkoutClick(
        elapsedSeconds: Long,
        navigateBack: () -> Unit
    ) {
        val state = _uiState.value
        val startTimeMillis = if (state.startTimeMillis == 0L) {
            System.currentTimeMillis()
        } else {
            state.startTimeMillis
        }

        val titleToSave = state.title.ifBlank { "Quick Workout" }
        val durationMinutes = max(1, (elapsedSeconds / 60).toInt())

        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startDate = Date(startTimeMillis)

        viewModelScope.launch {
            saveWorkoutUseCase(
                title = titleToSave,
                type = state.selectedWorkoutType,
                date = dateFormatter.format(startDate),
                time = timeFormatter.format(startDate),
                durationMinutes = durationMinutes
            )

            _uiState.update {
                it.copy(
                    title = "",
                    startTimeMillis = System.currentTimeMillis()
                )
            }

            navigateBack()
        }
    }
}
