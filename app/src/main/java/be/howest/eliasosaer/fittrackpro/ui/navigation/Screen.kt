package be.howest.eliasosaer.fittrackpro.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import be.howest.eliasosaer.fittrackpro.R

sealed class Screen(
    val route: String,
    @StringRes val labelResourceId: Int,
    val icon: ImageVector
) {
    data object Home : Screen("home", R.string.home_label, Icons.Filled.Home)
    data object Workouts : Screen("workouts", R.string.workouts_label, Icons.Filled.FitnessCenter)
    data object Shop : Screen("shop", R.string.shop_label, Icons.Filled.ShoppingCart)
    data object Cart : Screen("cart", R.string.shop_label, Icons.Filled.ShoppingCart)
    data object Map : Screen("map", R.string.map_label, Icons.Filled.Map)
    data object Sensors : Screen("sensors", R.string.sensors_label, Icons.Filled.Sensors)

    data object ScheduleWorkout : Screen("schedule_workout", R.string.schedule_workout, Icons.Filled.FitnessCenter)
    data object WorkoutDetail: Screen("workout/{workoutId}", R.string.shop_label, Icons.Filled.Search)
    data object WorkoutDetailEdit: Screen("workout_edit/{workoutId}", R.string.edit_workout_label, Icons.Filled.Edit)
    data object QuickWorkout : Screen("quick_workout", R.string.workout_add_label, Icons.Filled.FitnessCenter)
    data object SharePurchase : Screen("share_purchase", R.string.share_purchase_label, Icons.Filled.Share)
    data object ThankYou : Screen("thank_you", R.string.thank_you_label, Icons.Filled.Handshake)
    data object Notification : Screen("notification", R.string.notifications_label, Icons.Filled.Mail)
    data object WeeklyProgress : Screen("Progress", R.string.progress_label, Icons.Filled.Mail)
}

val bottomBarScreens = listOf(
    Screen.Home,
    Screen.Workouts,
    Screen.Shop,
    Screen.Map,
    Screen.Sensors
)
