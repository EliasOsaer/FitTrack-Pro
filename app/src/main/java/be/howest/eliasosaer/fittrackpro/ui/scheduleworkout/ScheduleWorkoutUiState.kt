package be.howest.eliasosaer.fittrackpro.ui.scheduleworkout

import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType

data class ScheduleWorkoutUiState(
    val title: String = "",
    val selectedWorkoutType: WorkoutType? = null,
    val date: String = "",
    val time: String = "",
    val durationMinutes: String = "",

    val showWorkoutTypeMenu: Boolean = false,
    val showDatePicker: Boolean = false,

    val titleError: Boolean = false,
    val workoutTypeError: Boolean = false,
    val dateError: Boolean = false,
    val timeError: Boolean = false,
    val durationError: Boolean = false,

    val showQrScanner: Boolean = false,
    val isSaving: Boolean = false,
    val saved: Boolean = false
)