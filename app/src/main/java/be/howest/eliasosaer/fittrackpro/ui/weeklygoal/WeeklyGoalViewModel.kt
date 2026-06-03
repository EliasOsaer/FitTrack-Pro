package be.howest.eliasosaer.fittrackpro.ui.weeklygoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.model.GoalType
import be.howest.eliasosaer.fittrackpro.domain.model.WeeklyGoal
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetWeeklyGoalUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetWeeklyGoalProgressUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.SaveWeeklyGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyGoalViewModel @Inject constructor(
    private val getWeeklyGoal: GetWeeklyGoalUseCase,
    private val getWeeklyGoalProgress: GetWeeklyGoalProgressUseCase,
    private val saveWeeklyGoal: SaveWeeklyGoalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeeklyGoalUiState())
    val uiState: StateFlow<WeeklyGoalUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getWeeklyGoal().collect { goal ->
                _uiState.update { state ->
                    if (goal == null) {
                        state.copy(
                            currentGoalType = GoalType.ACTIVITY,
                            currentGoalValueInput = ""
                        )
                    } else {
                        state.copy(
                            currentGoalType = goal.goalType,
                            currentGoalValueInput = goal.goalValue.toString()
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            getWeeklyGoalProgress().collect { progress ->
                _uiState.update {
                    it.copy(weeklyGoalProgress = progress)
                }
            }
        }
    }

    fun onGoalTypeSelected(goalType: GoalType) {
        _uiState.update {
            it.copy(
                currentGoalType = goalType,
                errorMessage = null
            )
        }
    }

    fun onGoalValueChanged(value: String) {
        val filteredValue = value
            .filter { it.isDigit() || it == '.' }
            .let { input ->
                val firstDotIndex = input.indexOf('.')
                if (firstDotIndex == -1) input
                else {
                    input.take(firstDotIndex + 1) +
                            input.drop(firstDotIndex + 1).replace(".", "")
                }
            }

        _uiState.update {
            it.copy(
                currentGoalValueInput = filteredValue,
                errorMessage = null
            )
        }
    }

    fun onSaveClick() {
        val currentState = _uiState.value
        val parsedValue = currentState.currentGoalValueInput.toFloatOrNull()

        if (parsedValue == null || parsedValue <= 0f) {
            _uiState.update {
                it.copy(errorMessage = "Please enter a valid goal.")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }
            try {
                saveWeeklyGoal(
                    WeeklyGoal(
                        goalType = currentState.currentGoalType,
                        goalValue = parsedValue
                    )
                )
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Unable to save weekly goal.",
                        isSaving = false
                    )
                }
                return@launch
            } finally {
                _uiState.update {
                    it.copy(isSaving = false)
                }
            }
        }
    }
}
