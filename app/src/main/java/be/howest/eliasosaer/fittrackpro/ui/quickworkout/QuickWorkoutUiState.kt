package be.howest.eliasosaer.fittrackpro.ui.quickworkout

import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType

data class QuickWorkoutUiState(
    val title: String = "",
    val selectedWorkoutType: WorkoutType = WorkoutType.STRENGTH,
    val startTimeMillis: Long = 0L
)