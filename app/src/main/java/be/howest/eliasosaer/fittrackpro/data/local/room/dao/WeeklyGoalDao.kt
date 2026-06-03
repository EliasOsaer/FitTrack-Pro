package be.howest.eliasosaer.fittrackpro.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WeeklyGoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeeklyGoalDao {

    @Query("SELECT * FROM weekly_goal WHERE id = 1 LIMIT 1")
    fun observeWeeklyGoal(): Flow<WeeklyGoalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeeklyGoal(goal: WeeklyGoalEntity)
}
