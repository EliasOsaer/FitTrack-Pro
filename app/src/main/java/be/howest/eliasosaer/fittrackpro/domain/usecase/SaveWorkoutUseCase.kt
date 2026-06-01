package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.CardioDetails
import be.howest.eliasosaer.fittrackpro.domain.model.StrengthDetails
import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class SaveWorkoutUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(
        title: String,
        type: WorkoutType,
        date: String,
        time: String,
        durationMinutes: Int
    ) {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val parsedDate = formatter.parse("$date $time")
        val dateTimeMillis = parsedDate?.time ?: System.currentTimeMillis()

        val workout = Workout(
            id = System.currentTimeMillis(),
            title = title,
            dateTime = dateTimeMillis,
            formattedDateTime = "",
            durationMinutes = durationMinutes,
            isUpcoming = dateTimeMillis > System.currentTimeMillis(),
            type = type,
            details = when (type) {
                WorkoutType.CARDIO -> CardioDetails(
                    distanceKm = null,
                    calories = null
                )
                WorkoutType.STRENGTH -> StrengthDetails(
                    setsOrReps = null,
                    muscleGroups = null,
                    calories = null
                )
            },
            rating = null,
            notes = null
        )

        repository.insertWorkout(workout)
    }

    suspend operator fun invoke(workout: Workout) {
        repository.insertWorkout(workout)
    }
}