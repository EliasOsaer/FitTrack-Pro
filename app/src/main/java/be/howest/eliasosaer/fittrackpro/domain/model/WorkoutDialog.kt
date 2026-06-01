package be.howest.eliasosaer.fittrackpro.domain.model

sealed interface WorkoutDialog {

    data object None : WorkoutDialog

    data class Reminder(
        val workoutId: Long
    ) : WorkoutDialog

    data class Qr(
        val workoutId: Long
    ) : WorkoutDialog
}