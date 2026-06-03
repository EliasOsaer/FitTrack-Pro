package be.howest.eliasosaer.fittrackpro.ui.editworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.model.CardioDetails
import be.howest.eliasosaer.fittrackpro.domain.model.StrengthDetails
import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetWorkoutByIdUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.SaveWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditWorkoutViewModel @Inject constructor(
    private val getWorkoutById: GetWorkoutByIdUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditWorkoutUiState())
    val uiState: StateFlow<EditWorkoutUiState> = _uiState

    private var loadedWorkout: Workout? = null

    fun loadWorkout(workoutId: Long) {
        viewModelScope.launch {
            val workout = getWorkoutById(workoutId).firstOrNull() ?: return@launch
            loadedWorkout = workout

            _uiState.value = when (workout.type) {
                WorkoutType.CARDIO -> {
                    val details = workout.details as CardioDetails
                    EditWorkoutUiState(
                        workoutId = workout.id,
                        workoutType = workout.type,
                        formattedDateTime = workout.formattedDateTime,
                        durationMinutes = workout.durationMinutes,
                        title = workout.title,
                        distanceKm = details.distanceKm?.toString() ?: "",
                        calories = details.calories?.toString() ?: "",
                        rating = workout.rating?.toString() ?: "",
                        notes = workout.notes ?: ""
                    )
                }

                WorkoutType.STRENGTH -> {
                    val details = workout.details as StrengthDetails
                    EditWorkoutUiState(
                        workoutId = workout.id,
                        workoutType = workout.type,
                        formattedDateTime = workout.formattedDateTime,
                        durationMinutes = workout.durationMinutes,
                        title = workout.title,
                        calories = details.calories?.toString() ?: "",
                        setsOrReps = details.setsOrReps ?: "",
                        muscleGroups = details.muscleGroups ?: "",
                        rating = workout.rating?.toString() ?: "",
                        notes = workout.notes ?: ""
                    )
                }
            }
        }
    }

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value, titleError = false) }
    }

    fun onDistanceChange(value: String) {
        _uiState.update { it.copy(distanceKm = value, distanceError = false) }
    }

    fun onCaloriesChange(value: String) {
        _uiState.update { it.copy(calories = value, caloriesError = false) }
    }

    fun onSetsOrRepsChange(value: String) {
        _uiState.update { it.copy(setsOrReps = value) }
    }

    fun onMuscleGroupsChange(value: String) {
        _uiState.update { it.copy(muscleGroups = value) }
    }

    fun onRatingChange(value: String) {
        _uiState.update { it.copy(rating = value, ratingError = false) }
    }

    fun onNotesChange(value: String) {
        _uiState.update { it.copy(notes = value) }
    }

    fun onSaveClick() {
        val state = _uiState.value
        val originalWorkout = loadedWorkout ?: return

        val hasError = validate(state)
        if (hasError) return

        viewModelScope.launch {
            val updatedWorkout = when (originalWorkout.type) {
                WorkoutType.CARDIO -> {
                    originalWorkout.copy(
                        title = state.title,
                        details = CardioDetails(
                            distanceKm = state.distanceKm.toDoubleOrNull(),
                            calories = state.calories.toIntOrNull()
                        ),
                        rating = state.rating.toIntOrNull(),
                        notes = state.notes.ifBlank { null }
                    )
                }

                WorkoutType.STRENGTH -> {
                    originalWorkout.copy(
                        title = state.title,
                        details = StrengthDetails(
                            setsOrReps = state.setsOrReps.ifBlank { null },
                            muscleGroups = state.muscleGroups.ifBlank { null },
                            calories = state.calories.toIntOrNull()
                        ),
                        rating = state.rating.toIntOrNull(),
                        notes = state.notes.ifBlank { null }
                    )
                }
            }

            saveWorkoutUseCase(updatedWorkout)

            _uiState.update {
                it.copy(saved = true)
            }
        }
    }

    private fun validate(state: EditWorkoutUiState): Boolean {
        val titleError = state.title.isBlank()
        val distanceError = state.workoutType == WorkoutType.CARDIO &&
                state.distanceKm.isNotBlank() &&
                state.distanceKm.toDoubleOrNull() == null
        val caloriesError = state.calories.isNotBlank() &&
                state.calories.toIntOrNull() == null
        val ratingValue = state.rating.toIntOrNull()
        val ratingError = state.rating.isNotBlank() &&
                (ratingValue == null || ratingValue !in 0..5)

        _uiState.update {
            it.copy(
                titleError = titleError,
                distanceError = distanceError,
                caloriesError = caloriesError,
                ratingError = ratingError
            )
        }

        return titleError || distanceError || caloriesError || ratingError
    }
}