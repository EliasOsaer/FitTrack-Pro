package be.howest.eliasosaer.fittrackpro.ui.workoutdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetWorkoutByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val getWorkoutById: GetWorkoutByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutDetailUiState())
    val uiState: StateFlow<WorkoutDetailUiState> = _uiState

    fun loadWorkout(workoutId: Long) {
        viewModelScope.launch {
            getWorkoutById(workoutId).collect { workout ->
                _uiState.update {
                    it.copy(workout = workout)
                }
            }
        }
    }
}