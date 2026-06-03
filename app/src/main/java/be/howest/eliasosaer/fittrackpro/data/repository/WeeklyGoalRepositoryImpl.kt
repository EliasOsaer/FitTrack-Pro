package be.howest.eliasosaer.fittrackpro.data.repository

import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WeeklyGoalDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WeeklyGoalEntity
import be.howest.eliasosaer.fittrackpro.domain.model.WeeklyGoal
import be.howest.eliasosaer.fittrackpro.domain.repository.WeeklyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeeklyGoalRepositoryImpl @Inject constructor(
    private val weeklyGoalDao: WeeklyGoalDao
) : WeeklyGoalRepository {

    override fun observeWeeklyGoal(): Flow<WeeklyGoal?> {
        return weeklyGoalDao.observeWeeklyGoal().map { entity ->
            entity?.let { e ->
                WeeklyGoal(
                    goalType = e.goalType,
                    goalValue = e.goalValue
                )
            }
        }
    }

    override suspend fun saveWeeklyGoal(goal: WeeklyGoal) {
        weeklyGoalDao.saveWeeklyGoal(
            WeeklyGoalEntity(
                id = 1L,
                goalType = goal.goalType,
                goalValue = goal.goalValue
            )
        )
    }
}
