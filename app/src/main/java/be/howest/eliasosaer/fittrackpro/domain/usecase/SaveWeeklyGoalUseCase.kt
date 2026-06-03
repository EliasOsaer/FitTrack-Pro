package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.WeeklyGoal
import be.howest.eliasosaer.fittrackpro.domain.repository.WeeklyGoalRepository
import javax.inject.Inject

class SaveWeeklyGoalUseCase @Inject constructor(
    private val repository: WeeklyGoalRepository
) {
    suspend operator fun invoke(goal: WeeklyGoal) {
        repository.saveWeeklyGoal(goal)
    }
}
