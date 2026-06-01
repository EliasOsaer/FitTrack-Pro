package be.howest.eliasosaer.fittrackpro.data.repository

import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WorkoutDao
import be.howest.eliasosaer.fittrackpro.data.local.room.mapper.toDomain
import be.howest.eliasosaer.fittrackpro.data.local.room.mapper.toEntity
import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {

    override fun getWorkouts(): Flow<List<Workout>> {
        return workoutDao.getWorkouts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getRecentWorkouts(): Flow<List<Workout>> {
        return workoutDao.getRecentWorkouts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getWorkoutsBetween(start: Long, end: Long): Flow<List<Workout>> {
        return workoutDao.getWorkoutsBetween(start, end).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getWorkoutById(workoutId: Long): Flow<Workout> {
        return workoutDao.getWorkoutById(workoutId).map { workout ->
            workout.toDomain()
        }
    }

    override suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout.toEntity())
    }
}