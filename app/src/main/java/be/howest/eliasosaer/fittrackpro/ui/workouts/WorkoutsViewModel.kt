package be.howest.eliasosaer.fittrackpro.ui.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutDialog
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutFilter
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    private val getWorkouts: GetWorkoutsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutsUiState())
    val uiState: StateFlow<WorkoutsUiState> = _uiState

    init {
        viewModelScope.launch {
            getWorkouts().collect { workouts ->
                _uiState.update { state ->
                    val filtered = filterWorkouts(
                        workouts,
                        state.selectedFilter
                    )

                    state.copy(
                        allWorkouts = workouts,
                        workouts = filtered
                    )
                }
            }
        }
    }

    fun onFilterSelected(filter: WorkoutFilter) {
        _uiState.update { state ->
            state.copy(
                selectedFilter = filter,
                workouts = filterWorkouts(state.allWorkouts, filter)
            )
        }
    }

    fun onReminderClick(workout: Workout) {
        _uiState.update {
            it.copy(dialog = WorkoutDialog.Reminder(workout.id))
        }
    }

    fun onQrClick(workout: Workout) {
        _uiState.update {
            it.copy(dialog = WorkoutDialog.Qr(workout.id))
        }
    }

    fun onDismissDialog() {
        _uiState.update {
            it.copy(dialog = WorkoutDialog.None)
        }
    }

    private fun filterWorkouts(
        workouts: List<Workout>,
        filter: WorkoutFilter
    ): List<Workout> {
        return when (filter) {
            WorkoutFilter.UPCOMING -> workouts.filter { it.isUpcoming }
            WorkoutFilter.HISTORY -> workouts.filter { !it.isUpcoming }
        }
    }
}