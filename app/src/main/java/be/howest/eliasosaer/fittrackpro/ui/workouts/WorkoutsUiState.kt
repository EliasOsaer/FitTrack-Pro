package be.howest.eliasosaer.fittrackpro.ui.workouts

import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutFilter
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutDialog

data class WorkoutsUiState(
    val selectedFilter: WorkoutFilter = WorkoutFilter.UPCOMING,
    val allWorkouts: List<Workout> = emptyList(),
    val workouts: List<Workout> = emptyList(),
    val dialog: WorkoutDialog = WorkoutDialog.None
)