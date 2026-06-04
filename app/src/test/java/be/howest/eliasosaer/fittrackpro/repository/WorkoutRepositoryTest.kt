package be.howest.eliasosaer.fittrackpro.repository

import be.howest.eliasosaer.fittrackpro.data.repository.WorkoutRepositoryImpl
import be.howest.eliasosaer.fittrackpro.domain.model.*
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
import be.howest.eliasosaer.fittrackpro.fake.FakeWorkoutDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WorkoutRepositoryTest {

    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository = WorkoutRepositoryImpl(FakeWorkoutDao())
    }

    @Test
    fun insertWorkout_workoutIsReturned() = runTest {

        val workout = Workout(
            id = 1,
            title = "Morning Run",
            dateTime = 1000L,
            formattedDateTime = "",
            durationMinutes = 30,
            isUpcoming = false,
            type = WorkoutType.CARDIO,
            details = CardioDetails(
                distanceKm = 5.0,
                calories = 300
            )
        )

        repository.insertWorkout(workout)

        val result = repository.getWorkouts().first()

        assertEquals(1, result.size)
        assertEquals("Morning Run", result.first().title)
    }

    @Test
    fun getWorkoutById_returnsCorrectWorkout() = runTest {

        val workout = Workout(
            id = 2,
            title = "Leg Day",
            dateTime = 2000L,
            formattedDateTime = "",
            durationMinutes = 60,
            isUpcoming = false,
            type = WorkoutType.STRENGTH,
            details = StrengthDetails(
                setsOrReps = "4x12",
                muscleGroups = "Legs",
                calories = 500
            )
        )

        repository.insertWorkout(workout)

        val result = repository.getWorkoutById(2).first()

        assertEquals("Leg Day", result.title)
        assertEquals(WorkoutType.STRENGTH, result.type)
    }

    @Test
    fun getWorkoutsBetween_filtersCorrectly() = runTest {

        val workout = Workout(
            id = 3,
            title = "Cycling",
            dateTime = 5000L,
            formattedDateTime = "",
            durationMinutes = 45,
            isUpcoming = false,
            type = WorkoutType.CARDIO,
            details = CardioDetails(
                distanceKm = 12.0,
                calories = 600
            )
        )

        repository.insertWorkout(workout)

        val result = repository.getWorkoutsBetween(4000L, 6000L).first()

        assertEquals(1, result.size)
        assertEquals("Cycling", result.first().title)
    }

    @Test
    fun getRecentWorkouts_returnsMax3_andNotUpcomingOnly() = runTest {

        repository.insertWorkout(
            Workout(
                id = 1,
                title = "A",
                dateTime = 1L,
                formattedDateTime = "",
                durationMinutes = 10,
                isUpcoming = false,
                type = WorkoutType.CARDIO,
                details = CardioDetails(null, null)
            )
        )

        repository.insertWorkout(
            Workout(
                id = 2,
                title = "B",
                dateTime = 2L,
                formattedDateTime = "",
                durationMinutes = 10,
                isUpcoming = false,
                type = WorkoutType.CARDIO,
                details = CardioDetails(null, null)
            )
        )

        repository.insertWorkout(
            Workout(
                id = 3,
                title = "C",
                dateTime = 3L,
                formattedDateTime = "",
                durationMinutes = 10,
                isUpcoming = true,
                type = WorkoutType.CARDIO,
                details = CardioDetails(null, null)
            )
        )

        val result = repository.getRecentWorkouts().first()

        assertEquals(2, result.size)
    }
}