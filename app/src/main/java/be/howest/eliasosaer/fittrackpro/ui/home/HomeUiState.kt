package be.howest.eliasosaer.fittrackpro.ui.home

import be.howest.eliasosaer.fittrackpro.domain.model.SummaryStat
import be.howest.eliasosaer.fittrackpro.domain.model.Workout

data class HomeUiState(
    val summaryStats: List<SummaryStat> = emptyList(),
    val weeklyGoalProgress: Float = 0f,
    val recentWorkouts: List<Workout> = emptyList()
)