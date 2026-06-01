package be.howest.eliasosaer.fittrackpro.data.local.room.mapper

import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WorkoutEntity
import be.howest.eliasosaer.fittrackpro.domain.model.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun WorkoutEntity.toDomain(): Workout {

    val details: WorkoutDetails = when (workoutType) {
        WorkoutType.CARDIO -> CardioDetails(
            distanceKm = distanceKm,
            calories = calories
        )

        WorkoutType.STRENGTH -> StrengthDetails(
            setsOrReps = setsOrReps,
            muscleGroups = muscleGroups,
            calories = calories
        )
    }

    return Workout(
        id = id,
        title = title,
        dateTime = dateTime,
        formattedDateTime = formatDateTime(dateTime),
        durationMinutes = durationMinutes,
        isUpcoming = isUpcoming,
        type = workoutType,
        details = details,
        rating = rating,
        notes = notes
    )
}

fun Workout.toEntity(): WorkoutEntity {

    return when (val d = details) {

        is CardioDetails -> WorkoutEntity(
            id = id,
            title = title,
            dateTime = dateTime,
            workoutType = WorkoutType.CARDIO,
            durationMinutes = durationMinutes,
            isUpcoming = isUpcoming,

            distanceKm = d.distanceKm,
            calories = d.calories,

            setsOrReps = null,
            muscleGroups = null,

            rating = rating,
            notes = notes
        )

        is StrengthDetails -> WorkoutEntity(
            id = id,
            title = title,
            dateTime = dateTime,
            workoutType = WorkoutType.STRENGTH,
            durationMinutes = durationMinutes,
            isUpcoming = isUpcoming,

            distanceKm = null,
            calories = d.calories,

            setsOrReps = d.setsOrReps,
            muscleGroups = d.muscleGroups,

            rating = rating,
            notes = notes
        )
    }
}

private fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}