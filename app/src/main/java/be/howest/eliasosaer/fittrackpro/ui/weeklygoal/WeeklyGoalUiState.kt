package be.howest.eliasosaer.fittrackpro.ui.weeklygoal

import be.howest.eliasosaer.fittrackpro.domain.model.GoalType

data class WeeklyGoalUiState(
    val currentGoalType: GoalType = GoalType.ACTIVITY,
    val currentGoalValueInput: String = "",
    val weeklyGoalProgress: Float = 0f,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)
