package be.howest.eliasosaer.fittrackpro.domain.usecase

import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<Workout>> {
        return repository.getWorkouts()
    }
}