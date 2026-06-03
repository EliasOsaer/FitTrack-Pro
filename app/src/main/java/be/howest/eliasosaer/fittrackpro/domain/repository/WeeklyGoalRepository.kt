package be.howest.eliasosaer.fittrackpro.domain.repository

import be.howest.eliasosaer.fittrackpro.domain.model.WeeklyGoal
import kotlinx.coroutines.flow.Flow

interface WeeklyGoalRepository {
    fun observeWeeklyGoal(): Flow<WeeklyGoal?>
    suspend fun saveWeeklyGoal(goal: WeeklyGoal)
}
