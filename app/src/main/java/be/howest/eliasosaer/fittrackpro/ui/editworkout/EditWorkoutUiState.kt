package be.howest.eliasosaer.fittrackpro.ui.editworkout

import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType

data class EditWorkoutUiState(
    val workoutId: Long? = null,
    val workoutType: WorkoutType? = null,
    val formattedDateTime: String = "",
    val durationMinutes: Int = 0,

    val title: String = "",
    val distanceKm: String = "",
    val calories: String = "",
    val setsOrReps: String = "",
    val muscleGroups: String = "",
    val rating: String = "",
    val notes: String = "",

    val titleError: Boolean = false,
    val distanceError: Boolean = false,
    val caloriesError: Boolean = false,
    val ratingError: Boolean = false,

    val saved: Boolean = false
)