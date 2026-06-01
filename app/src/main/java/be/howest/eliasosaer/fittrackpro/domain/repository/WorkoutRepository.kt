package be.howest.eliasosaer.fittrackpro.domain.repository

import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getWorkouts(): Flow<List<Workout>>
    fun getRecentWorkouts(): Flow<List<Workout>>
    fun getWorkoutsBetween(start: Long, end: Long): Flow<List<Workout>>
    fun getWorkoutById(workoutId: Long): Flow<Workout>
    suspend fun insertWorkout(workout: Workout)
}