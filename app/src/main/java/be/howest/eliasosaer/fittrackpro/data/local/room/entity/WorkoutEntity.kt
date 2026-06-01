package be.howest.eliasosaer.fittrackpro.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val dateTime: Long,
    val durationMinutes: Int,
    val isUpcoming: Boolean,
    val workoutType: WorkoutType,

    val distanceKm: Double?,
    val setsOrReps: String?,
    val calories: Int?,
    val muscleGroups: String?,
    val rating: Int?,
    val notes: String?
)