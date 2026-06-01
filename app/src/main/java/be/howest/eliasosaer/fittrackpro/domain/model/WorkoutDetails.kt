package be.howest.eliasosaer.fittrackpro.domain.model

sealed class WorkoutDetails {
}

data class CardioDetails(
    val distanceKm: Double?,
    val calories: Int?
) : WorkoutDetails()

data class StrengthDetails(
    val setsOrReps: String?,
    val muscleGroups: String?,
    val calories: Int?
) : WorkoutDetails()