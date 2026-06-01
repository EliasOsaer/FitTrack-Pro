package be.howest.eliasosaer.fittrackpro.domain.model

data class Workout(
    val id: Long,
    val title: String,
    val dateTime: Long,
    val formattedDateTime: String,
    val durationMinutes: Int,
    val isUpcoming: Boolean,
    val type: WorkoutType,
    val details: WorkoutDetails,
    val rating: Int? = null,
    val notes: String? = null
)