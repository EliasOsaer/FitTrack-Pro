package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.CardioDetails
import be.howest.eliasosaer.fittrackpro.domain.model.StrengthDetails
import be.howest.eliasosaer.fittrackpro.domain.model.SummaryStat
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTodaySummaryUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<SummaryStat>> {

        val startOfDay = getStartOfDayMillis()
        val now = System.currentTimeMillis()

        return repository.getWorkoutsBetween(startOfDay, now)
            .map { workouts ->

                val totalMinutes = workouts.sumOf { it.durationMinutes }

                val totalCalories = workouts.sumOf { workout ->
                    when (val d = workout.details) {
                        is CardioDetails -> d.calories ?: 0
                        is StrengthDetails -> d.calories ?: 0
                    }
                }

                val totalDistance = workouts.sumOf { workout ->
                    when (val d = workout.details) {
                        is CardioDetails -> d.distanceKm ?: 0.0
                        else -> 0.0
                    }
                }

                listOf(
                    SummaryStat("Active Minutes", "$totalMinutes min"),
                    SummaryStat("Calories Burned", "$totalCalories kcal"),
                    SummaryStat("Distance", "${"%.1f".format(totalDistance)} km")
                )
            }
    }
}

fun getStartOfDayMillis(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}
