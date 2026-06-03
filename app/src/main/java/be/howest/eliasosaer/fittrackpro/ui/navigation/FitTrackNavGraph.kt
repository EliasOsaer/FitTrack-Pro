package be.howest.eliasosaer.fittrackpro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import be.howest.eliasosaer.fittrackpro.ui.editworkout.EditWorkoutScreen
import be.howest.eliasosaer.fittrackpro.ui.editworkout.EditWorkoutViewModel
import be.howest.eliasosaer.fittrackpro.ui.gearshop.GearShopScreen
import be.howest.eliasosaer.fittrackpro.ui.gearshop.GearShopViewModel
import be.howest.eliasosaer.fittrackpro.ui.cart.CartScreen
import be.howest.eliasosaer.fittrackpro.ui.cart.CartViewModel
import be.howest.eliasosaer.fittrackpro.ui.home.HomeScreen
import be.howest.eliasosaer.fittrackpro.ui.home.HomeViewModel
import be.howest.eliasosaer.fittrackpro.ui.scheduleworkout.ScheduleWorkoutScreen
import be.howest.eliasosaer.fittrackpro.ui.scheduleworkout.ScheduleWorkoutViewModel
import be.howest.eliasosaer.fittrackpro.ui.workoutdetail.WorkoutDetailScreen
import be.howest.eliasosaer.fittrackpro.ui.workoutdetail.WorkoutDetailViewModel
import be.howest.eliasosaer.fittrackpro.ui.workouts.WorkoutsScreen
import be.howest.eliasosaer.fittrackpro.ui.workouts.WorkoutsViewModel
import be.howest.eliasosaer.fittrackpro.ui.quickworkout.QuickWorkoutScreen
import be.howest.eliasosaer.fittrackpro.ui.quickworkout.QuickWorkoutViewModel
import be.howest.eliasosaer.fittrackpro.ui.map.MapScreen
import be.howest.eliasosaer.fittrackpro.ui.map.MapViewModel
import be.howest.eliasosaer.fittrackpro.ui.permissions.AppPermission
import be.howest.eliasosaer.fittrackpro.ui.permissions.MainViewModel
import be.howest.eliasosaer.fittrackpro.ui.permissions.PermissionManager
import be.howest.eliasosaer.fittrackpro.ui.sharepurchase.SharePurchaseScreen
import be.howest.eliasosaer.fittrackpro.ui.thankyou.ThankYouScreen
import be.howest.eliasosaer.fittrackpro.ui.sensors.SensorsScreen
import be.howest.eliasosaer.fittrackpro.ui.sensors.SensorsViewModel

@Composable
fun FitTrackNavGraph(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    workoutsViewModel: WorkoutsViewModel = hiltViewModel(),
    scheduleWorkoutViewModel: ScheduleWorkoutViewModel = hiltViewModel(),
    workoutDetailViewModel: WorkoutDetailViewModel = hiltViewModel(),
    editWorkoutViewModel: EditWorkoutViewModel = hiltViewModel(),
    quickWorkoutViewModel: QuickWorkoutViewModel = hiltViewModel(),
    gearShopViewModel: GearShopViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    sensorsViewModel: SensorsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val workoutsUiState by workoutsViewModel.uiState.collectAsStateWithLifecycle()
    val scheduleWorkoutUiState by scheduleWorkoutViewModel.uiState.collectAsStateWithLifecycle()
    val workoutDetailUiState by workoutDetailViewModel.uiState.collectAsStateWithLifecycle()
    val editWorkoutUiState by editWorkoutViewModel.uiState.collectAsStateWithLifecycle()
    val quickWorkoutUiState by quickWorkoutViewModel.uiState.collectAsStateWithLifecycle()
    val gearShopUiState by gearShopViewModel.uiState.collectAsStateWithLifecycle()
    val cartUiState by cartViewModel.uiState.collectAsStateWithLifecycle()
    val mapUiState by mapViewModel.uiState.collectAsStateWithLifecycle()
    val permissionRequest by mainViewModel.permissionRequest.collectAsStateWithLifecycle()
    val sensorsUiState by sensorsViewModel.uiState.collectAsStateWithLifecycle()

    PermissionManager(
        permissionRequest = permissionRequest,
        onPermissionResult = { permission, granted ->
            mainViewModel.onPermissionResult(permission, granted)
        }
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

        composable(Screen.Home.route) {
            LaunchedEffect(Unit) {
                mainViewModel.requestPermission(AppPermission.NOTIFICATIONS)
            }
            HomeScreen(
                uiState = homeUiState,
                onNotificationsClick = {
                    //TODO link notifications screen navController.navigate(Screen.Notifications.route)
                },
                onWeeklyGoalClick = {
                    //TODO link weekly goal screen navController.navigate(Screen.GoalSettings.route)
                },
                onRecentWorkoutClick = { workout ->
                    navController.navigate("workout/${workout.id}")
                },
                onQuickWorkoutClick = {
                    navController.navigate(Screen.QuickWorkout.route)
                }
            )
        }

        composable(Screen.Workouts.route) {
            WorkoutsScreen(
                uiState = workoutsUiState,
                onFilterSelected = { filter ->
                    workoutsViewModel.onFilterSelected(filter)
                },
                onWorkoutClick = { workout ->
                    navController.navigate("workout/${workout.id}")
                },
                onReminderClick = { workout ->
                    workoutsViewModel.onReminderClick(workout)
                },
                onQrClick = { workout ->
                    workoutsViewModel.onQrClick(workout)
                },
                onDismissDialog = {
                    workoutsViewModel.onDismissDialog()
                }
            )
        }

        composable(Screen.ScheduleWorkout.route) {
            ScheduleWorkoutScreen(
                uiState = scheduleWorkoutUiState,
                events = scheduleWorkoutViewModel.events,
                onTitleChange = { value ->
                    scheduleWorkoutViewModel.onTitleChange(value)
                },
                onWorkoutTypeMenuExpandedChange = { expanded ->
                    scheduleWorkoutViewModel.onWorkoutTypeMenuExpandedChange(expanded)
                },
                onWorkoutTypeSelected = { type ->
                    scheduleWorkoutViewModel.onWorkoutTypeSelected(type)
                },
                onDateClick = {
                    scheduleWorkoutViewModel.onDateClick()
                },
                onDismissDatePicker = {
                    scheduleWorkoutViewModel.onDismissDatePicker()
                },
                onDateSelected = { date ->
                    scheduleWorkoutViewModel.onDateSelected(date)
                },
                onTimeChange = { value ->
                    scheduleWorkoutViewModel.onTimeChange(value)
                },
                onDurationChange = { value ->
                    scheduleWorkoutViewModel.onDurationChange(value)
                },
                onImportQrClick = {
                    scheduleWorkoutViewModel.onImportQrClick()
                },
                onRequestCameraPermission = {
                    mainViewModel.requestPermission(AppPermission.CAMERA)
                },
                onSaveClick = {
                    scheduleWorkoutViewModel.onSaveClick()
                },
                onSavedHandled = {
                    scheduleWorkoutViewModel.onSavedHandled()
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.WorkoutDetail.route,
            arguments = listOf(
                navArgument("workoutId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getLong("workoutId")!!
            LaunchedEffect(workoutId) {
                workoutDetailViewModel.loadWorkout(workoutId)
            }

            WorkoutDetailScreen(
                uiState = workoutDetailUiState,
            )
        }

        composable(
            route = Screen.WorkoutDetailEdit.route,
            arguments = listOf(
                navArgument("workoutId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getLong("workoutId")!!
            LaunchedEffect(workoutId) {
                editWorkoutViewModel.loadWorkout(workoutId)
            }

            EditWorkoutScreen(
                uiState = editWorkoutUiState,
                onTitleChange = { editWorkoutViewModel.onTitleChange(it) },
                onDistanceChange = { editWorkoutViewModel.onDistanceChange(it) },
                onCaloriesChange = { editWorkoutViewModel.onCaloriesChange(it) },
                onSetsOrRepsChange = { editWorkoutViewModel.onSetsOrRepsChange(it) },
                onMuscleGroupsChange = { editWorkoutViewModel.onMuscleGroupsChange(it) },
                onRatingChange = { editWorkoutViewModel.onRatingChange(it) },
                onNotesChange = { editWorkoutViewModel.onNotesChange(it) },
                onSaveClick = { editWorkoutViewModel.onSaveClick() },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.QuickWorkout.route) {
            QuickWorkoutScreen(
                uiState = quickWorkoutUiState,
                onWorkoutStarted = {
                    quickWorkoutViewModel.onWorkoutStarted()
                },
                onTitleChange = { quickWorkoutViewModel.onTitleChange(it) },
                onWorkoutTypeSelected = { quickWorkoutViewModel.onWorkoutTypeSelected(it) },
                onEndWorkoutClick = { elapsedSeconds ->
                    quickWorkoutViewModel.onEndWorkoutClick(
                        elapsedSeconds = elapsedSeconds,
                        navigateBack = { navController.popBackStack() }
                    )
                }
            )
        }

        composable(Screen.Shop.route) {
            GearShopScreen(
                uiState = gearShopUiState,
                onSearchQueryChange = { query ->
                    gearShopViewModel.onSearchQueryChange(query)
                },
                onToggleSortOrder = {
                    gearShopViewModel.onToggleSortOrder()
                },
                onAddToCart = { item ->
                    gearShopViewModel.onAddToCart(item)
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                uiState = cartUiState,
                onBackClick = {
                    navController.popBackStack()
                },
                onCheckoutClick = {
                    cartViewModel.onCheckout()
                    navController.navigate(Screen.SharePurchase.route)
                },
                onIncreaseQuantity = { item ->
                    cartViewModel.onUpdateQuantity(item.id, item.qty + 1)
                },
                onDecreaseQuantity = { item ->
                    if (item.qty > 1) {
                        cartViewModel.onUpdateQuantity(item.id, item.qty - 1)
                    } else {
                        cartViewModel.onRemove(item.id)
                    }
                }
            )
        }

        composable(Screen.Map.route) {
            MapScreen(
                uiState = mapUiState,
                onRequestLocationPermissionResult = { granted ->
                    if (!granted) {
                        // TODO add permission manager support
                    }
                },
                onMapReady = { mapView ->
                    mapViewModel.attachMapView(mapView)
                },
                onMapDisposed = {
                    mapViewModel.detachMapView()
                }
            )
        }

        composable(Screen.SharePurchase.route) {
            SharePurchaseScreen(
                onShared = {
                    navController.navigate(Screen.ThankYou.route) {
                        popUpTo(Screen.SharePurchase.route) { inclusive = true }
                    }
                },
                onNoThanksClick = {
                    navController.navigate(Screen.ThankYou.route) {
                        popUpTo(Screen.SharePurchase.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ThankYou.route) {
            ThankYouScreen(
                onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Sensors.route) {
            SensorsScreen(
                uiState = sensorsUiState,
                onScreenVisible = {
                    sensorsViewModel.onScreenVisible()
                },
                onScreenHidden = {
                    sensorsViewModel.onScreenHidden()
                }
            )
        }
    }
}
