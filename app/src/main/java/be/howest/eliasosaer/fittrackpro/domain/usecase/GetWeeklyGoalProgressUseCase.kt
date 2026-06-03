package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.CardioDetails
import be.howest.eliasosaer.fittrackpro.domain.model.StrengthDetails
import be.howest.eliasosaer.fittrackpro.domain.model.GoalType
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.WeeklyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class GetWeeklyGoalProgressUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val weeklyGoalRepository: WeeklyGoalRepository
) {
    operator fun invoke(): Flow<Float> {
        val startOfWeek = getStartOfWeekMillis()
        val endOfWeek = getEndOfWeekMillis()

        return combine( //calculating the progress depends on both flows so combine makes sure that if either of the flows change that progress is recalculated
            weeklyGoalRepository.observeWeeklyGoal(),
            workoutRepository.getWorkoutsBetween(startOfWeek, endOfWeek)
        ) { weeklyGoal, workouts ->
            if (weeklyGoal == null || weeklyGoal.goalValue <= 0f) {
                return@combine 0f
            }

            val completed = when (weeklyGoal.goalType) {
                GoalType.ACTIVITY -> workouts
                    .filterNot { it.isUpcoming }
                    .sumOf { it.durationMinutes }
                    .toFloat()

                GoalType.CALORIES -> workouts
                    .filterNot { it.isUpcoming }
                    .sumOf { workout ->
                        when (val details = workout.details) {
                            is CardioDetails -> details.calories ?: 0
                            is StrengthDetails -> details.calories ?: 0
                        }
                    }
                    .toFloat()

                GoalType.DISTANCE -> workouts
                    .filterNot { it.isUpcoming }
                    .sumOf { workout ->
                        when (val details = workout.details) {
                            is CardioDetails -> details.distanceKm ?: 0.0
                            is StrengthDetails -> 0.0
                        }
                    }
                    .toFloat()
            }

            (completed / weeklyGoal.goalValue).coerceIn(0f, 1f) //makes sure values stay between 0 and 100%
        }
    }
}

private fun getStartOfWeekMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

private fun getEndOfWeekMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    calendar.add(Calendar.DAY_OF_WEEK, 6)
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}
