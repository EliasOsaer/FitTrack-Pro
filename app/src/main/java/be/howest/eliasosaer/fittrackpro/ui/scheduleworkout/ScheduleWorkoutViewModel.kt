package be.howest.eliasosaer.fittrackpro.ui.scheduleworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType
import be.howest.eliasosaer.fittrackpro.domain.usecase.SaveWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleWorkoutViewModel @Inject constructor(
    private val createWorkout: SaveWorkoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleWorkoutUiState())
    val uiState: StateFlow<ScheduleWorkoutUiState> = _uiState

    private val _events = MutableSharedFlow<ScheduleWorkoutEvent>()
    val events = _events.asSharedFlow()

    fun onTitleChange(value: String) {
        _uiState.update {
            it.copy(title = value, titleError = false)
        }
    }

    fun onWorkoutTypeMenuExpandedChange(expanded: Boolean) {
        _uiState.update {
            it.copy(showWorkoutTypeMenu = expanded)
        }
    }

    fun onWorkoutTypeSelected(type: WorkoutType) {
        _uiState.update {
            it.copy(
                selectedWorkoutType = type,
                workoutTypeError = false,
                showWorkoutTypeMenu = false
            )
        }
    }

    fun onDateClick() {
        _uiState.update {
            it.copy(showDatePicker = true, dateError = false)
        }
    }

    fun onDismissDatePicker() {
        _uiState.update {
            it.copy(showDatePicker = false)
        }
    }

    fun onDateSelected(date: String) {
        _uiState.update {
            it.copy(
                date = date,
                dateError = false,
                showDatePicker = false
            )
        }
    }

    fun onTimeChange(value: String) {
        _uiState.update {
            it.copy(time = value, timeError = false)
        }
    }

    fun onDurationChange(value: String) {
        val filtered = value.filter { char -> char.isDigit() }
        _uiState.update {
            it.copy(durationMinutes = filtered, durationError = false)
        }
    }

    fun onSaveClick() {
        val state = _uiState.value
        if (!validate(state)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            try {
                createWorkout(
                    title = state.title.trim(),
                    type = state.selectedWorkoutType!!,
                    date = state.date,
                    time = state.time.trim(),
                    durationMinutes = state.durationMinutes.toInt()
                )

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        saved = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isSaving = false)
                }
            }
        }
    }

    fun onSavedHandled() {
        _uiState.update {
            it.copy(saved = false)
        }
    }

    fun onImportQrClick() {
        viewModelScope.launch {
            _events.emit(ScheduleWorkoutEvent.OpenCamera)
        }
    }

    private fun validate(state: ScheduleWorkoutUiState): Boolean {
        val titleError = state.title.isBlank()
        val workoutTypeError = state.selectedWorkoutType == null
        val dateError = state.date.isBlank()
        val timeError = !isValidTime(state.time)
        val durationError = state.durationMinutes.toIntOrNull() == null || state.durationMinutes.toInt() <= 0

        _uiState.update {
            it.copy(
                titleError = titleError,
                workoutTypeError = workoutTypeError,
                dateError = dateError,
                timeError = timeError,
                durationError = durationError
            )
        }

        return !(titleError || workoutTypeError || dateError || timeError || durationError)
    }

    private fun isValidTime(value: String): Boolean {
        val regex = Regex("^([01]\\d|2[0-3]):([0-5]\\d)$")
        return regex.matches(value)
    }
}