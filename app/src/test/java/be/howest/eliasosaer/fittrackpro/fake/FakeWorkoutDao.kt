package be.howest.eliasosaer.fittrackpro.fake

import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WorkoutDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeWorkoutDao : WorkoutDao {

    private val workouts = MutableStateFlow<List<WorkoutEntity>>(emptyList())

    override fun getWorkouts(): Flow<List<WorkoutEntity>> {
        return workouts
    }

    override fun getRecentWorkouts(): Flow<List<WorkoutEntity>> {
        return workouts.map { list ->
            list.filter { !it.isUpcoming }
                .sortedByDescending { it.dateTime }
                .take(3)
        }
    }

    override fun getWorkoutsBetween(startOfDay: Long, endOfDay: Long): Flow<List<WorkoutEntity>> {
        return workouts.map { list ->
            list.filter { it.dateTime in startOfDay..endOfDay }
        }
    }

    override fun getWorkoutById(workoutId: Long): Flow<WorkoutEntity> {
        return workouts.map { list ->
            list.first { it.id == workoutId }
        }
    }

    override suspend fun insertWorkout(workout: WorkoutEntity) {
        val current = workouts.value.toMutableList()
        current.removeAll { it.id == workout.id }
        current.add(workout)
        workouts.value = current
    }
}