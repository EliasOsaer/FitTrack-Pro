package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.WeeklyGoal
import be.howest.eliasosaer.fittrackpro.domain.repository.WeeklyGoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeeklyGoalUseCase @Inject constructor(
    private val repository: WeeklyGoalRepository
) {
    operator fun invoke(): Flow<WeeklyGoal?> {
        return repository.observeWeeklyGoal()
    }
}
