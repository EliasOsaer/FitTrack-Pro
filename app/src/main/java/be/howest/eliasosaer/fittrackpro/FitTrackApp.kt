package be.howest.eliasosaer.fittrackpro

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.eliasosaer.fittrackpro.ui.navigation.FitTrackAppBar
import be.howest.eliasosaer.fittrackpro.ui.navigation.FitTrackAppBarAction
import be.howest.eliasosaer.fittrackpro.ui.navigation.FitTrackBottomBar
import be.howest.eliasosaer.fittrackpro.ui.navigation.FitTrackNavGraph
import be.howest.eliasosaer.fittrackpro.ui.navigation.Screen
import be.howest.eliasosaer.fittrackpro.ui.theme.FitTrackProTheme

@Composable
fun FitTrackApp() {
    FitTrackProTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val currentRoute = currentDestination?.route

        val canNavigateBack = currentRoute in listOf(
            Screen.ScheduleWorkout.route,
            Screen.WorkoutDetail.route,
            Screen.WorkoutDetailEdit.route,
            Screen.QuickWorkout.route
        )

        val appBarActions = when (currentRoute) {
            Screen.Workouts.route -> listOf(
                FitTrackAppBarAction(
                    icon = Icons.Filled.Add,
                    contentDescriptionResId = R.string.workout_add_label,
                    onClick = {
                        navController.navigate(Screen.ScheduleWorkout.route)
                    }
                )
            )

            Screen.WorkoutDetail.route -> listOf(
                FitTrackAppBarAction(
                    icon = Icons.Filled.Edit,
                    contentDescriptionResId = R.string.edit_workout_label,
                    onClick = {
                        val workoutId = navBackStackEntry?.arguments?.getLong("workoutId")

                        if (workoutId != null) {
                            navController.navigate("workout_edit/$workoutId")
                        }
                    }
                )
            )

            else -> listOf(
                FitTrackAppBarAction(
                    icon = Icons.Filled.Notifications,
                    contentDescriptionResId = R.string.notifications_label,
                    onClick = {
                        // TODO notifications later
                    }
                )
            )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                FitTrackAppBar(
                    appName = R.string.app_name,
                    canNavigateBack = canNavigateBack,
                    navigateUp = { navController.navigateUp() },
                    actions = appBarActions
                )
            },
            bottomBar = {
                FitTrackBottomBar(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        ) { innerPadding ->
            FitTrackNavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}