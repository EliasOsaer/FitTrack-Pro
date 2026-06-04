package be.howest.eliasosaer.fittrackpro.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.howest.eliasosaer.fittrackpro.domain.model.CardioDetails
import be.howest.eliasosaer.fittrackpro.domain.model.SummaryStat
import be.howest.eliasosaer.fittrackpro.domain.model.Workout
import be.howest.eliasosaer.fittrackpro.domain.model.WorkoutType
import be.howest.eliasosaer.fittrackpro.ui.home.HomeScreen
import be.howest.eliasosaer.fittrackpro.ui.home.HomeUiState
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeWorkout = Workout(
        id = 1L,
        title = "Morning Run",
        dateTime = 0L,
        formattedDateTime = "01 Jan 2026 • 10:00",
        durationMinutes = 30,
        isUpcoming = false,
        type = WorkoutType.CARDIO,
        details = CardioDetails(
            distanceKm = 5.0,
            calories = 300
        )
    )

    private val fakeState = HomeUiState(
        summaryStats = listOf(
            SummaryStat("Steps", "5000"),
            SummaryStat("Calories", "1200")
        ),
        weeklyGoalProgress = 0.6f,
        recentWorkouts = listOf(fakeWorkout)
    )

    @Test
    fun homeScreen_allMainElements_areDisplayed() {

        composeTestRule.setContent {
            HomeScreen(
                uiState = fakeState,
                onWeeklyGoalClick = {},
                onRecentWorkoutClick = {},
                onQuickWorkoutClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("home_screen")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("weekly_goal_card")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("quick_workout_button")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Today's Summary")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Recent workouts")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Morning Run")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_clicks_workWithoutCrashing() {

        var weeklyGoalClicked = false
        var quickWorkoutClicked = false
        var workoutClicked = false

        composeTestRule.setContent {
            HomeScreen(
                uiState = fakeState,
                onWeeklyGoalClick = { weeklyGoalClicked = true },
                onRecentWorkoutClick = { workoutClicked = true },
                onQuickWorkoutClick = { quickWorkoutClicked = true }
            )
        }

        composeTestRule
            .onNodeWithTag("weekly_goal_card")
            .performClick()

        composeTestRule
            .onNodeWithTag("quick_workout_button")
            .performClick()

        composeTestRule
            .onNodeWithText("Morning Run")
            .performClick()

        assert(weeklyGoalClicked)
        assert(quickWorkoutClicked)
        assert(workoutClicked)
    }
}