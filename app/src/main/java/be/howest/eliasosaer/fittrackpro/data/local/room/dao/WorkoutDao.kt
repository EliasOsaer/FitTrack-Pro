package be.howest.eliasosaer.fittrackpro.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts")
    fun getWorkouts(): Flow<List<WorkoutEntity>>

    @Query("""
    SELECT * FROM workouts
    WHERE isUpcoming = 0
    ORDER BY dateTime DESC
    LIMIT 3
""")
    fun getRecentWorkouts(): Flow<List<WorkoutEntity>>

    @Query("""
    SELECT * FROM workouts
    WHERE dateTime BETWEEN :startOfDay AND :endOfDay
""")
    fun getWorkoutsBetween(
        startOfDay: Long,
        endOfDay: Long
    ): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    fun getWorkoutById(workoutId: Long): Flow<WorkoutEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)
}