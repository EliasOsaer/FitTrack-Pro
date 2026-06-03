package be.howest.eliasosaer.fittrackpro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetRecentWorkoutsUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetTodaySummaryUseCase
import be.howest.eliasosaer.fittrackpro.domain.usecase.GetWeeklyGoalProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSummaryStats: GetTodaySummaryUseCase,
    private val getRecentWorkouts: GetRecentWorkoutsUseCase,
    private val getWeeklyGoalProgress: GetWeeklyGoalProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            getSummaryStats().collect { stats ->
                _uiState.update {
                    it.copy(summaryStats = stats)
                }
            }
        }

        viewModelScope.launch {
            getRecentWorkouts().collect { workouts ->
                _uiState.update {
                    it.copy(recentWorkouts = workouts)
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
}
